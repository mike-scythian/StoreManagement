package nix.project.store.management.exceptions;

public class ValueExistsAlreadyException extends RuntimeException {
    public ValueExistsAlreadyException() {
        super("Such value already exists in database");
    }

}
