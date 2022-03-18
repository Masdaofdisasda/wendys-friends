package at.ac.tuwien.sepm.assignment.individual.exception;

public class DataValidationException extends RuntimeException {
    public DataValidationException() { super(); }
    public DataValidationException(String message) { super(message); }
    public DataValidationException(Throwable cause) { super(cause); }
    public DataValidationException(String message, Throwable cause) { super(message, cause); }
}
