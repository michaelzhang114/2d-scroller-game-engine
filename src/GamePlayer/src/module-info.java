module GamePlayer {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;

    requires java.mail;
    requires GameEngine;

    exports Running;
}