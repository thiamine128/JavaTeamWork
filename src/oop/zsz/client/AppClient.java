package oop.zsz.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import oop.zsz.page.Page;
import oop.zsz.post.Comment;
import oop.zsz.post.DetailedPost;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;
import oop.zsz.user.UserProfile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class AppClient {
    private HttpClient httpClient;
    private String uri;
    private String protocol;
    private String host;
    private int port;
    private Gson gson;
    private IClientEventHandler clientEventHandler;
    private String token;

    private static Type POSTS_PAGE_TYPE = new TypeToken<Page<Post>>(){}.getType();
    public AppClient(String protocol, String host, int port, IClientEventHandler clientEventHandler) {
        this.httpClient = HttpClient.newBuilder().build();
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.uri = protocol + "://" + host + ":" + port;
        this.gson = new GsonBuilder().create();
        this.clientEventHandler = clientEventHandler;
    }

    /*
    用户注册
    用户名：数字，大小写字母构成的6-13长度的字符串
    密码：数字，大小写字母构成的6-13长度的字符串
     */
    public void register(String username, String password, String email, String verificationCode) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("verificationCode", verificationCode);
        HttpRequest request = defaultBuilder()
                .uri(URI.create(uri + "/user/register"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onRegisterSuccess(response.get("data").getAsString());
                        return;
                    }
                    clientEventHandler.onRegisterFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    用户登录
    用户名，密码
     */
    public void login(String username, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
        HttpRequest request = defaultBuilder()
                .uri(URI.create(uri + "/user/login"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onLoginSuccess(response.get("data").getAsJsonObject().get("username").getAsString(),
                                response.get("data").getAsJsonObject().get("token").getAsString(),
                                this);
                        return;
                    }
                    clientEventHandler.onLoginFailed(response.get("data").getAsString());
                }
        );
    }

    public void loginEmail(String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", password);
        HttpRequest request = defaultBuilder()
                .uri(URI.create(uri + "/user/login-email"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onLoginSuccess(response.get("data").getAsJsonObject().get("username").getAsString(),
                                response.get("data").getAsJsonObject().get("token").getAsString(),
                                this);
                        return;
                    }
                    clientEventHandler.onLoginFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    发布帖子
    标题，省份，内容，图片列表
    图片列表为本地文件路径列表
     */
    public void publishPost(String title, String province, String text, List<Path> images) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("province", province);
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("text", text);
        List<String> encodedImages = new ArrayList<>();
        try {
            for (var path : images) {
                encodedImages.add(Base64.getEncoder().encodeToString(Files.readAllBytes(path)));
            }
        } catch (IOException ioException) {
            clientEventHandler.onPublishPostFailed("Failed to read files");
        }
        jsonObject.add("images", gson.toJsonTree(encodedImages));

        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/publish"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onPublishPostSuccess(gson.fromJson(response.get("data"), Post.class));
                        return;
                    }
                    clientEventHandler.onPublishPostFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    获取单篇帖子
     */
    public void fetchPost(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/get" + "?id=" + uuid.toString()))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onFetchPostSuccess(gson.fromJson(response.get("data"), DetailedPost.class));
                        return;
                    }
                    clientEventHandler.onFetchPostFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    获取所有帖子
     */
    public void fetchAllPost(int page, int size) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/all" + "?page=" + page + "&size=" + size))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onFetchAllPostsSuccess(gson.fromJson(response.get("data"), POSTS_PAGE_TYPE));
                        return;
                    }
                    clientEventHandler.onFetchAllPostsFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    发布回复
     */
    public void publishComment(UUID postId, String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("post", postId.toString());
        jsonObject.addProperty("text", text);
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/comment/publish"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onCommentPublishSuccess(gson.fromJson(response.get("data"), Comment.class));
                        return;
                    }
                    clientEventHandler.onCommentPublishFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    发布回复
     */
    public void publishReply(UUID commentId, String text, String replyTo) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("comment", commentId.toString());
        jsonObject.addProperty("text", text);
        jsonObject.addProperty("replyTo", replyTo);
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/reply/publish"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onReplyPublishSuccess(gson.fromJson(response.get("data"), Reply.class));
                        return;
                    }
                    clientEventHandler.onReplyPublishFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    设置登录信息
     */
    public void setUserToken(String token) {
        this.token = token;
    }

    private HttpRequest.Builder defaultBuilder() {
        return HttpRequest.newBuilder()
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json");
    }

    private HttpRequest.Builder authenticatedBuilder() {
        return HttpRequest.newBuilder()
                .timeout(Duration.of(10, ChronoUnit.SECONDS))
                .header("Content-Type", "application/json")
                .headers("Authorization", "Bearer " + this.token);
    }

    /*
    获取省份内帖子
     */
    public void searchPost(String province, String key, int page, int size) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/search" + "?province=" + province + "&key=" + key + "&page=" + page + "&size=" + size))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onSearchPostSuccessSuccess(gson.fromJson(response.get("data"), POSTS_PAGE_TYPE));
                        return;
                    }
                    clientEventHandler.onSearchPostFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    删除帖子
     */
    public void removePost(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/remove" + "?id=" + uuid.toString()))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onRemovePostSuccess();
                        return;
                    }
                    clientEventHandler.onRemovePostFailed(response.get("data").getAsString());
                }
        );
    }

    public void likePost(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/like" + "?id=" + uuid.toString()))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onLikePostSuccess();
                        return;
                    }
                    clientEventHandler.onLikePostFailed(response.get("data").getAsString());
                }
        );
    }

    public void dislikePost(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/dislike" + "?id=" + uuid.toString()))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onDislikePostSuccess();
                        return;
                    }
                    clientEventHandler.onDislikePostFailed(response.get("data").getAsString());
                }
        );
    }

    public void resetPassword(String email, String verificationCode, String newPassword) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", newPassword);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("verificationCode", verificationCode);
        HttpRequest request = defaultBuilder()
                .uri(URI.create(uri + "/user/reset-password"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonObject.toString())).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onResetPasswordSuccess();
                        return;
                    }
                    clientEventHandler.onResetPasswordFailed(response.get("data").getAsString());
                }
        );
    }

    public void checkLikedPost(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/user/liked-post" + "?id=" + uuid))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    System.out.println(body.body());
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onCheckLikedPostSuccess(uuid, response.get("data").getAsBoolean());
                        return;
                    }
                    clientEventHandler.onCheckLikedPostFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    上传头像
    本地文件路径
     */
    public void uploadPortrait(Path file) throws FileNotFoundException {

        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/user/portrait/upload"))
                .POST(HttpRequest.BodyPublishers.ofFile(file)).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onUploadPortraitSuccess();
                        return;
                    }
                    clientEventHandler.onUploadPortraitFailed(response.get("data").getAsString());
                }
        );
    }

    public void updateJigsaw(Long time) throws FileNotFoundException {

        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/user/mark-jigsaw-flag" + "?time=" + time))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onUpdateJigsawSuccess();
                        return;
                    }
                    clientEventHandler.onUpdateJigsawFailed(response.get("data").getAsString());
                }
        );
    }

    public void updateQuiz() throws FileNotFoundException {

        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/user/mark-quiz"))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onUpdateQuizSuccess();
                        return;
                    }
                    clientEventHandler.onUpdateQuizFailed(response.get("data").getAsString());
                }
        );
    }



    /*
    获取用户资料
     */
    public void fetchProfile(String username) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/user/profile/get" + "?username=" + username))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    System.out.println(body.body());
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onFetchProfileSuccess(gson.fromJson(response.get("data"), UserProfile.class));
                        return;
                    }
                    clientEventHandler.onFetchProfileFailed(response.get("data").getAsString());
                }
        );
    }

    public void requestVerificationCode(String email) {
        HttpRequest request = defaultBuilder()
                .uri(URI.create(uri + "/verify/send-code" + "?email=" + email))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onSendVerificationCodeSuccess();
                        return;
                    }
                    clientEventHandler.onSendVerificationCodeFailed(response.get("data").getAsString());
                }
        );
    }

    /*
    获取用户头像URL
     */
    public URL getPortraitURL(String portrait) throws MalformedURLException {
        return new URL(protocol, host, 80, "/portrait/" + portrait);
    }

    /*
    获取帖子图片URL
     */
    public URL getPostImageURL(UUID image) throws MalformedURLException {
        return new URL(protocol, host, 80, "/post/" + image.toString() + ".png");
    }
}
