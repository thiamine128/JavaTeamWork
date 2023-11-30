package oop.zsz.post;

import java.util.UUID;

public class Reply {
    private UUID id;
    private UUID comment;
    private String user;
    private String replyTo;
    private String text;

    public String getReplyTo() {
        return replyTo;
    }

    public String getText() {
        return text;
    }

    public UUID getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public UUID getComment() {
        return comment;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setComment(UUID comment) {
        this.comment = comment;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
