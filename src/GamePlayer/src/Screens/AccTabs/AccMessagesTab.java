package Screens.AccTabs;

import Accounts.MessagesTab.ConversationsList;
import Accounts.MessagesTab.ConversationsText;
import Database.DBMessages;
import Database.Message;
import Screens.ScreenFunction;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Controls the information to be displayed by the messages tab within the accounts section. This tab is broken into two
 * parts, a list of active conversation and the current conversation. It allows users to view their recent messages and
 * conversations with other users. This class depends on the connection to the database and methods from the database API
 * to return correct and correct information.
 *
 * @author Connor Ghazaleh and Eric Werbel
 */
public class AccMessagesTab extends ScreenFunction {

    public static final int LIST_WIDTH = 150;
    public static final int MESSAGE_WIDTH = 300;
    public static final int REGION_MIN_WIDTH = 100;
    public static final int SPACE_WIDTH = 15;

    public static final String SENT_STYLE = "message-sent";
    public static final String RECEIVED_STYLE = "message-received";
    public static final String SEND = "Send";

    private ScrollPane myRoot = new ScrollPane();
    private VBox myContainer = new VBox();
    private ConversationsList convoList = new ConversationsList();
    private ConversationsText convoText = new ConversationsText();
    private DBMessages msgDB = new DBMessages();
    private ListView conversations = new ListView();
    private VBox currentConvo = new VBox();
    private static final String REFRESH = "Refresh";

    /**
     * Constructor initializes the content to be displayed on the ScrollPane and creates the general containers for how
     * conversations will be displayed.
     */
    public AccMessagesTab() {
        conversations.setPrefWidth(LIST_WIDTH);
        updateScrollPane();
        HBox lowerContainer = new HBox();
        Region spacer = new Region();
        spacer.setPrefWidth(SPACE_WIDTH);
        lowerContainer.getChildren().addAll(conversations, spacer, currentConvo);
        myContainer.getChildren().add(lowerContainer);
        myContainer.getChildren().add(makeRefreshButton());
        myRoot.setContent(myContainer);
    }

    /**
     * Updates the content to be displayed on the ScrollPane.
     */
    public void updateScrollPane(){
        List<String> contents = conversations.getItems();
        conversations.getItems().removeAll(contents);
        conversations.getItems().addAll(convoList.getConversations());
        addConvoListener();
    }

    private Button makeRefreshButton(){
        Button refresh = makeButton(REFRESH,e -> updateScrollPane());
        return refresh;
    }

    /**
     * Calls a method to update the ScrollPane and returns the updated version
     *
     * @return an updated version of the ScrollPane associated with this tab
     */
    public ScrollPane getScrollPane() {
        updateScrollPane();
        return myRoot;
    }

    private void addConvoListener() {
        conversations.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String username = conversations.getSelectionModel().getSelectedItem().toString();
                updateCurrentConvo(username);
            }
        });
    }

    private void updateCurrentConvo(String otherUser) {
        currentConvo.getChildren().clear();
        List<Message> myMessages = convoText.getMessages(otherUser);
        for (Message m : myMessages) {
            System.out.println(m.getMessage());
            HBox messageBox = new HBox();
            messageBox.setPrefWidth(MESSAGE_WIDTH);
            Region r = new Region();
            r.setMinWidth(REGION_MIN_WIDTH);
            HBox.setHgrow(r, Priority.ALWAYS);
            Label msgText = new Label(m.getMessage());
            msgText.setWrapText(true);
            if (m.getSender().equals(otherUser)) {
                messageBox.getChildren().addAll(msgText, r);
                msgText.getStyleClass().add(RECEIVED_STYLE);
            } else {
                messageBox.getChildren().addAll(r, msgText);
                msgText.getStyleClass().add(SENT_STYLE);
            }
            currentConvo.getChildren().add(messageBox);
        }
        TextField newMessage = new TextField();
        Button sendMessage = makeButton(SEND, e->send(otherUser, newMessage));
        HBox sendArea = new HBox();
        sendArea.getChildren().addAll(newMessage, sendMessage);
        currentConvo.getChildren().add(sendArea);
    }

    private void send(String otherUser, TextField msg) {
        msgDB.sendMessage(msg.getText(), otherUser, getActiveUser().getUsername());
        msg.clear();
        updateCurrentConvo(otherUser);
    }

}
