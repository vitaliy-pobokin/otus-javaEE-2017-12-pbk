package org.examples.pbk.otus;

import org.examples.pbk.otus.decoders.MessageDecoder;
import org.examples.pbk.otus.encoders.ChatMessageEncoder;
import org.examples.pbk.otus.encoders.InfoMessageEncoder;
import org.examples.pbk.otus.encoders.JoinMessageEncoder;
import org.examples.pbk.otus.messages.ChatMessage;
import org.examples.pbk.otus.messages.InfoMessage;
import org.examples.pbk.otus.messages.JoinMessage;
import org.examples.pbk.otus.messages.Message;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Logger;

@ApplicationScoped
@ServerEndpoint(value = "/messages",
        encoders = {JoinMessageEncoder.class, InfoMessageEncoder.class, ChatMessageEncoder.class},
        decoders = MessageDecoder.class)
public class MessageWebSocketServer {

    private Logger logger = Logger.getLogger("Message Endpoint");

    @Inject
    private WebSocketSessionHandler sessionHandler;

    @OnOpen
    public void open(Session session) {
        // Log connected.
        logger.info("Connected " + session.getBasicRemote());
    }

    @OnClose
    public void close(Session session) {
        sessionHandler.removeSession(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        sessionHandler.removeSession(session);
    }

    @OnMessage
    public void handleMessage(Message message, Session session) {
        if (message instanceof JoinMessage) {
            logger.info("JoinMessage received: " + message);
            JoinMessage msg = (JoinMessage) message;
            session.getUserProperties().put("user", msg.getUser());
            sessionHandler.addSession(session);
            sessionHandler.sendToAllConnectedSessions(new InfoMessage(msg.getUser() + " has joined the chat!"));
        } else if (message instanceof ChatMessage) {
            String user = (String) session.getUserProperties().get("user");
            ChatMessage msg = (ChatMessage) message;
            if (user == null || !user.equals(msg.getFrom())) {
                logger.info("Unknown user: " + user);
            } else {
                sessionHandler.sendToAllConnectedSessions(msg);
            }
        }
    }
}
