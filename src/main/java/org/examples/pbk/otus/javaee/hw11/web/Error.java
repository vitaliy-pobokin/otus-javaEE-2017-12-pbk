package org.examples.pbk.otus.javaee.hw11.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named("error")
@RequestScoped
public class Error implements Serializable {

    private String message;

    public Error() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
