/**
 * This class can be used to retrieve all the people that the active user has active conversations with and can list all the people who either sent messages to or received messages from the active user. This class was used to index all of the active user's conversations by who the conversations were with.
 * @author Connor Ghazaleh
 */

package Accounts.MessagesTab;

import Database.DBMessages;
import Database.Message;
import Screens.ScreenFunction;

import java.util.ArrayList;
import java.util.List;


public class ConversationsList extends ScreenFunction {

    private static final int CONVO_MENU_WIDTH = 200;
    private String user = getActiveUser().getUsername();

    DBMessages msgsDB = new DBMessages();


    public ConversationsList(){

    }

//    public ListView makeList(){
//        ListView conversations = new ListView();
//        conversations.getItems().addAll(getConversations());
//        conversations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        conversations.setPrefWidth(CONVO_MENU_WIDTH);
//        return conversations;
//    }

    /**
     * Retrieves conversations from database for the active user
     * @return List of all messages sent and received
     */
    public List<String> getConversations(){
        String user = getActiveUser().getUsername();
        List<Message> received = msgsDB.getReceivedMessages(user);
        List<Message> sent = msgsDB.getSentMessages(user);
        ArrayList<String> convos = new ArrayList<>();
        for (String name : messagedYou(received)){
            if (!convos.contains(name)){
                convos.add(name);
            }
        }
        for (String name : youMessaged(sent)){
            if (!convos.contains(name)){
                convos.add(name);
            }
        }
        return convos;
    }

    /**
     * Retrieves names of all people who sent you messages from database
     * @param messages List of Message objects
     * @return List of names of people
     */
    public List<String> messagedYou(List<Message> messages){
        ArrayList<String> people = new ArrayList<>();
        for (Message m : messages){
            people.add(m.getSender());
        }
        return people;
    }

    /**
     * Retrieves names of all people you messaged from database
     * @param messages List of Message objects
     * @return List of names of people
     */
    public List<String> youMessaged(List<Message> messages){
        ArrayList<String> people = new ArrayList<>();
        for (Message m : messages){
            people.add(m.getRecipient());
        }
        return people;
    }


}
