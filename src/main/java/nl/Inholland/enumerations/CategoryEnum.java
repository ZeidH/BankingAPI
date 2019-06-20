package nl.Inholland.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum CategoryEnum {
    LIVING("Living"),

    ENTERTAINMENT("Entertainment"),

    FOOD("Food"),

    TRANSPORT("Transport"),

    SAVING("Saving"),

    OTHER("Other");

    private String value;

    CategoryEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static CategoryEnum fromValue(String text) {
        for (CategoryEnum b : CategoryEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}