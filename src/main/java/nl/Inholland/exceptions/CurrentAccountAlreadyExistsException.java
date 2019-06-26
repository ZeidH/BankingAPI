package nl.Inholland.exceptions;

public class CurrentAccountAlreadyExistsException extends Exception {

    public CurrentAccountAlreadyExistsException(String message) {
        super(message);
    }
}