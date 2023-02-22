package nix.project.store.management.exceptions;

public class NotEnoughLeftoversException extends RuntimeException {

    public NotEnoughLeftoversException(){
        super("ERROR! Not enough left in stock");
    }
}
