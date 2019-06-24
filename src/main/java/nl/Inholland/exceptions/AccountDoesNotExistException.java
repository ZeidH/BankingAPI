package nl.Inholland.exceptions;

public class AccountDoesNotExistException extends Exception{
    public AccountDoesNotExistException(String message) {
        super(message);
    }
}
