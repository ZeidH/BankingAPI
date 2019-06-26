package nl.Inholland.exceptions;

public class DailyLimitSurpassedException extends Exception {

    public DailyLimitSurpassedException(String message) {
        super(message);
    }
}