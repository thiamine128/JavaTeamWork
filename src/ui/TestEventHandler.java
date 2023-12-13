package ui;

import controller.PostController;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import oop.zsz.client.AppClient;
import oop.zsz.client.IClientEventHandler;
import oop.zsz.page.Page;
import oop.zsz.post.Comment;
import oop.zsz.post.DetailedPost;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;
import oop.zsz.user.UserProfile;
import post.CommentBox;
import post.PostBox;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.concurrent.CancellationException;

public class TestEventHandler implements IClientEventHandler {

    public static UIManager manager;
    @Override
    public void onLoginSuccess(String username, String token, AppClient appClient) {
        appClient.setUserToken(token);
        System.out.println("Login success: " + username);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                AudioManager.setButtonAudio(4);
                UIManager.personController.username.setText(username);
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
                                                    AudioManager.cancelMusic();
                                                    manager.toMainFrame(true);
                                                    UINetwork.fetchProfile(username);
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
        UIManager.editorController.resetEditor();
    }

    private Set <CommentBox> boxSet;
    @Override
    public void onFetchPostSuccess(DetailedPost post) {
        System.out.println("Fetch post:");
        System.out.println(post.getText());

        Platform.runLater(new Runnable() {
            @Override
            public void run() {



                UIManager.postViewController.authorProtrait.setImage(
                        new Image("http://116.204.117.136/portrait/"+post.getPoster()+".png"));

                UIManager.postViewController.postID = post.getId();

                UIManager.postViewController.authorText.setText(post.getPoster());
                UIManager.postViewController.title.setText(post.getTitle());
                UIManager.postViewController.postContent.getEngine().loadContent(post.getText());
                Set<Comment> commentSet = post.getComments();
                boxSet = new HashSet<>();
                for (Comment comment : commentSet){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(comment.getCreatedDate());
                    CommentBox commentBox = new CommentBox(comment.getId(), comment.getUser(), comment.getText(),
                            "./resources/icon.png", calendar.get(Calendar.YEAR)+
                            "年"+(calendar.get(Calendar.MONTH)+1)+"月"
                            +calendar.get(Calendar.DAY_OF_MONTH)+"日", post.getProvince());
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

                UIManager.postViewController.resetImageBox();
                for (UUID id : post.getImages()){
                    System.out.println("fetch image: "+id);
                    try {
                        UIManager.postViewController.addImage(UINetwork.getImageUrl(id));
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
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

    private void postsSet(Page<Post> postList){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                UIManager.postController.setTotalNum(postList.getTotalPages());
                System.out.println(postList.getTotalPages());
                UIManager.postController.postMainVbox.getChildren().clear();
                for (Post p0 : postList.getList()){

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(p0.getCreatedDate());
                    int likesNum = 0;
                    if (p0.getLikes() != null) likesNum = Math.toIntExact(p0.getLikes());
                    PostBox postbox = new PostBox(p0.getId(), p0.getTitle(), p0.getPoster(), p0.getProvince(),
                            calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH)+1)+"月"
                                    +calendar.get(Calendar.DAY_OF_MONTH)+"日", likesNum);
                    UIManager.postController.postMainVbox.getChildren().addAll(postbox);
                    UIManager.postController.postMainVbox.getChildren().addAll(new Separator());
                }
            }
        });
    }
    @Override
    public void onFetchAllPostsSuccess(Page<Post> postList) {
        postsSet(postList);
    }

    @Override
    public void onFetchAllPostsFailed(String error) {
        System.out.println("Fetch all posts failed: ");
        System.out.println(error);
    }

    @Override
    public void onFetchPostInProvinceSuccess(Page<Post> postList) {
        postsSet(postList);
    }

    @Override
    public void onFetchPostInProvinceFailed(String error) {

    }

    @Override
    public void onRemovePostSuccess() {
        System.out.println("remove post success");
    }

    @Override
    public void onRemovePostFailed(String error) {

    }

    @Override
    public void onUploadPortraitSuccess() {
        System.out.println("upload image success");
    }

    @Override
    public void onUploadPortraitFailed(String data) {

    }

    @Override
    public void onFetchProfileSuccess(UserProfile data) {
        System.out.println("fetch profile success");
        System.out.println(data.getHistory());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                if (data.getJigsawFlag() != null){
                    UIManager.personController.puzzleTrophy.setImage(
                            new Image("./resources/personImage/puzzle_trophy.png"));
                    int timer = Math.toIntExact(data.getJigsawTime());
                    UIManager.personController.puzzleTime.setText(String.format("%02d:%02d", timer/60, timer%60));
                }else {
                    UIManager.personController.puzzleTrophy.setImage(
                            new Image("./resources/personImage/puzzle_trophy_null.png"));
                    UIManager.personController.puzzleTime.setText("");
                }
                if (data.getQuizFlag() != null) UIManager.personController.questionTrophy.setImage(
                        new Image("./resources/personImage/question_trophy.png"));
                else UIManager.personController.questionTrophy.setImage(
                        new Image("./resources/personImage/question_trophy_null.png"));

                UIManager.personController.username.setText(data.getUsername());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data.getRegisteredDate());
                UIManager.personController.loginDate.setText(
                        calendar.get(Calendar.YEAR)+"."+(calendar.get(Calendar.MONTH)+1)+"."
                                +calendar.get(Calendar.DAY_OF_MONTH));
                for (String key : data.getHistory().keySet()){
                    UIManager.personController.setProvinceColor(key, Math.toIntExact(data.getHistory().get(key)));
                }
                UIManager.personController.addChartInfo(data.getHistory());
                if (data.getPortrait() != null){
                    if (data.getUsername().equals(UIManager.mainController.frameUsername.getText())){
                        UIManager.mainController.profilePhoto.setImage(new Image("http://116.204.117.136/portrait/"
                                + data.getPortrait()));
                    }
                    UIManager.personController.protraitImage.setImage(new Image("http://116.204.117.136/portrait/"
                            + data.getPortrait()));
                }else{
                    if (data.getUsername().equals(UIManager.mainController.frameUsername.getText())){
                        UIManager.mainController.profilePhoto.setImage(new Image("resources/personImage/uncertainty.png"));
                    }
                    UIManager.personController.protraitImage.setImage(new Image("resources/personImage/uncertainty.png"));
                    File file = new File("src/resources/personImage/uncertainty.png");
                    System.out.println(file.getAbsoluteFile().toPath());
                    try {
                        UINetwork.uploadProtrait(file.getAbsoluteFile().toPath());
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }

    @Override
    public void onFetchProfileFailed(String data) {

    }

    @Override
    public void onUpdateJigsawSuccess() {
        System.out.println("update jigsaw success");
    }

    @Override
    public void onUpdateJigsawFailed(String error) {
        System.out.println("update jigsaw failed");
    }

    @Override
    public void onUpdateQuizSuccess() {
        System.out.println("update quiz success");
    }

    @Override
    public void onUpdateQuizFailed(String error) {
        System.out.println("update quiz failed");
    }

    @Override
    public void onLikePostSuccess() {
        System.out.println("like post success");
    }

    @Override
    public void onLikePostFailed(String error) {
        System.out.println("like post failed");
    }

    @Override
    public void onDislikePostSuccess() {
        System.out.println("dislike post success");
    }

    @Override
    public void onDislikePostFailed(String error) {
        System.out.println("dislike post failed");
    }

    @Override
    public void onCheckLikedPostSuccess(UUID post, boolean liked) {
        System.out.println("check liked success: "+liked);
        if (liked) UIManager.postViewController.setRedHeart();
        else UIManager.postViewController.setWhiteHeart();
    }

    @Override
    public void onCheckLikedPostFailed(String error) {
        System.out.println("check liked post failed");
    }

}