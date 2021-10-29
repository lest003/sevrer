import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ErrorGroup {
    private final boolean keepHash;
    private final List<String> filterPhrases;
    private final String groupMarker;
    private final List<String> errors = new ArrayList<>();
    private static final String br = "\n";

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
        Executor.separateWithLineAndText("Default User Revision Errors");
        ErrorGroup userRevision = new ErrorGroup(false, Arrays.asList(
                "Default User Revision"),
                br + br + Executor.separateWithLineAndText("Default User Revision Errors") + br +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-16340" + br + br);
        ErrorGroup useDescribed = new ErrorGroup(false, Arrays.asList(
                "Use described configured"),
                br + br + Executor.separateWithLineAndText("Default User Revision Errors") + br + br);
        ErrorGroup webSocket = new ErrorGroup(false, Arrays.asList(
                "WebSocketSession is not attached to the WebSession",
                "Failed to fire STARTED from WebProcessor",
                "Failed web processing"),
                br + br + Executor.separateWithLineAndText("WebSocketSession is not attached errors") + br + br);
        ErrorGroup alreadyStopped = new ErrorGroup(false, Arrays.asList(
                "service has been already stopped"),
                br + br + Executor.separateWithLineAndText("Service already stopped errors") + br + br);
        ErrorGroup unDisposedListeners = new ErrorGroup(false, Arrays.asList(
                "ndisposed listeners"),
                br + br + Executor.separateWithLineAndText("Undisposed listeners errors") + br +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-10097\n\n");
        ErrorGroup dirtyEvalGroup = new ErrorGroup(false, Arrays.asList(
                "A DIRTY evaluation engine CliElEvaluationEngine"),
                br + br + Executor.separateWithLineAndText("Possible Evaluation stuck") + br +
                        "    https://jira.smedigitalapps.com/jira/browse/GRA-11189\n\n");
        ErrorGroup resultResent5301Times = new ErrorGroup(false, Arrays.asList(
                "Query result response was resent 5301 times"),
                br + br + Executor.separateWithLineAndText("Query result response was resent 5301 times") + br + br);
        ErrorGroup failedTests = new ErrorGroup(true, Arrays.asList(
                "test failed"),
                br + br + Executor.separateWithLineAndText("Failed tests") + br + br);
        ErrorGroup psqlErrorGroup = new ErrorGroup(true, Arrays.asList(
                "PSQLException"),
                br + br + Executor.separateWithLineAndText("PSQL Exceptions") + br + br);
        ErrorGroup concurrentErrorGroup = new ErrorGroup(true, Arrays.asList(
                "Concurrent modification",
                "different user step"),
                br + br + Executor.separateWithLineAndText("Concurrent Modification errors") + br + br);
        ErrorGroup beanNotFound = new ErrorGroup(true, Arrays.asList(
                "Bean not found with id"),
                br + br + Executor.separateWithLineAndText("Bean not found with id errors") + br + br);
        ErrorGroup failedToHandleIncMsg = new ErrorGroup(true, Arrays.asList(
                "Failed to handle incoming message"),
                br + br + Executor.separateWithLineAndText("Failed to handle incoming message errors") + br + br);

        List<ErrorGroup> errorGroups = new ArrayList<>(Arrays.asList(
                userRevision, useDescribed, webSocket, alreadyStopped, unDisposedListeners, dirtyEvalGroup,
                resultResent5301Times, failedTests, psqlErrorGroup, concurrentErrorGroup, beanNotFound, failedToHandleIncMsg));

        return errorGroups;
    }
}
