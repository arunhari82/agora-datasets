package com.demo.agora_datasets.models;
import java.util.Objects;

public class SlackMessage {
    
    private String text;

    public SlackMessage() {
    }

    public SlackMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SlackMessage text(String text) {
        setText(text);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof SlackMessage)) {
            return false;
        }
        SlackMessage slackMessage = (SlackMessage) o;
        return Objects.equals(text, slackMessage.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(text);
    }

    @Override
    public String toString() {
        return "{" +
            " text='" + getText() + "'" +
            "}";
    }

}
