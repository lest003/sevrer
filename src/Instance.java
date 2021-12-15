import java.util.*;

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
    public static Set<Instance> getInstanceSet(Properties properties) {
        Set<Instance> instanceSet = new HashSet<Instance>();
        for (String propName : properties.stringPropertyNames()) {
            String[] propKeys = propName.split("_");
            String env = propKeys[0];
            if ("test".equals(env) || "int".equals(env) || "prod".equals(env)) {
                String ctr = propKeys[2];
                if (propName.equals(env + "_filteredlogfile_" + ctr)) {                                                             // This is a required prop
                    instanceSet.add(new Instance(
                            properties.getProperty(env + "_filteredlogfile_" + ctr)
                            , Arrays.asList(properties.getProperty(env + "_localpaths_" + ctr))
                            , properties.getProperty(env + "_serverpath_" + ctr)
                            , Arrays.asList(properties.getProperty(env + "_logfiles_" + ctr))
                    ));
                }
            }
        }

        return instanceSet;
    }
}
