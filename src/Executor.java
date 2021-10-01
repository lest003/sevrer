import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Executor {

    private final String basePath;
    private final String date;
    private static final String line = "--------------------------------------------------------------------------";
    private final String br = "\n";

    public Executor(String date, String basePath) {
        this.date = date;
        this.basePath = basePath;
    }

    public void filter(Set<Instance> instanceSet) {
        System.out.println("\nthe filtered files will be saved at: \n" + basePath + date + "/");
        prepareActionable();

        for (Instance instance : instanceSet) {
            String path = basePath + date + "/";
            String filePathAndName = basePath + date + "/" + date + instance.getFilteredFilename();

            try (FileWriter myUnfilteredWriter = new FileWriter(path + "/nofilter/"
                    + date + instance.getFilteredFilename() + "_unfiltered");
                 FileWriter myWriter = new FileWriter(filePathAndName)) {

                String folder = basePath + date + instance.getDirectoriesForLogFiles().get(0);
                List<File> files = new ArrayList<>();
                List<File> listOfFiles = getListOfFiles(folder, files);

                List<String> parsedErrors = parse(listOfFiles);
                writeToFile(parsedErrors, myUnfilteredWriter);

                unSevere(parsedErrors);

                List<ErrorGroup> errorGroups = ErrorGroup.getErrorGroups();
                List<String> unGroupedErrors = separateGroupedFromUngrouped(parsedErrors, errorGroups);

                List<String> sortedErrors = sortGroupedErrors(errorGroups);

                List<String> trimmedErrors = groupSameSizedErrors(unGroupedErrors);
                sortTrimmedErrors(trimmedErrors, sortedErrors);
                sortUngroupedErrors(unGroupedErrors, sortedErrors);

                writeToFile(sortedErrors, myWriter);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareActionable() {
        try (FileWriter myActionableWriter = new FileWriter(basePath + date + "/" + date + "_actionable")) {
            String separator = line + br;
            String placeForText = br + br + br + br;
            List<String> actionableTemplate = new ArrayList<>(Arrays.asList(date + br,
                    separator + "t1\n" + separator + placeForText + separator,
                    separator + "cmb_t1\n" + separator + placeForText + separator,
                    separator + "t1b\n" + separator + placeForText + separator));
            writeToFile(actionableTemplate, myActionableWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getListOfFiles(String directoryName, List<File> files) {
        File directory = new File(directoryName);
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    getListOfFiles(file.getAbsolutePath(), files);
                }
            }
        return files;
    }

    private LinkedList<String> parse(List<File> files) {
        LinkedList<String> allErrors = new LinkedList<>();
        for (File file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    if (!line.contains("[NO_USER]"))
                        sb.append(line).append("\n");
                    if (line.contains("---END")) {
                        sb.append("\n");
                        allErrors.add(sb.toString());
                        sb = new StringBuilder();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return allErrors;
    }

    private void unSevere(List<String> errors) {
        for (int i = errors.size() - 1; i >= 0; i--) {
            if (!errors.get(i).contains("SEVR ")) {
                errors.remove(i);
            }
        }
    }

    private List<String> separateGroupedFromUngrouped(List<String> errors, List<ErrorGroup> errorGroups) {
        for (int i = errors.size() - 1; i >= 0; i--) {
            if (grouper(errors.get(i), errorGroups)) {
                errors.remove(i);
            }
        }
        return errors;
    }

    private boolean grouper(String error, List<ErrorGroup> errorGroups) {
        for (int i = 0; i < errorGroups.size(); i++) {

            for (int j = 0; j < errorGroups.get(i).getFilterPhrases().size(); j++) {
                if (error.contains(errorGroups.get(i).getFilterPhrases().get(j))) {
                    errorGroups.get(i).getErrors().add(error);
                    return true;
                }
            }
        }
        return false;
    }

    private List<String> sortGroupedErrors(List<ErrorGroup> errorGroups) {
        List<String> sortedErrors = new ArrayList<>();
        for (int i = 0; i < errorGroups.size(); i++) {
            List<String> groupsErrors = errorGroups.get(i).getErrors();
            if (!groupsErrors.isEmpty()) {
                sortedErrors.add(errorGroups.get(i).getGroupMarker());
                cutAroundNoformat(groupsErrors, errorGroups.get(i).getKeepHash());
                sortByLength(groupsErrors);
                sortedErrors.addAll(groupsErrors);
            }
        }
        return sortedErrors;
    }

    private void cutAfterStart(List<String> errors) {
        if (!errors.isEmpty()) {
            for (int i = 0; i < errors.size(); i++) {
                String error = errors.get(i);
                String noformat = "{noformat}";
                int firstIndex = error.indexOf("\n", 1);
                int lastIndex = error.indexOf(noformat);

                String blankSpace = br + br + line + br + br;
                String textToCut = error.substring(firstIndex, lastIndex);
                error = error.replace(textToCut, blankSpace);
                errors.set(i, error);
            }
        }
    }

    private void cutAroundNoformat(List<String> uglyErrors, boolean keepHash) {
        if (!uglyErrors.isEmpty()) {
            for (int i = 0; i < uglyErrors.size(); i++) {
                String uglyError = uglyErrors.get(i);
                String noformat = "{noformat}";
                int firstIndex = uglyError.indexOf(noformat);
                int lastIndex = keepHash ? uglyError.indexOf("----END") : uglyError.lastIndexOf(noformat) + 1;
                String prettyError = keepHash
                        ? uglyError.substring(firstIndex, lastIndex)
                        : uglyError.substring(firstIndex, lastIndex + noformat.length());
                uglyErrors.set(i, prettyError);
            }
        }
    }

    private void cutOnlyTheEnd(List<String> uglyErrors) {
        if (!uglyErrors.isEmpty()) {
            for (int i = 0; i < uglyErrors.size(); i++) {
                String uglyError = uglyErrors.get(i);
                int lastIndex = uglyError.indexOf("----END") - 1;
                String prettyError = uglyError.substring(0, lastIndex);
                uglyErrors.set(i, prettyError);
            }
        }
    }

    private void sortByLength(List<String> errors) {
        errors.sort(Comparator.comparingInt(String::length));
    }

    private void sortUngroupedErrors(List<String> errors, List<String> sortedErrors) {
        if (!errors.isEmpty()) {
            sortByLength(errors);
            if (!sortedErrors.isEmpty())
                sortedErrors.add(br + br + separateWithLineAndText("All other errors") + br + br);
            sortedErrors.addAll(errors);

        }
    }

    private void sortTrimmedErrors(List<String> trimmedErrors, List<String> sortedErrors) {
        if (!sortedErrors.isEmpty() && !trimmedErrors.isEmpty()) {
            sortedErrors.add(br + br + separateWithLineAndText("Same same") + br + br);
        }
        sortedErrors.addAll(trimmedErrors);

    }

    private List<String> groupSameSizedErrors(List<String> unGroupedErrors) {
        List<String> trimmedErrors = new ArrayList<>();
        if (!unGroupedErrors.isEmpty()) {
            for (int i = unGroupedErrors.size() - 1; i >= 0; i--) {
                List<String> errorsToTrim = new ArrayList<>();
                int length = unGroupedErrors.get(i).length();
                for (int j = i - 1; j >= 0; j--) {
                    if (unGroupedErrors.get(j).length() == length) {
                        errorsToTrim.add(unGroupedErrors.get(j));
                        unGroupedErrors.remove(j);
                        i--;
                    }
                }
                if (!errorsToTrim.isEmpty()) {
                    errorsToTrim.add(unGroupedErrors.get(i));
                    unGroupedErrors.remove(i);
                    trimSimilarErrorsTogether(errorsToTrim);
                    trimmedErrors.addAll(errorsToTrim);
                }
            }
        }
        return trimmedErrors;

    }


    private void trimSimilarErrorsTogether(List<String> errorsToTrim) {
        List<String> firstError = new ArrayList<>(Arrays.asList(errorsToTrim.get(0)));
        List<String> lastError = new ArrayList<>(Arrays.asList(errorsToTrim.get(errorsToTrim.size() - 1)));

        cutAroundNoformat(lastError, true);
        cutAfterStart(firstError);
        cutOnlyTheEnd(firstError);
        cutAroundNoformat(errorsToTrim, false);
        errorsToTrim.set(0, firstError.get(0));
        errorsToTrim.set(errorsToTrim.size() - 1, lastError.get(0)
                .concat(br + br + line + br + br));
    }

    private void writeToFile(List<String> errors, FileWriter myWriter) {
        try {
            for (int i = 0, size = errors.size(); i < size; i++) {
                myWriter.append(errors.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String separateWithLineAndText(String text) {
        text = " " + text + " ";
        StringBuilder separator = new StringBuilder(line);
        separator.replace(4, 4 + text.length(), text);
        return separator.toString();
    }
}
