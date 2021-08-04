import java.io.IOException;
import java.util.Set;

public class MonitorMonitor {

    public static void main(String[] args) {
        String whatYouLookingFor = "test failed";
        String folderToSearch = "/temp_check";
        final String basePath = "/home/mihalyl/Documents/log_monitoring/";
        final String date = Preparator.getDateInRightFormat();
        final Set<Instance> instanceSet = Instance.getInstanceSet(folderToSearch, date, whatYouLookingFor);
        Executor executor = new Executor(date, basePath, whatYouLookingFor);
        executor.filter(instanceSet);
    }
}
