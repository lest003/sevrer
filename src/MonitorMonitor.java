import java.io.IOException;
import java.util.Set;

public class MonitorMonitor {

    public static void main(String[] args) throws IOException, InterruptedException {
        final String basepath = "/home/mihalyl/Documents/log_monitoring/";
        final Set<Instance> instanceSet = Instance.getInstanceSet();

        final String date = Preparator.getDateInRightFormat();

        Preparator preparator = new Preparator(basepath, instanceSet);
//        preparator.getDailyFiles(date);
        Animator animator = new Animator();
//        animator.animateBar();
        Executor executor = new Executor(date, basepath);
        executor.filter(instanceSet);
    }
}
