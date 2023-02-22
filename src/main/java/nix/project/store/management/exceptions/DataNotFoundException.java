package nix.project.store.management.exceptions;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(){

        super("ERROR! Data not found");
    }

}
