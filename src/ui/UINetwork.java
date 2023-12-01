package ui;

import oop.zsz.client.AppClient;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.UUID;

public class UINetwork {

    private static AppClient appClient = new AppClient("http", "116.204.117.136", 8080, new TestEventHandler());

    //重置manager
    private static void refreshUIManager(UIManager manager){
        TestEventHandler.manager = manager;
    }

    //尝试登录
    public static void trylogin(String username, String password){
        appClient.login(username, password);
    }

    //尝试注册
    public static void tryRegister(String username, String password){
        appClient.register(username, password);
    }

    //发帖子
    public static void publishPost(String title, String province, String content) {
        appClient.publishPost(title, province, content, new ArrayList<>());
    }

    //加评论
    public static void addComment(UUID postID, String content){
        appClient.publishComment(postID, content);
    }


    //评论回复：repliedName为null时，表示不回复任何人
    public static void addCommentReply(UUID commentID, String content, String repliedName){
        appClient.publishReply(commentID, content, repliedName);
    }

    //根据ID获取帖子
    public static void fetchPost(UUID postID){
        appClient.fetchPost(postID);
    }

    //获取所有帖子
    public static void fetchAllPost(){
        appClient.fetchAllPost(0, 100);
    }

    //删除指定帖子
    public static void removePost(UUID postID){
        appClient.removePost(postID);
    }

    public static void uploadProtrait(Path imagePath) throws FileNotFoundException {
        appClient.uploadPortrait(imagePath);
    }

    public static void fetchProfile(String user){
        appClient.fetchProfile(user);
    }

}