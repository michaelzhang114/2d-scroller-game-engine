/**
 * This class was set up as a main for the purpose of testing the database classes.
 * @author Connor Ghazaleh
 */

package Database;



import java.io.IOException;


public class DBMain {

    public static void main (String args[]) throws IOException {

        DBMessages db = new DBMessages();
        db.sendMessage("7","rando","apple");
        db.sendMessage("Hey","mark","rando");
        db.sendMessage("Hey","rando","Jimmy");

    }

}
