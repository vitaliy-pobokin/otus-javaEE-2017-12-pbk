package org.examples.pbk.otus;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ApplicationScoped
@ServerEndpoint("/messages")
public class MessageWebSocketServer {

    @Inject
    private WebSocketSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
        try {
            session.getBasicRemote().sendText("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        sessionHandler.sendToAllConnectedSessions(message);
    }
}
