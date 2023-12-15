package ui;

import Click.ProvinceDetail;
import Game.Guess;
import Game.GuessDetail;
import Game.Hamiltonian;
import oop.zsz.client.AppClient;

import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        AppClient appClient = new AppClient("http", "116.204.117.136", 8080, new TestEventHandler());

        appClient.login("123456", "123456");
        Thread.sleep(1000);
        //appClient.fetchAllPost(1, 10);
        appClient.likePost(UUID.fromString("853aca8e-0c4a-4005-b195-3738ff0094b1"));
        Thread.sleep(2000);
    }

}