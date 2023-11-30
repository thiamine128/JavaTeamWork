package ui;

import oop.zsz.client.AppClient;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        AppClient appClient = new AppClient("http://116.204.117.136:8080", new TestEventHandler());

        appClient.register("user123", "abcdefg");
        appClient.register("user1", "abcg");
        appClient.register("user123", "abcdef123g");
        Thread.sleep(4000);
        appClient.login("user123", "abcdef123g");
        appClient.login("user123", "abcdefg");
        Thread.sleep(4000);

        appClient.fetchAllPost();
        Thread.sleep(1000);
        /*appClient.publishComment(UUID.fromString("3aafa4d7-ec6b-4776-b984-e2870716fe96"), "Comment1");
        appClient.publishReply(UUID.fromString("71959938-3bc9-4181-ba58-e681f0f0dc7c"), "Reply 1", null);
        appClient.publishReply(UUID.fromString("71959938-3bc9-4181-ba58-e681f0f0dc7c"), "Reply 1", "user123");
        */
        while (true) {

        }
    }
}