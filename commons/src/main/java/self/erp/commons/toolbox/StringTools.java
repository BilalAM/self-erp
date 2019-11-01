package self.erp.commons.toolbox;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class StringTools {
    public static String replaceString(String toBeReplaced, Map<String, String> map) {
        StringSubstitutor substitutor = new StringSubstitutor(map);
        return substitutor.replace(toBeReplaced);
    }
}
