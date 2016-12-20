package utils;

import java.util.Arrays;
import java.util.List;

public class Utils {

    public static List<String> getListFromCommaSeperateString(String commaSeperateString) {
        return Arrays.asList(commaSeperateString.split(","));
    }
}
