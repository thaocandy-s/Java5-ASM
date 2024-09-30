package poly.thao.menfashion.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum EntityStatus {

    ACTIVE,
    INACTIVE,
    DELETED;

    public static List<String> getAllEntityStatus() {
        return Arrays.stream(EntityStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public static String getAllEntityStatusString() {
        return Arrays.stream(EntityStatus.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }
}
