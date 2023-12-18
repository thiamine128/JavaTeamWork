package network.post;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Comment {
    private UUID id;
    private UUID post;
    private String text;
    private String user;
    private Date createdDate;
    private Set<Reply> replies;

    public String getUser() {
        return user;
    }

    public UUID getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Set<Reply> getReplies() {
        return replies;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public UUID getPost() {
        return post;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setReplies(Set<Reply> replies) {
        this.replies = replies;
    }

    public void setPost(UUID post) {
        this.post = post;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
