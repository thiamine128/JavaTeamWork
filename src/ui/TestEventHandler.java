package ui;

import controller.PostController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import oop.zsz.client.AppClient;
import oop.zsz.client.IClientEventHandler;
import oop.zsz.post.Comment;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;
import post.CommentBox;
import post.PostBox;

import java.util.*;
import java.util.concurrent.CancellationException;

public class TestEventHandler implements IClientEventHandler {

    public static UIManager manager;
    @Override
    public void onLoginSuccess(String username, String token, AppClient appClient) {
        appClient.setUserToken(token);
        System.out.println("Login success: " + username);

        System.out.println(UIManager.loginController);

        UIManager.mainController.frameUsername.setText(username);
        UIManager.loginController.loginHint.setText("登录成功");
        UIManager.loginController.loginHint.setOpacity(0.8);
        //登录成功入口
        UIAnimation.timer(2000, event114514 -> {
            UIManager.loginController.startHint.setImage(new Image("resources/loginImage/startButton2.png"));
            UIAnimation.setBlackMask(UIManager.loginController.startHint, null, 600);
            UIAnimation.fadeAnimation(UIManager.loginController.loginPane,null, false);

            UIAnimation.vectorMove(UIManager.loginController.loginMainTitle, 0, 100, 1000, event00->{

                UIAnimation.timer(2000, event0->{

                    UIAnimation.setBlackMask(UIManager.loginController.loginMainTitleWhite, null, 1000);
                    UIAnimation.setBlackMask(UIManager.loginController.loginFrameMask, evente -> {

                        UIAnimation.timer(1500, eventt->{
                            UIAnimation.fadeAnimation(UIManager.loginController.loginMainTitleWhite, event -> {
                                        try {
                                            manager.toMainFrame();
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    },
                                    false, 800);
                        });
                    }, 1000);
                });
            });
        });
    }

    @Override
    public void onLoginFailed(String error) {
        System.out.println("Login failed:");
        System.out.println(error);
        UIManager.loginController.loginHint.setText("登录失败");
        UIAnimation.timer(1000, event -> UIManager.loginController.loginPane.setMouseTransparent(false));
    }

    @Override
    public void onRegisterSuccess(String username) {
        System.out.println("Register success: " + username);
        UIManager.loginController.loginHint.setText("注册成功");
        UIAnimation.timer(1000, event -> UIManager.loginController.loginPane.setMouseTransparent(false));
    }

    @Override
    public void onRegisterFailed(String error) {
        System.out.println("Register failed:");
        System.out.println(error);
        UIManager.loginController.loginHint.setText("注册失败");
        UIAnimation.timer(1000, event -> UIManager.loginController.loginPane.setMouseTransparent(false));
    }

    @Override
    public void onPublishPostSuccess(Post post) {
        System.out.println("Post published: " + post.getId());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UIAnimation.setBlackMask(UIManager.editorController.postSuccessHint, event -> {
                    UIAnimation.fadeAnimation(UIManager.editorController.postSuccessHint, event1 -> {
                        try {
                            manager.toProvinceFrame(true);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }, false, 600);
                }, 2000);
            }
        });

    }

    @Override
    public void onPublishPostFailed(String error) {
        System.out.println("Failed to publish post:");
        System.out.println("error");
    }

    private Set <CommentBox> boxSet;
    @Override
    public void onFetchPostSuccess(Post post) {
        System.out.println("Fetch post:");
        System.out.println(post.getText());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                UIManager.postViewController.postID = post.getId();

                UIManager.postViewController.authorText.setText(post.getPoster());
                UIManager.postViewController.title.setText(post.getTitle());
                UIManager.postViewController.postContent.getEngine().loadContent(post.getText());
                Set<Comment> commentSet = post.getComments();
                boxSet = new HashSet<>();
                for (Comment comment : commentSet){
                    CommentBox commentBox = new CommentBox(comment.getId(), comment.getUser(), comment.getText(),
                            "./resources/icon.png", "2023", post.getProvince());
                    for (Reply reply : comment.getReplies()){
                        if (reply.getReplyTo() == null)
                            commentBox.addReply(reply.getId(), reply.getUser(), reply.getText());
                        else commentBox.addReply(reply.getId(), reply.getUser(), reply.getReplyTo(), reply.getText());
                    }
                    boxSet.add(commentBox);
                    UIManager.postViewController.mainVBox.getChildren().addAll(commentBox);
                }

                UIManager.postViewController.postViewCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        try {
                            for (Node comment : boxSet)
                                UIManager.postViewController.mainVBox.getChildren().removeAll(comment);
                            manager.toPostFrame(true);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

                try {
                    manager.toPostViewFrame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }

    @Override
    public void onFetchPostFailed(String error) {
        System.out.println("Failed to fetch post:");
        System.out.println(error);
    }

    @Override
    public void onCommentPublishSuccess(Comment comment) {
        System.out.println("Comment publish success : " + comment.getId());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CommentBox commentBox = new CommentBox(comment.getId(), comment.getUser(), comment.getText(),
                        "./resources/icon.png", "2023", "BUAA");
                UIManager.postViewController.mainVBox.getChildren().addAll(commentBox);
                boxSet.add(commentBox);
            }
        });
    }

    @Override
    public void onCommentPublishFailed(String error) {
        System.out.println("Failed to publish comment :");
        System.out.println(error);
    }

    public static UUID commentID;
    @Override
    public void onReplyPublishSuccess(Reply reply) {
        System.out.println("Reply publish success : " + reply.getId());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (CommentBox comment : boxSet){
                    if (comment.commentID.equals(commentID)){
                        if (reply.getReplyTo() == null)
                            comment.addReply(reply.getId(), reply.getUser(), reply.getText());
                        else
                            comment.addReply(reply.getId(), reply.getUser(), reply.getReplyTo(), reply.getText());

                        break;
                    }
                }
            }
        });

    }

    @Override
    public void onReplyPublishFailed(String error) {
        System.out.println("Failed to publish reply :");
        System.out.println(error);
    }

    @Override
    public void onFetchAllPostsSuccess(List<Post> postList) {
        System.out.println("Fetch posts success.");

        for (var post : postList) {
            System.out.println(post.getId().toString());
            if (!post.getComments().isEmpty()) {
                System.out.println("with comments:");
                for (var comment : post.getComments()) {
                    System.out.println(comment.getId());
                    if (!comment.getReplies().isEmpty()) {
                        System.out.println("with replies:");
                        for (var reply : comment.getReplies()) {
                            System.out.println(reply.getId());
                        }
                    }
                }
            }
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UIManager.postController.postMainVbox.getChildren().clear();
                for (Post p0 : postList){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(p0.getCreatedDate());
                    PostBox postbox = new PostBox(p0.getId(), p0.getTitle(), p0.getPoster(), p0.getProvince(),
                            calendar.get(Calendar.YEAR)+"年"+calendar.get(Calendar.MONTH)+"月"
                                    +calendar.get(Calendar.DAY_OF_MONTH)+"日");
                    UIManager.postController.postMainVbox.getChildren().addAll(postbox);
                }
            }
        });

    }

    @Override
    public void onFetchAllPostsFailed(String error) {
        System.out.println("Fetch all posts failed: ");
        System.out.println(error);
    }

    @Override
    public void onFetchPostInProvinceSuccess(List<Post> postList) {

    }

    @Override
    public void onFetchPostInProvinceFailed(String data) {

    }

}
