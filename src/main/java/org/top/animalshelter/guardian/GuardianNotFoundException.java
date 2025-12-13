package org.top.animalshelter.guardian;

public class GuardianNotFoundException extends RuntimeException {
    public GuardianNotFoundException(String message) {
        super(message);
    }
}
