package oop.zsz.client;

import oop.zsz.page.Page;
import oop.zsz.post.Comment;
import oop.zsz.post.DetailedPost;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;
import oop.zsz.user.UserProfile;

import java.util.UUID;

public interface IClientEventHandler {
    void onLoginSuccess(String username, String token, AppClient appClient);
    void onLoginFailed(String error);
    void onRegisterSuccess(String username);
    void onRegisterFailed(String error);

    void onPublishPostSuccess(Post post);
    void onPublishPostFailed(String error);

    void onFetchPostSuccess(DetailedPost post);
    void onFetchPostFailed(String error);

    void onCommentPublishSuccess(Comment comment);
    void onCommentPublishFailed(String error);

    void onReplyPublishSuccess(Reply reply);
    void onReplyPublishFailed(String error);
    void onFetchAllPostsSuccess(Page<Post> postList);
    void onFetchAllPostsFailed(String error);

    void onSearchPostSuccessSuccess(Page<Post> postList);

    void onSearchPostFailed(String error);

    void onRemovePostSuccess();

    void onRemovePostFailed(String error);

    void onUploadPortraitSuccess();

    void onUploadPortraitFailed(String data);

    void onFetchProfileSuccess(UserProfile data);

    void onFetchProfileFailed(String data);
    void onUpdateJigsawSuccess();
    void onUpdateJigsawFailed(String error);
    void onUpdateQuizSuccess();
    void onUpdateQuizFailed(String error);
    void onLikePostSuccess();
    void onLikePostFailed(String error);
    void onDislikePostSuccess();
    void onDislikePostFailed(String error);
    void onCheckLikedPostSuccess(UUID post, boolean liked);
    void onCheckLikedPostFailed(String error);
    void onSendVerificationCodeSuccess();
    void onSendVerificationCodeFailed(String error);
    void onResetPasswordSuccess();
    void onResetPasswordFailed(String error);
}
