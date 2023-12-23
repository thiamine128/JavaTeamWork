package network.client;

import javafx.application.Platform;
import ui.ErrorManager;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Error message.
 */
public class ErrorMessage {
    private static Map<String, String> message;

    static {
        message = new HashMap<>();
        message.put("Failed to publish comment", "发送评论失败，请检查帖子ID.");
        message.put("Failed to remove comment", "删除评论失败，用户权限不足");
        message.put("Incorrect province format", "省份信息格式错误");
        message.put("Failed to upload images", "上传图片失败");
        message.put("Failed to publish post", "发送帖子失败");
        message.put("Failed to get post", "获取帖子失败");
        message.put("No such post", "帖子不存在");
        message.put("No posts in province", "没有帖子");
        message.put("No posts", "没有帖子");
        message.put("Failed to remove post", "删除帖子失败，请检查帖子ID以及用户权限");
        message.put("Like post failed", "帖子点赞失败");
        message.put("Dislike post failed", "取消点赞失败");
        message.put("Timeout", "请求超时");
        message.put("Failed to publish reply", "回复评论失败");
        message.put("Failed to remove reply", "删除评论失败");
        message.put("Username already exist", "用户名已注册");
        message.put("Email already exist", "邮箱已经注册");
        message.put("Failed to verify email", "邮箱验证失败");
        message.put("Failed to register", "注册失败");
        message.put("Wrong username or password", "用户名或密码错误");
        message.put("Illegal portrait", "头像格式错误");
        message.put("Failed to upload image", "上传头像失败");
        message.put("Failed to get profile", "获取资料失败");
        message.put("Failed to check like", "获取点赞信息失败");
        message.put("Failed to update", "游戏数据更新失败");
        message.put("No such user", "用户不存在");
        message.put("Failed to verify", "邮箱验证失败");
        message.put("Failed to reset password", "重置密码失败");
        message.put("Too many send request", "请求过于频繁");
        message.put("Failed to send email", "发送邮件失败");
        message.put("Invalid password", "密码格式错误（6-16个数字，大小写字母）");
        message.put("Invalid username", "用户名错误（6-13个数字，大小写字母）");
    }

    /**
     * Show the first error in a dialog.
     *
     * @param error the error to show
     */
    public static void show(String error) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    String [] errorArray = error.split("\n");
                    ErrorManager.sendError(message.get(errorArray[0].trim()));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}