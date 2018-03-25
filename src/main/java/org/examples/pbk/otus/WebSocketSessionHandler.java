package org.examples.pbk.otus;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ApplicationScoped
public class WebSocketSessionHandler {
    private Set<Session> sessions = new CopyOnWriteArraySet<Session>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToAllConnectedSessions(String message) {
        sessions.stream().forEach(session -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
