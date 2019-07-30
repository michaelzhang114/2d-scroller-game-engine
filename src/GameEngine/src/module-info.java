module GameEngine {
    // Add all classes/resources it imports i.e. "requires javafx.base;"
    // requires ...
    // Add all classes/packages to export
    // exports ...
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires java.xml;
    //requires java.util;
    // Java scripting module to use groovy
    requires java.scripting;
    // imported JAR libraries as modules
    //requires xstream;
    // imported JAR libraries as modules
    requires xstream;

    //opens makingGame.external to xstream;
    // open this project's packages for reflection in xstreamspike and groovy
    // this project's packages to make available

    requires groovy.all;
    requires org.junit.jupiter.api;

    //Required for the Point object
    requires java.desktop;
    exports external to GameAuthoring, GamePlayer;
    exports exception to GameAuthoring;

    // open this project's packages for reflection in xstreamspike and groovy
    opens testing to xstream, groovy.all, javafx.graphics, javafx.base, javafx.controls, javafx.media;
//    opens external to xstream, groovy.all, javafx.graphics, javafx.base, javafx.controls, javafx.media;
    // this project's packages to make available
    exports testing;
}