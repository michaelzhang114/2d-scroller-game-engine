/**
 * This class was used as a data structure to store message data as individual objects. Message objects store the message body, sender, recipient, and timestamp for a message. To use this class, declare a Message object and fill each of the fields with the setter methods
 * @author Connor Ghazaleh
 */

package Database;

public class Message {

    private String message = "";
    private String recipient = "";
    private String sender = "";
    private String timeStamp = "";
    private int messageID;

    public Message(){

    }

    /**
     * Set message field
     * @param message message body
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets recipient field
     * @param recipient username of recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Sets sender field
     * @param sender username of sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Sets timestamp field
     * @param timeStamp time message was sent/received
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Sets messageID field
     * @param messageID ID of message
     */
    public void setMessageID(int messageID) {
        this.messageID = messageID;
    }

    /**
     * Gets message field
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get recipient field
     * @return
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Get sender field
     * @return
     */
    public String getSender() {
        return sender;
    }

    /**
     * Get timeStamp field
     * @return
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Get messageID field
     * @return
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * Set string representation of a Message object
     * @return
     */
    @Override
    public String toString(){
        return String.format("{%s} from: %s, to: %s, ID: %d\n",message,sender,recipient,messageID);
    }
}
