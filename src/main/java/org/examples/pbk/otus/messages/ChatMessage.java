package org.examples.pbk.otus.messages;

import java.time.Instant;

public class ChatMessage extends Message {
    private String from;
    private String to;
    private String text;
    private Instant date;

    public ChatMessage(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = Instant.now();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getText() {
        return text;
    }

    public Instant getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
