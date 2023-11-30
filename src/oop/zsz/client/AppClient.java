package oop.zsz.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import oop.zsz.post.Comment;
import oop.zsz.post.Post;
import oop.zsz.post.Reply;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public class AppClient {
    private HttpClient httpClient;
    private String uri;
    private Gson gson;
    private IClientEventHandler clientEventHandler;
    private String token;

    private static Type POSTS_LIST_TYPE = new TypeToken<List<Post>>(){}.getType();
    public AppClient(String uri, IClientEventHandler clientEventHandler) {
        this.httpClient = HttpClient.newBuilder().build();
        this.uri = uri;
        this.gson = new GsonBuilder().create();
        this.clientEventHandler = clientEventHandler;
    }

    public void register(String username, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username", username);
        jsonObject.addProperty("password", password);
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

    public void publishPost(String title, String province, String text) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("province", province);
        jsonObject.addProperty("title", title);
        jsonObject.addProperty("text", text);
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
                        clientEventHandler.onFetchPostSuccess(gson.fromJson(response.get("data"), Post.class));
                        return;
                    }
                    clientEventHandler.onFetchPostFailed(response.get("data").getAsString());
                }
        );
    }

    public void fetchAllPost() {
        System.out.println(1);
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/all"))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onFetchAllPostsSuccess(gson.fromJson(response.get("data"), POSTS_LIST_TYPE));
                        return;
                    }
                    clientEventHandler.onFetchAllPostsFailed(response.get("data").getAsString());
                }
        );
    }

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

    public void fetchPostInProvince(String province) {
        HttpRequest request = authenticatedBuilder()
                .uri(URI.create(uri + "/post/province" + "?province=" + province))
                .GET().build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(
                body -> {
                    JsonObject response = gson.fromJson(body.body(), JsonObject.class);
                    int code = -1;
                    if (response.has("code")) {
                        code = response.get("code").getAsInt();
                    }
                    if (code == 1) {
                        clientEventHandler.onFetchPostInProvinceSuccess(gson.fromJson(response.get("data"), POSTS_LIST_TYPE));
                        return;
                    }
                    clientEventHandler.onFetchPostInProvinceFailed(response.get("data").getAsString());
                }
        );
    }
}
