package at.ac.tuwien.sepm.assignment.individual.exception;

public class PersistenceDataException extends RuntimeException {
    public PersistenceDataException() { super(); }
    public PersistenceDataException(String message) { super(message); }
    public PersistenceDataException(Throwable cause) { super(cause); }
    public PersistenceDataException(String message, Throwable cause) { super(message, cause); }
}
