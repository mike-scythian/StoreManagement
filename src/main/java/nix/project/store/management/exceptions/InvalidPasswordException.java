package nix.project.store.management.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(){
        super("ERROR! Invalid password. Try again!");
    }
}
