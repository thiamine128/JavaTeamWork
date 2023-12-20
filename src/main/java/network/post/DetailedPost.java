package network.post;

import java.util.Set;

/**
 * The type Detailed post.
 */
public class DetailedPost extends Post {
    private Set<Comment> comments;


    /**
     * Get comments.
     *
     * @return the comments
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Set comments.
     *
     * @param comments the comments
     */
    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

}
