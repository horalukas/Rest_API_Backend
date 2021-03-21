package cz.cvut.fit.horaluk1.gradle.exception;

public class ExistingEntityException extends IllegalArgumentException {
    public ExistingEntityException(){super("Entity already exists");}
}
