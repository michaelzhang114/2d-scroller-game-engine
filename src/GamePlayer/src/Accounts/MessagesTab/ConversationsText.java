/**
 * This class retrieves all messages sent back and forth between a primary user and all their friends. This class sets up specifically the message bodies, so the actual text of what is sent. An instance of this class should be declared wherever messages are to be displayed and the method getMessages() should be called through this object to get all the messages between the active user and another user.
 * @author Connor Ghazaleh
 * @author Eric Werbel
 */
package Accounts.MessagesTab;

import Database.DBMessages;
import Database.Message;
import Screens.ScreenFunction;
import java.util.ArrayList;
import java.util.List;

public class ConversationsText extends ScreenFunction {

    DBMessages msgsDB = new DBMessages();


    public ConversationsText(){

    }

    /**
     * Retrieves all messages associated with a particular user in chronological order
     * @param otherUser
     * @return
     */
    public List<Message> getMessages(String otherUser){
        String user = getActiveUser().getUsername();
        List<Message> received = filterReceived(user, otherUser);
        List<Message> sent = filterSent(user, otherUser);
        List<Message> allMessages = new ArrayList<>();
        allMessages.addAll(sent);
        allMessages.addAll(received);
        List<Message> sortedMessages = new ArrayList<>();
        while (!allMessages.isEmpty()){
            Message message = findEarliest(allMessages);
            sortedMessages.add(message);
            allMessages.remove(message);
        }
        return sortedMessages;
    }

    private List<Message> filterReceived(String user, String otherUser) {
        List<Message> receivedMessages = msgsDB.getReceivedMessages(user);
        List<Message> filteredMessages = new ArrayList<>();
        for (Message m : receivedMessages) {
            if (m.getSender().equals(otherUser)) {
                filteredMessages.add(m);
            }
        }
        return filteredMessages;
    }

    private List<Message> filterSent(String user, String otherUser) {
        List<Message> sentMessages = msgsDB.getSentMessages(user);
        List<Message> filteredMessages = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.getRecipient().equals(otherUser)) {
                filteredMessages.add(m);
            }
        }
        return filteredMessages;
    }

    private Message findEarliest(List<Message> messages){
        Message earliest = messages.get(0);
        for (Message m : messages){
            if (m.getMessageID() < earliest.getMessageID()){
                earliest = m;
            }
        }
        return earliest;
    }

}
