import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Properties;
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

    public void getDailyFiles(Properties prop, String date) throws IOException {
        String serverCommandAndPath = prop.getProperty("servercommand");
        Set<String> commands = new LinkedHashSet<>();
        Files.createDirectories(Paths.get(basePath + date + "/nofilter/"));
        for (Instance i : instanceSet) {
            for (int j = 0; j < i.getServerFileNames().size(); j++) {
                Files.createDirectories(Paths.get(basePath + date + i.getDirectoriesForLogFiles().get(j)));
                commands.add(serverCommandAndPath + i.getServerPath() + date + i.getServerFileNames().get(j)
                        + " " + basePath + date + i.getDirectoriesForLogFiles().get(j));
            }
        }
        for (String command : commands) {
            Runtime.getRuntime().exec(command);
        }
    }

}
