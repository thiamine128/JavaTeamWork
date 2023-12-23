package ui;

import network.client.AppClient;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * functions when using network.
 */
public class UINetwork {
    //将host参数改为后端的部署ip
    private static AppClient appClient = new AppClient("http", "203.33.224.165", 8081, new AppClientEventHandler());
    //private static AppClient appClient = new AppClient("http", "10.193.220.242", 8080, new AppClientEventHandler());

    //重置manager
    private static void refreshUIManager(UIManager manager){
        AppClientEventHandler.manager = manager;
    }

    /**
     * Reset password of current user.
     *
     * @param email    the email address.
     * @param code     the verification code.
     * @param password new password.
     */
    public static void resetPassword(String email, String code, String password){
        appClient.resetPassword(email, code, password);
    }

    /**
     * Try to login.
     *
     * @param username the username.
     * @param password the password.
     */
//尝试登录
    public static void trylogin(String username, String password){
        if (username.contains("@")) appClient.loginEmail(username, password);
        else appClient.login(username, password);
    }

    /**
     * Try to register.
     *
     * @param username the username.
     * @param password the password.
     * @param email    the email address.
     * @param code     the verification code.
     */
//尝试注册
    public static void tryRegister(String username, String password, String email, String code){
        appClient.register(username, password, email, code);
    }

    /**
     * Send email.
     *
     * @param email the email address.
     */
    public static void sendEmail(String email){
        System.out.println("try to send email to "+email);
        appClient.requestVerificationCode(email);
    }

    /**
     * Publish post.
     *
     * @param title    the title of this post.
     * @param province the province of this post.
     * @param content  the content of this post.
     * @param pathList the uploaded image list.
     */
//发帖子
    public static void publishPost(String title, String province, String content, List<Path> pathList) {
        appClient.publishPost(title, province, content, pathList);
    }

    /**
     * Add comment.
     *
     * @param postID  the id of this post.
     * @param content the content of this comment.
     */
//加评论
    public static void addComment(UUID postID, String content){
        appClient.publishComment(postID, content);
    }


    /**
     * Add comment reply.
     *
     * @param commentID   the id of this comment.
     * @param content     the content of reply.
     * @param repliedName the replied name.
     */
//评论回复：repliedName为null时，表示不回复任何人
    public static void addCommentReply(UUID commentID, String content, String repliedName){
        appClient.publishReply(commentID, content, repliedName);
    }

    /**
     * Fetch post.
     *
     * @param postID the id of this post.
     */
//根据ID获取帖子
    public static void fetchPost(UUID postID){
        appClient.fetchPost(postID);
    }

    /**
     * Fetch all posts.
     *
     * @param pageNum  the number of pages.
     * @param pageSize the size of one page.
     */
//获取所有帖子
    public static void fetchAllPost(int pageNum, int pageSize){
        appClient.fetchAllPost(pageNum, pageSize);
    }

    /**
     * Fetch posts on the basis of province.
     *
     * @param pageNum      the number of pages.
     * @param pageSize     the size of one page.
     * @param provinceName the name of province.
     * @param keyword      keyword.
     */
    public static void fetchPostInProvince(int pageNum, int pageSize, String provinceName, String keyword){
        appClient.searchPost(provinceName, keyword, pageNum, pageSize);
    }

    /**
     * Remove some post.
     *
     * @param postID the id of this post.
     */
//删除指定帖子
    public static void removePost(UUID postID){
        appClient.removePost(postID);
    }

    /**
     * Remove comment.
     *
     * @param commentID the id of this comment.
     */
    public static void removeComment(UUID commentID){
        appClient.removeComment(commentID);
    }

    /**
     * Remove reply.
     *
     * @param replyID the id of this reply.
     */
    public static void removeReply(UUID replyID){
        appClient.removeReply(replyID);
    }

    /**
     * Upload portrait of current user.
     *
     * @param imagePath the image path.
     * @throws FileNotFoundException the file not found exception
     */
    public static void uploadProtrait(Path imagePath) throws FileNotFoundException {
        appClient.uploadPortrait(imagePath);
    }

    /**
     * Fetch profile.
     *
     * @param user the username.
     */
    public static void fetchProfile(String user){
        appClient.fetchProfile(user);
    }

    /**
     * Gets image url.
     *
     * @param imageId the id of this image
     * @return the image url.
     * @throws MalformedURLException the malformed url exception.
     */
    public static URL getImageUrl(UUID imageId) throws MalformedURLException {
        return appClient.getPostImageURL(imageId);
    }


    public static URL getPortraitUrl(String username) throws MalformedURLException {
        return appClient.getPortraitURL(username + ".png");
    }

    /**
     * upload puzzle record.
     *
     * @param puzzleTime the time of this game.
     * @throws FileNotFoundException the file not found exception.
     */
    public static void setPuzzleWin(Long puzzleTime) throws FileNotFoundException {
        appClient.updateJigsaw(puzzleTime);
    }

    /**
     * upload question record.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public static void setQuestionWin() throws FileNotFoundException {
        appClient.updateQuiz();
    }

    /**
     * like a post.
     *
     * @param postID the id of the post.
     */
    public static void setPostLikes(UUID postID){
        appClient.likePost(postID);
    }

    /**
     * remove likes.
     *
     * @param postID the id of the post.
     */
    public static void setPostDislikes(UUID postID){
        appClient.dislikePost(postID);
    }

    /**
     * Check likes.
     *
     * @param postID the id of the post.
     */
    public static void checkLikes(UUID postID){
        appClient.checkLikedPost(postID);
    }

}