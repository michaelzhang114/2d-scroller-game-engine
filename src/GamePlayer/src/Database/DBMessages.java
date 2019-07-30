/**
 * This class is responsible for retrieving or storing any information pertinent to messages in a mySQL database. This class extends DBManager so it can use 2 general query methods that either retrieve data or modify data in the database. This class depends on having an active database set up that it can query and depends on the methods in the superclass DBManager. This class should be used for any manipulation or retrieval of the information storing messages between users. To do so, declare an instance of it in code and call the public methods listed below with appropriate input parameters.
 * @author Connor Ghazaleh
 */

package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DBMessages extends DBManager{

    public DBMessages(){
        super();
    }

    /**
     * Sends message from a sender to a recipient
     * @param message message text
     * @param recipient username of recipient
     * @param sender username of sender
     */
    public void sendMessage(String message, String recipient, String sender){
        String query = String.format("INSERT INTO user_info.messages VALUE ('%s','%s','%s',SYSDATE(),NULL);",message,recipient,sender);
        super.insertQuery(query);
    }

    /**
     * Gets all messages received by a user
     * @param user user retrieving messages for
     * @return
     */
    public List<Message> getReceivedMessages(String user){
        String query = String.format("SELECT * FROM user_info.messages WHERE recipient = '%s';",user);
        return listMessages(sendSelectQuery(query));
    }

    /**
     * Gets all messages sent by a user
     * @param user user retrieving messages for
     * @return
     */
    public List<Message> getSentMessages(String user){
        String query = String.format("SELECT * FROM user_info.messages WHERE sender = '%s';",user);
        return listMessages(sendSelectQuery(query));
    }

    private List<Object> sendSelectQuery(String query){
        ArrayList<Object> messages = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,messages);
        super.selectQuery(query,consumer);
        return messages;
    }

    /**
     * Deletes a message identified by a particular message ID
     * @param messageID ID of message to be deleted
     */
    public void deleteMessage(int messageID){
        String query = String.format("DELETE FROM user_info.messages WHERE message_id = %d",messageID);
        super.insertQuery(query);
    }

    private List<Message> listMessages(List<Object> m){
        ArrayList<Message> messages = new ArrayList<>();
        for (Object message : m){
            messages.add((Message) message);
        }
        return messages;
    }

    private void populateArray(ResultSet rs, List<Object> messages){
        try{
            while(rs.next()){
                Message message = new Message();
                message.setMessage(rs.getString(1));
                message.setRecipient(rs.getString(2));
                message.setSender(rs.getString(3));
                message.setTimeStamp(rs.getDate(4).toString());
                message.setMessageID(rs.getInt(5));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println("Could not extract results from result set");
        }
    }


}
