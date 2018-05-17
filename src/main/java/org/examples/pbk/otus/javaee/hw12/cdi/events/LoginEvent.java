package org.examples.pbk.otus.javaee.hw12.cdi.events;

import org.examples.pbk.otus.javaee.hw12.model.Credentials;

import java.time.LocalDateTime;

public class LoginEvent {
    private Credentials credentials;
    private String ipAddress;
    private LocalDateTime eventDateTime;

    public LoginEvent(Credentials credentials, String ipAddress) {
        this.credentials = credentials;
        this.ipAddress = ipAddress;
        this.eventDateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "LoginEvent{" +
                "credentials=" + credentials +
                ", ipAddress='" + ipAddress + '\'' +
                ", eventDateTime=" + eventDateTime +
                '}';
    }
}
