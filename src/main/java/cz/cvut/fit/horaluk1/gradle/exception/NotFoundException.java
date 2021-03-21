package cz.cvut.fit.horaluk1.gradle.exception;

public class NotFoundException extends IllegalArgumentException {
    public NotFoundException(){super("Entity not found");}
}
