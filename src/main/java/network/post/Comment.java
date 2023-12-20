package network.post;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * The comment entity.
 */
public class Comment {
    private UUID id;
    private UUID post;
    private String text;
    private String user;
    private Date createdDate;
    private Set<Reply> replies;

    /**
     * Get user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
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
     * Get text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Get replies.
     *
     * @return the replies
     */
    public Set<Reply> getReplies() {
        return replies;
    }

    /**
     * Get created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Get post.
     *
     * @return the post
     */
    public UUID getPost() {
        return post;
    }

    /**
     * Set id.
     *
     * @param id the id
     */
    public void setId(UUID id) {
        this.id = id;
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
     * Set text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Set replies.
     *
     * @param replies the replies
     */
    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }

    /**
     * Set post.
     *
     * @param post the post
     */
    public void setPost(UUID post) {
        this.post = post;
    }

    /**
     * Set created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
