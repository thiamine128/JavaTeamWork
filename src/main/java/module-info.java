module oop.javateamworkmaven {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires com.google.gson;
    requires java.net.http;
    requires javafx.media;
    requires log4j;

    opens ui to javafx.fxml;
    opens controller to javafx.fxml;
    opens network.page to com.google.gson;
    opens network.user to com.google.gson;
    opens network.post to com.google.gson;
    exports ui;
    exports controller;
    exports network.page;
    exports network.user;
    exports network.post;
    exports app;
    exports game;
}