package oop.zsz.client;

import oop.zsz.page.Page;
import oop.zsz.post.Comment;
import oop.zsz.post.DetailedPost;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;
import oop.zsz.user.UserProfile;

import java.util.UUID;

/**
 * The interface Client event handler.
 */
public interface IClientEventHandler {
    /**
     * On login success.
     *
     * @param username  the username
     * @param token     the token
     * @param appClient the app client
     */
    void onLoginSuccess(String username, String token, AppClient appClient);

    /**
     * On login failed.
     *
     * @param error the error
     */
    void onLoginFailed(String error);

    /**
     * On register success.
     *
     * @param username the username
     */
    void onRegisterSuccess(String username);

    /**
     * On register failed.
     *
     * @param error the error
     */
    void onRegisterFailed(String error);

    /**
     * On publish post success.
     *
     * @param post the post
     */
    void onPublishPostSuccess(Post post);

    /**
     * On publish post failed.
     *
     * @param error the error
     */
    void onPublishPostFailed(String error);

    /**
     * On fetch post success.
     *
     * @param post the post
     */
    void onFetchPostSuccess(DetailedPost post);

    /**
     * On fetch post failed.
     *
     * @param error the error
     */
    void onFetchPostFailed(String error);

    /**
     * On comment publish success.
     *
     * @param comment the comment
     */
    void onCommentPublishSuccess(Comment comment);

    /**
     * On comment publish failed.
     *
     * @param error the error
     */
    void onCommentPublishFailed(String error);

    /**
     * On reply publish success.
     *
     * @param reply the reply
     */
    void onReplyPublishSuccess(Reply reply);

    /**
     * On reply publish failed.
     *
     * @param error the error
     */
    void onReplyPublishFailed(String error);

    /**
     * On fetch all posts success.
     *
     * @param postList the post list
     */
    void onFetchAllPostsSuccess(Page<Post> postList);

    /**
     * On fetch all posts failed.
     *
     * @param error the error
     */
    void onFetchAllPostsFailed(String error);

    /**
     * On search post success success.
     *
     * @param postList the post list
     */
    void onSearchPostSuccessSuccess(Page<Post> postList);

    /**
     * On search post failed.
     *
     * @param error the error
     */
    void onSearchPostFailed(String error);

    /**
     * On remove post success.
     */
    void onRemovePostSuccess();

    /**
     * On remove post failed.
     *
     * @param error the error
     */
    void onRemovePostFailed(String error);

    /**
     * On remove comment success.
     */
    void onRemoveCommentSuccess();

    /**
     * On remove comment failed.
     *
     * @param error the error
     */
    void onRemoveCommentFailed(String error);

    /**
     * On remove reply success.
     */
    void onRemoveReplySuccess();

    /**
     * On remove reply failed.
     *
     * @param error the error
     */
    void onRemoveReplyFailed(String error);

    /**
     * On upload portrait success.
     */
    void onUploadPortraitSuccess();

    /**
     * On upload portrait failed.
     *
     * @param data the data
     */
    void onUploadPortraitFailed(String data);

    /**
     * On fetch profile success.
     *
     * @param data the data
     */
    void onFetchProfileSuccess(UserProfile data);

    /**
     * On fetch profile failed.
     *
     * @param data the data
     */
    void onFetchProfileFailed(String data);

    /**
     * On update jigsaw success.
     */
    void onUpdateJigsawSuccess();

    /**
     * On update jigsaw failed.
     *
     * @param error the error
     */
    void onUpdateJigsawFailed(String error);

    /**
     * On update quiz success.
     */
    void onUpdateQuizSuccess();

    /**
     * On update quiz failed.
     *
     * @param error the error
     */
    void onUpdateQuizFailed(String error);

    /**
     * On like post success.
     */
    void onLikePostSuccess();

    /**
     * On like post failed.
     *
     * @param error the error
     */
    void onLikePostFailed(String error);

    /**
     * On dislike post success.
     */
    void onDislikePostSuccess();

    /**
     * On dislike post failed.
     *
     * @param error the error
     */
    void onDislikePostFailed(String error);

    /**
     * On check liked post success.
     *
     * @param post  the post
     * @param liked the liked
     */
    void onCheckLikedPostSuccess(UUID post, boolean liked);

    /**
     * On check liked post failed.
     *
     * @param error the error
     */
    void onCheckLikedPostFailed(String error);

    /**
     * On send verification code success.
     */
    void onSendVerificationCodeSuccess();

    /**
     * On send verification code failed.
     *
     * @param error the error
     */
    void onSendVerificationCodeFailed(String error);

    /**
     * On reset password success.
     */
    void onResetPasswordSuccess();

    /**
     * On reset password failed.
     *
     * @param error the error
     */
    void onResetPasswordFailed(String error);
}
