package network.post;

import java.util.UUID;

/**
 * The type Reply.
 */
public class Reply {
    private UUID id;
    private UUID comment;
    private String user;
    private String replyTo;
    private String text;

    /**
     * Get reply to.
     *
     * @return the reply to
     */
    public String getReplyTo() {
        return replyTo;
    }

    /**
     * Get text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Get id.
     *
     * @return the id
     */
    public UUID getId() {
        return id;
    }

    /**
     * Get user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Get comment.
     *
     * @return the comment
     */
    public UUID getComment() {
        return comment;
    }

    /**
     * Set reply to.
     *
     * @param replyTo the reply to
     */
    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    /**
     * Set text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set comment.
     *
     * @param comment the comment
     */
    public void setComment(UUID comment) {
        this.comment = comment;
    }

    /**
     * Set user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
    }
}
