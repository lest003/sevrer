import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErrorGroup {
    private final boolean keepHash;
    private final List<String> filterPhrases;
    private final String groupMarker;
    private final List<String> errors = new ArrayList<>();

    public ErrorGroup(boolean keepHash, List<String> filterPhrases, String groupMarker) {
        this.keepHash = keepHash;
        this.filterPhrases = filterPhrases;
        this.groupMarker = groupMarker;
    }

    public boolean getKeepHash() {
        return keepHash;
    }

    public String getGroupMarker() {
        return groupMarker;
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getFilterPhrases() {
        return filterPhrases;
    }

    public static List<ErrorGroup> getErrorGroups() {
        ErrorGroup userRevision = new ErrorGroup(false, Arrays.asList("Default User Revision"),
                "\n\n----Default User Revision Errors-------------------------------------------\n" +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-16340\n\n");
        ErrorGroup useDescribed = new ErrorGroup(false, Arrays.asList("Use described configured"),
                "\n\n----Use described configured errors----------------------------------------\n\n");
        ErrorGroup webSocket = new ErrorGroup(false, Arrays.asList(
                "WebSocketSession is not attached to the WebSession",
                "Failed to fire STARTED from WebProcessor",
                "Failed web processing"),
                "\n\n----WebSocketSession is not attached errors--------------------------------\n\n");
        ErrorGroup alreadyStopped = new ErrorGroup(false, Arrays.asList("service has been already stopped"),
                "\n\n----Service already stopped errors-----------------------------------------\n\n");
        ErrorGroup unDisposedListeners = new ErrorGroup(false, Arrays.asList("ndisposed listeners"),
                "\n\n----Undisposed listeners errors--------------------------------------------\n" +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-10097\n\n");
        ErrorGroup dirtyEvalGroup = new ErrorGroup(false, Arrays.asList("A DIRTY evaluation engine CliElEvaluationEngine"),
                "\n\n---- Possible Evaluation stuck --------------------------------------------\n" +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-11189\n\n");


        List<ErrorGroup> errorGroups = new ArrayList<>(Arrays.asList(
                userRevision, useDescribed, webSocket, alreadyStopped,
                unDisposedListeners, dirtyEvalGroup));

//      TODO temporary stuff
        ErrorGroup psqlErrorGroup = new ErrorGroup(true, Arrays.asList("PSQLException"),
                "\n\n---- PSQL Exceptions  --------------------------------------------\n\n");
        errorGroups.add(psqlErrorGroup);
        ErrorGroup referenceColumnErrorGroup = new ErrorGroup(true, Arrays.asList("Referenced column not found for Prod Marketing Hist Session"),
                "\n\n---- Reference Column errors ----------------------------------------\n\n");
        errorGroups.add(referenceColumnErrorGroup);
        ErrorGroup concurrentErrorGroup = new ErrorGroup(true, Arrays.asList(
                "Concurrent modification",
                "different user step"),
                "\n\n---- Concurrent Modification errors-----------------------------------\n\n");
        errorGroups.add(concurrentErrorGroup);
        ErrorGroup beanNotFound = new ErrorGroup(true, Arrays.asList(
                "Bean not found with id"),
                "\n\n---- Bean not found with id errors -----------------------------------\n\n");
        errorGroups.add(beanNotFound);
        ErrorGroup failedToHndlIncMsg = new ErrorGroup(true, Arrays.asList(
                " Failed to handle incoming message"),
                "\n\n----  Failed to handle incoming message errors -------------------------\n\n");
        errorGroups.add(failedToHndlIncMsg);
        ErrorGroup rowLimitErrors = new ErrorGroup(true, Arrays.asList(
                "Cannot limit rows in PostgresUpdateDataSource"),
                "\n\n---- Cannot limit rows in PostgresUpdateDataSource ---------------------\n" +
                        "---- waiting on PRRev\n\n");
        errorGroups.add(rowLimitErrors);

        ErrorGroup tempIntErrorGroup = new ErrorGroup(true, Arrays.asList("Higher link of LinkEntity is not the first property"),
                "\n\n----Temporarily ignored errors--------------------------------------------\n\n");
        errorGroups.add(tempIntErrorGroup);


        // TODO local file search
//        errorGroups.clear();
//        ErrorGroup finderErrorGroup = new ErrorGroup(true, Arrays.asList(
//        "WorkResourceError$NotFound: Work resource"),
//                "\n\n----here here--------------------------------------------\n\n");
//        errorGroups.add(finderErrorGroup);


        return errorGroups;
    }
}
