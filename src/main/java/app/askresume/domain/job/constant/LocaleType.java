package app.askresume.domain.job.constant;

import java.util.Arrays;
import java.util.List;

public enum LocaleType {

    KO, EN, JP;

    public static LocaleType from(String type) {
        return LocaleType.valueOf(type.toUpperCase());
    }

    public static boolean isLocaleType(String type) {
        List<LocaleType> localeTypes = Arrays.stream(LocaleType.values())
                .filter(localeType -> localeType.name().equalsIgnoreCase(type)).toList();
        return localeTypes.size() != 0;
    }

}
