import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class Preparator {
    private final String basePath;
    private final Set<Instance> instanceSet;

    public Preparator(String basePath, Set<Instance> instanceSet) {
        this.basePath = basePath;
        this.instanceSet = instanceSet;
    }

    public static String getDateInRightFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MMdd");
        Date dateRaw = new Date();
        return dateFormat.format(dateRaw);
    }

    public void getDailyFiles(String date) throws IOException {
        String serverCommandAndPath = "scp ngd:/GRAS/share/GRASINST/";
        Set<String> commands = new LinkedHashSet<>();
        commands.add("mkdir -p " + basePath + date + "/nofilter/");
        System.out.println("mkdir -p " + basePath + date + "/nofilter");
        for (Instance i : instanceSet) {
            for (int j = 0; j < i.getServerFileNames().size(); j++) {
                commands.add("mkdir -p " + basePath + date + i.getDirectoriesForLogFiles().get(j));
                commands.add(serverCommandAndPath + i.getServerPath() + date + i.getServerFileNames().get(j)
                        + " " + basePath + date + i.getDirectoriesForLogFiles().get(j));
            }
        }
        for (String command : commands) {
            Runtime.getRuntime().exec(command);
        }
    }

}
