package org.examples.pbk.otus.messages;

public class InfoMessage extends Message {
    private String text;

    public InfoMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "InfoMessage{" +
                "text='" + text + '\'' +
                '}';
    }
}
