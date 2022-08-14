package services;

public class EmptyFieldsException extends Exception{
    public EmptyFieldsException() {
        super();
    }

    public EmptyFieldsException(String message) {
        super(message);
    }

    public EmptyFieldsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyFieldsException(Throwable cause) {
        super(cause);
    }
}
