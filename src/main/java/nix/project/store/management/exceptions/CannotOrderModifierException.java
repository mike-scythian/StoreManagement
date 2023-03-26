package nix.project.store.management.exceptions;

public class CannotOrderModifierException extends RuntimeException{

    public CannotOrderModifierException(){

        super("ERROR! The order is done");
    }
}
