package oop.zsz.post;

import java.util.*;

public class Post {
    private UUID id;
    private String poster;
    private String title;
    private String province;
    private String text;
    private Date createdDate;
    private Long likes;

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

    public void setLikes(Long likes) {
        this.likes = likes;
    }

    public Long getLikes() {
        return likes;
    }
}
