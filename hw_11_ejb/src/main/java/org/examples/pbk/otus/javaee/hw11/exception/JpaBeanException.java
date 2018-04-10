package org.examples.pbk.otus.javaee.hw11.exception;

public class JpaBeanException extends Exception {
    public JpaBeanException() {
    }

    public JpaBeanException(String message) {
        super(message);
    }

    public JpaBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public JpaBeanException(Throwable cause) {
        super(cause);
    }
}
