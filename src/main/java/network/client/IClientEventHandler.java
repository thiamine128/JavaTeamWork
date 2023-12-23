package network.client;

import network.page.Page;
import network.post.Comment;
import network.post.DetailedPost;
import network.post.Post;
import network.post.Reply;
import network.user.UserProfile;

import java.util.UUID;

/**
 * The interface Client event handler.
 */
public interface IClientEventHandler {
    /**
     * Called when login succeeded.
     *
     * @param username  the username of login
     * @param token     the user token for authentication
     * @param appClient the app client performing login
     */
    void onLoginSuccess(String username, String token, AppClient appClient);

    /**
     * Called when login failed.
     *
     * @param error the error from the server
     */
    void onLoginFailed(String error);

    /**
     * Called when register succeeded.
     *
     * @param username the username of registration
     */
    void onRegisterSuccess(String username);

    /**
     * Called when register failed.
     *
     * @param error the error from the server
     */
    void onRegisterFailed(String error);

    /**
     * Called when publish post succeeded.
     *
     * @param post the post published
     */
    void onPublishPostSuccess(Post post);

    /**
     * Called when publish post failed.
     *
     * @param error the error from the server
     */
    void onPublishPostFailed(String error);

    /**
     * Called when fetch post succeeded.
     *
     * @param post the post fetched
     */
    void onFetchPostSuccess(DetailedPost post);

    /**
     * Called when fetch post failed.
     *
     * @param error the error from the server
     */
    void onFetchPostFailed(String error);

    /**
     * Called when comment publish succeeded.
     *
     * @param comment the comment published
     */
    void onCommentPublishSuccess(Comment comment);

    /**
     * Called when comment publish failed.
     *
     * @param error the error from the server
     */
    void onCommentPublishFailed(String error);

    /**
     * Called when reply publish succeeded.
     *
     * @param reply the reply published
     */
    void onReplyPublishSuccess(Reply reply);

    /**
     * Called when reply publish failed.
     *
     * @param error the error from the server
     */
    void onReplyPublishFailed(String error);

    /**
     * Called when fetch all posts succeeded.
     *
     * @param postList the post list fetched
     */
    void onFetchAllPostsSuccess(Page<Post> postList);

    /**
     * Called when fetch all posts failed.
     *
     * @param error the error from the server
     */
    void onFetchAllPostsFailed(String error);

    /**
     * Called when search post succeeded succeeded.
     *
     * @param postList the post list of search result
     */
    void onSearchPostSuccessSuccess(Page<Post> postList);

    /**
     * Called when search post failed.
     *
     * @param error the error from the server
     */
    void onSearchPostFailed(String error);

    /**
     * Called when remove post succeeded.
     */
    void onRemovePostSuccess();

    /**
     * Called when remove post failed.
     *
     * @param error the error from the server
     */
    void onRemovePostFailed(String error);

    /**
     * Called when remove comment succeeded.
     */
    void onRemoveCommentSuccess();

    /**
     * Called when remove comment failed.
     *
     * @param error the error from the server
     */
    void onRemoveCommentFailed(String error);

    /**
     * Called when remove reply succeeded.
     */
    void onRemoveReplySuccess();

    /**
     * Called when remove reply failed.
     *
     * @param error the error from the server
     */
    void onRemoveReplyFailed(String error);

    /**
     * Called when upload portrait succeeded.
     */
    void onUploadPortraitSuccess();

    /**
     * Called when upload portrait failed.
     *
     * @param data the error from the server
     */
    void onUploadPortraitFailed(String data);

    /**
     * Called when fetch profile succeeded.
     *
     * @param data the profile fetched
     */
    void onFetchProfileSuccess(UserProfile data);

    /**
     * Called when fetch profile failed.
     *
     * @param data the error from the server
     */
    void onFetchProfileFailed(String data);

    /**
     * Called when update jigsaw succeeded.
     */
    void onUpdateJigsawSuccess();

    /**
     * Called when update jigsaw failed.
     *
     * @param error the error from the server
     */
    void onUpdateJigsawFailed(String error);

    /**
     * Called when update quiz succeeded.
     */
    void onUpdateQuizSuccess();

    /**
     * Called when update quiz failed.
     *
     * @param error the error from the server
     */
    void onUpdateQuizFailed(String error);

    /**
     * Called when like post succeeded.
     */
    void onLikePostSuccess();

    /**
     * Called when like post failed.
     *
     * @param error the error from the server
     */
    void onLikePostFailed(String error);

    /**
     * Called when dislike post succeeded.
     */
    void onDislikePostSuccess();

    /**
     * Called when dislike post failed.
     *
     * @param error the error from the server
     */
    void onDislikePostFailed(String error);

    /**
     * Called when check liked post succeeded.
     *
     * @param post  the post checked
     * @param liked the liked, true for liked and vice versa
     */
    void onCheckLikedPostSuccess(UUID post, boolean liked);

    /**
     * Called when check liked post failed.
     *
     * @param error the error from the server
     */
    void onCheckLikedPostFailed(String error);

    /**
     * Called when send verification code succeeded.
     */
    void onSendVerificationCodeSuccess();

    /**
     * Called when send verification code failed.
     *
     * @param error the error from the server
     */
    void onSendVerificationCodeFailed(String error);

    /**
     * Called when reset password succeeded.
     */
    void onResetPasswordSuccess();

    /**
     * Called when reset password failed.
     *
     * @param error the error from the server
     */
    void onResetPasswordFailed(String error);
}
