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

    public static List<ErrorGroup> getErrorGroups(String whatYouLookingFor) {
        System.out.println(whatYouLookingFor);
        // TODO local file search
        ErrorGroup finderErrorGroup = new ErrorGroup(true, Arrays.asList(
                whatYouLookingFor),
                "\n\n----here here--------------------------------------------\n\n");
        List<ErrorGroup> errorGroups = new ArrayList<>(Arrays.asList(finderErrorGroup));
        return errorGroups;
    }
}
