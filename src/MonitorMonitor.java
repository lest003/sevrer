import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class MonitorMonitor {

    public static void main(String[] args) throws IOException, InterruptedException {
        Properties properties = new Properties();
        try (FileInputStream inStream = new FileInputStream("config.properties")) {
            properties.load(inStream);
        } catch (IOException fnfEx) {
            throw new FileNotFoundException("config.properties file is missing");
        }

        final String basePath = properties.getProperty("sourcefolder", System.getProperty("user.home"));
        final Set<Instance> instanceSet = Instance.getInstanceSet(properties);
        final String date = Preparator.getDateInRightFormat();
        final int downloadTimeInSec = 15;

        Preparator preparator = new Preparator(basePath, instanceSet);
        preparator.getDailyFiles(properties, date);
        Animator animator = new Animator();
        animator.animateBar(downloadTimeInSec);
        Executor executor = new Executor(date, basePath);
        executor.filter(instanceSet);
    }
}
