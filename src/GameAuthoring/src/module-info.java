module GameAuthoring {
    // Add all classes/resources it imports i.e. "requires javafx.base;"
    // requires ...

    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires javafx.web;
    requires GameEngine;
    requires org.junit.jupiter.api;

    //exports Screens;
    // Add all classes/packages to export
    // exports ...
    exports Utilities;
    exports Manager;
}