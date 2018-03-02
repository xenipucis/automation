package es.automation.exceptions;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StringsHelper {

    public static final String DOT = "\\.";
    public static final String COLON = ":";
    public static final String COMMA = ",";

    public String extractSubStringBetweenTwoSubStrings(final String baseString, final String str1, final String str2) {
        if (str2.isEmpty()) {
            return extractSubStringBetweenTwoSubStrings(new StringBuilder(baseString).append(COLON).toString(), str1,
                    COLON);
        }
        Pattern pattern = Pattern.compile(new StringBuilder().append(str1).append("(.*?)").append(str2).toString());
        Matcher matcher = pattern.matcher(baseString);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
