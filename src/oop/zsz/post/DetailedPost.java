package oop.zsz.post;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DetailedPost extends Post {
    private Set<Comment> comments;
    private List<UUID> images;

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public List<UUID> getImages() {
        return images;
    }

    public void setImages(List<UUID> images) {
        this.images = images;
    }
}
