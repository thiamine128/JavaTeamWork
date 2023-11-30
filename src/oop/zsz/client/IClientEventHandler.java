package oop.zsz.client;

import oop.zsz.post.Comment;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;

import java.util.List;
import java.util.UUID;

public interface IClientEventHandler {
    void onLoginSuccess(String username, String token, AppClient appClient);
    void onLoginFailed(String error);
    void onRegisterSuccess(String username);
    void onRegisterFailed(String error);

    void onPublishPostSuccess(Post post);
    void onPublishPostFailed(String error);

    void onFetchPostSuccess(Post post);
    void onFetchPostFailed(String error);

    void onCommentPublishSuccess(Comment comment);
    void onCommentPublishFailed(String error);

    void onReplyPublishSuccess(Reply reply);
    void onReplyPublishFailed(String error);
    void onFetchAllPostsSuccess(List<Post> postList);
    void onFetchAllPostsFailed(String error);

    void onFetchPostInProvinceSuccess(List<Post> postList);

    void onFetchPostInProvinceFailed(String data);

    void onRemovePostSuccess();

    void onRemovePostFailed(String data);
}
