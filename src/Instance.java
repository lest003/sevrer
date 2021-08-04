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
    public static Set<Instance> getInstanceSet() {
        Instance t1client = new Instance("_t1_client", Arrays.asList("/logfiles/test/client/"),
                "LMTGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.clientExceptionsGroup.log"));
        Instance t1error = new Instance("_t1_error", Arrays.asList("/logfiles/test/error/"),
                "LMTGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Instance cmb_t1 = new Instance("_cmb_t1_error", Arrays.asList("/logfiles/cmb_t1/"),
                "LMTCMB1/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Instance i1client = new Instance("_i1_client", Arrays.asList("/logfiles/int/client/"),
                "LMIGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.clientExceptionsGroup.log"));
        Instance i1error = new Instance("_i1_error", Arrays.asList("/logfiles/int/error/"),
                "LMIGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Instance f1client = new Instance("_f1_client", Arrays.asList("/logfiles/func/client/"),
                "LMFGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.clientExceptionsGroup.log"));
        Instance f1error = new Instance("_f1_error", Arrays.asList("/logfiles/func/error/"),
                "LMFGRAS1/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Instance cmb_i1 = new Instance("_cmb_i1_error", Arrays.asList("/logfiles/cmb_i1/"),
                "LMICMB1/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Instance t1bclient = new Instance("_t1_b_client", Arrays.asList("/logfiles/test_b/client/"),
                "LMTGRAS1B/backup/result/middletier/2021", Arrays.asList("*/*.gras.clientExceptionsGroup.log"));
        Instance t1berror = new Instance("_t1_b_error", Arrays.asList("/logfiles/test_b/error/"),
                "LMTGRAS1B/backup/result/middletier/2021", Arrays.asList("*/*.gras.errorGroup.log"));
        Set<Instance> instanceSet = new HashSet<Instance>(Arrays.asList(
                t1client, t1error, cmb_t1, t1bclient, t1berror));
        return instanceSet;
    }
}
