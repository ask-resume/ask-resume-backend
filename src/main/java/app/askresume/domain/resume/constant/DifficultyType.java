package app.askresume.domain.resume.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum DifficultyType {

    EASY("easy"), MEDIUM("medium"), HARD("hard");

    private final String type;

    public String type() {
        return type;
    }

    DifficultyType(String type) {
        this.type = type;
    }

    public static boolean isDifficultyType(String type) {
        List<DifficultyType> difficultyTypes = Arrays.stream(DifficultyType.values())
                .filter(difficultyType -> difficultyType.name().equalsIgnoreCase(type)).toList();
        return difficultyTypes.size() != 0;
    }


}