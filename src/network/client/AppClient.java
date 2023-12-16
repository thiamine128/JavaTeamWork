package network.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import network.page.Page;
import network.post.Comment;
import network.post.DetailedPost;
import network.post.Post;
import network.post.Reply;
import network.user.UserProfile;

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

/**
 * The type App client. Provides methods to interact with server as a client.
 *
 * @author thiamine128
 * @Description:
 * @date 2023 /12/16 19:56
 */
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

    /**
     * Instantiates a new App client.
     *
     * @param protocol           the protocol
     * @param host               the host
     * @param port               the port
     * @param clientEventHandler the client event handler
     */
    public AppClient(String protocol, String host, int port, IClientEventHandler clientEventHandler) {
        this.httpClient = HttpClient.newBuilder().build();
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.uri = protocol + "://" + host + ":" + port;
        this.gson = new GsonBuilder().create();
        this.clientEventHandler = clientEventHandler;
    }

    /**
     * Send register request and response asynchronously.
     *
     * @param username         the username
     * @param password         the password
     * @param email            the email
     * @param verificationCode the verification code
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

    /**
     * Send login request and response asynchronously.
     *
     * @param username the username
     * @param password the password
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


    /**
     * Send login request via email and response asynchronously.
     *
     * @param email    the email
     * @param password the password
     */
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


    /**
     * Send publish post request and response asynchronously.
     *
     * @param title    the title
     * @param province the province
     * @param text     the text
     * @param images   the images
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


    /**
     * Send fetch post request and response asynchronously.
     *
     * @param uuid the uuid
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


    /**
     * Send fetch all post request and response asynchronously.
     *
     * @param page the page
     * @param size the size
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

    /**
     * Send publish comment request and response asynchronously.
     *
     * @param postId the post id
     * @param text   the text
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


    /**
     * Send publish reply request and response asynchronously.
     *
     * @param commentId the comment id
     * @param text      the text
     * @param replyTo   the reply to
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


    /**
     * Sets user token.
     *
     * @param token the token
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


    /**
     * Send search post and response asynchronously.
     *
     * @param province the province
     * @param key      the key
     * @param page     the page
     * @param size     the size
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

    /**
     * Send remove post request and response asynchronously.
     *
     * @param uuid the uuid
     */
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

    /**
     * Send remove comment and response asynchronously.
     *
     * @param uuid the uuid
     */
    public void removeComment(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/comment/remove" + "?id=" + uuid.toString()))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onRemoveCommentSuccess();
                        return;
                    }
                    clientEventHandler.onRemoveCommentFailed(response.get("data").getAsString());
                }
        );
    }

    /**
     * Send remove reply request and response asynchronously.
     *
     * @param uuid the uuid
     */
    public void removeReply(UUID uuid) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/reply/remove" + "?id=" + uuid.toString()))
                .POST(HttpRequest.BodyPublishers.noBody()).build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onRemoveReplySuccess();
                        return;
                    }
                    clientEventHandler.onRemoveReplyFailed(response.get("data").getAsString());
                }
        );
    }


    /**
     * Send like post and response asynchronously.
     *
     * @param uuid the uuid
     */
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

    /**
     * Send dislike post and response asynchronously.
     *
     * @param uuid the uuid
     */
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

    /**
     * Send reset password and response asynchronously.
     *
     * @param email            the email
     * @param verificationCode the verification code
     * @param newPassword      the new password
     */
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

    /**
     * Send check liked post request and response asynchronously.
     *
     * @param uuid the uuid
     */
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

    /**
     * Send upload portrait request and response asynchronously.
     *
     * @param file the file
     * @throws FileNotFoundException the file not found exception
     */
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

    /**
     * Send update jigsaw request and response asynchronously.
     *
     * @param time the time
     * @throws FileNotFoundException the file not found exception
     */
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

    /**
     * Send update quiz and response asynchronously.
     *
     * @throws FileNotFoundException the file not found exception
     */
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

    /**
     * Send fetch profile request and response asynchronously.
     *
     * @param username the username
     */
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

    /**
     * Send request verification code request and response asynchronously.
     *
     * @param email the email
     */
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

    /**
     * Gets portrait url.
     *
     * @param portrait the portrait
     * @return the portrait url
     * @throws MalformedURLException the malformed url exception
     */
/*
    获取用户头像URL
     */
    public URL getPortraitURL(String portrait) throws MalformedURLException {
        return new URL(protocol, host, 80, "/portrait/" + portrait);
    }

    /**
     * Gets post image url.
     *
     * @param image the image
     * @return the post image url
     * @throws MalformedURLException the malformed url exception
     */
    public URL getPostImageURL(UUID image) throws MalformedURLException {
        return new URL(protocol, host, 80, "/post/" + image.toString() + ".png");
    }
}
