package net.weg.observability_test.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SetConverter {

    public static Set<String> convertStringToSet(String str) {
        if (str == null || str.isEmpty()) return new HashSet<>();
        Set<String> result = Arrays.stream(str.split(","))
                .collect(Collectors.toSet());
        return result;
    }

    public static String convertSetToString(Set<String> set) {
        if (set == null || set.isEmpty()) return "";
        return set.stream().collect(Collectors.joining(","));
    }

}
