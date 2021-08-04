import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Instance {

    private final String filteredFilename;
    private final List<String> directoriesForLogFiles;
    private final String serverPath;
    private final List<String> serverFileNames;


    public Instance(String filteredFilename, List<String> directoriesForLogFiles, String serverPath, List<String> serverFileNames) {
        this.filteredFilename = filteredFilename;
        this.directoriesForLogFiles = directoriesForLogFiles;
        this.serverPath = serverPath;
        this.serverFileNames = serverFileNames;
    }

    public String getFilteredFilename() {
        return filteredFilename;
    }

    public List<String> getDirectoriesForLogFiles() {
        return directoriesForLogFiles;
    }

    public String getServerPath() {
        return serverPath;
    }

    public List<String> getServerFileNames() {
        return serverFileNames;
    }

    /*
    in case of server restart:
    extend directoryForLogFiles
    extend serverFileNames
     */
    public static Set<Instance> getInstanceSet(String folderToSearch, String date, String whatYouLookingFor) {
        // TODO local file search in elder folder
        Instance finder = new Instance(whatYouLookingFor + "_" + date, Arrays.asList(folderToSearch),
                "/home/mihalyl/Documents/log_monitoring/", Arrays.asList("*unfiltered"));
        Set<Instance> instanceSet = new HashSet<Instance>(Arrays.asList(finder));
        return instanceSet;
    }
}
