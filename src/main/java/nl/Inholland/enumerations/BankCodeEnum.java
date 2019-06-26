package nl.Inholland.enumerations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BankCodeEnum {

    INHO("INHO"),

    FAKE("FAKE");

    private String value;

    BankCodeEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static BankCodeEnum fromValue(String text) {
        for (BankCodeEnum b : BankCodeEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
