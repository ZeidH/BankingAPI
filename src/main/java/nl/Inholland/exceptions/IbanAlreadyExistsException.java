package nl.Inholland.exceptions;

public class IbanAlreadyExistsException extends Exception {
    public IbanAlreadyExistsException(String message) {
        super(message);
    }
}
