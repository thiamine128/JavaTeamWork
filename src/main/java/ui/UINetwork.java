package ui;

import network.client.AppClient;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

/**
 * The type Ui network.
 */
public class UINetwork {
    //将host参数改为后端的部署ip
    private static AppClient appClient = new AppClient("http", "116.204.117.136", 8080, new AppClientEventHandler());
    //private static AppClient appClient = new AppClient("http", "10.193.220.242", 8080, new AppClientEventHandler());

    //重置manager
    private static void refreshUIManager(UIManager manager){
        AppClientEventHandler.manager = manager;
    }

    /**
     * Reset password.
     *
     * @param email    the email
     * @param code     the code
     * @param password the password
     */
    public static void resetPassword(String email, String code, String password){
        appClient.resetPassword(email, code, password);
    }

    /**
     * Try login.
     *
     * @param username the username
     * @param password the password
     */
//尝试登录
    public static void trylogin(String username, String password){
        if (username.contains("@")) appClient.loginEmail(username, password);
        else appClient.login(username, password);
    }

    /**
     * Try register.
     *
     * @param username the username
     * @param password the password
     * @param email    the email
     * @param code     the code
     */
//尝试注册
    public static void tryRegister(String username, String password, String email, String code){
        appClient.register(username, password, email, code);
    }

    /**
     * Send email.
     *
     * @param email the email
     */
    public static void sendEmail(String email){
        System.out.println("try to send email to "+email);
        appClient.requestVerificationCode(email);
    }

    /**
     * Publish post.
     *
     * @param title    the title
     * @param province the province
     * @param content  the content
     * @param pathList the path list
     */
//发帖子
    public static void publishPost(String title, String province, String content, List<Path> pathList) {
        appClient.publishPost(title, province, content, pathList);
    }

    /**
     * Add comment.
     *
     * @param postID  the post id
     * @param content the content
     */
//加评论
    public static void addComment(UUID postID, String content){
        appClient.publishComment(postID, content);
    }


    /**
     * Add comment reply.
     *
     * @param commentID   the comment id
     * @param content     the content
     * @param repliedName the replied name
     */
//评论回复：repliedName为null时，表示不回复任何人
    public static void addCommentReply(UUID commentID, String content, String repliedName){
        appClient.publishReply(commentID, content, repliedName);
    }

    /**
     * Fetch post.
     *
     * @param postID the post id
     */
//根据ID获取帖子
    public static void fetchPost(UUID postID){
        appClient.fetchPost(postID);
    }

    /**
     * Fetch all post.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     */
//获取所有帖子
    public static void fetchAllPost(int pageNum, int pageSize){
        appClient.fetchAllPost(pageNum, pageSize);
    }

    /**
     * Fetch post in province.
     *
     * @param pageNum      the page num
     * @param pageSize     the page size
     * @param provinceName the province name
     * @param keyword      the keyword
     */
    public static void fetchPostInProvince(int pageNum, int pageSize, String provinceName, String keyword){
        appClient.searchPost(provinceName, keyword, pageNum, pageSize);
    }

    /**
     * Remove post.
     *
     * @param postID the post id
     */
//删除指定帖子
    public static void removePost(UUID postID){
        appClient.removePost(postID);
    }

    /**
     * Remove comment.
     *
     * @param commentID the comment id
     */
    public static void removeComment(UUID commentID){
        appClient.removeComment(commentID);
    }

    /**
     * Remove reply.
     *
     * @param replyID the reply id
     */
    public static void removeReply(UUID replyID){
        appClient.removeReply(replyID);
    }

    /**
     * Upload protrait.
     *
     * @param imagePath the image path
     * @throws FileNotFoundException the file not found exception
     */
    public static void uploadProtrait(Path imagePath) throws FileNotFoundException {
        appClient.uploadPortrait(imagePath);
    }

    /**
     * Fetch profile.
     *
     * @param user the user
     */
    public static void fetchProfile(String user){
        appClient.fetchProfile(user);
    }

    /**
     * Gets image url.
     *
     * @param imageId the image id
     * @return the image url
     * @throws MalformedURLException the malformed url exception
     */
    public static URL getImageUrl(UUID imageId) throws MalformedURLException {
        return appClient.getPostImageURL(imageId);
    }


    public static URL getPortraitUrl(String username) throws MalformedURLException {
        return appClient.getPortraitURL(username + ".png");
    }

    /**
     * Sets puzzle win.
     *
     * @param puzzleTime the puzzle time
     * @throws FileNotFoundException the file not found exception
     */
    public static void setPuzzleWin(Long puzzleTime) throws FileNotFoundException {
        appClient.updateJigsaw(puzzleTime);
    }

    /**
     * Sets question win.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public static void setQuestionWin() throws FileNotFoundException {
        appClient.updateQuiz();
    }

    /**
     * Set post likes.
     *
     * @param postID the post id
     */
    public static void setPostLikes(UUID postID){
        appClient.likePost(postID);
    }

    /**
     * Set post dislikes.
     *
     * @param postID the post id
     */
    public static void setPostDislikes(UUID postID){
        appClient.dislikePost(postID);
    }

    /**
     * Check likes.
     *
     * @param postID the post id
     */
    public static void checkLikes(UUID postID){
        appClient.checkLikedPost(postID);
    }

}