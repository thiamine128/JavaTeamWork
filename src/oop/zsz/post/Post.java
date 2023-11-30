package oop.zsz.post;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class Post {
    private UUID id;
    private String poster;
    private String title;
    private String province;
    private String text;
    private Date createdDate;
    private Set<Comment> comments;

    public UUID getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public String getPoster() {
        return poster;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCreatedDate(Date date) {
        this.createdDate = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
