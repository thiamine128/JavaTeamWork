package network.post;

import java.util.Set;

public class DetailedPost extends Post {
    private Set<Comment> comments;


    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
