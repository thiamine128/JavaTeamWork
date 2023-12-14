package oop.zsz.post;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DetailedPost extends Post {
    private Set<Comment> comments;


    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
