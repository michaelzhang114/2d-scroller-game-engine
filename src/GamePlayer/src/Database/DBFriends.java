/**
 * This class is responsible for retrieving or storing any information pertinent to friends in a mySQL database. This class extends DBManager so it can use 2 general query methods that either retrieve data or modify data in the database. This class depends on having an active database set up that it can query and depends on the methods in the superclass DBManager. This class should be used for any manipulation or retrieval of the information storing friendships. To do so, declare an instance of it in code and call the public methods listed below with appropriate input parameters.
 * @author Connor Ghazaleh
 */

package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DBFriends extends DBManager{

    public DBFriends(){
        super();
    }

    /**
     * Adds friendship between 2 users
     * @param user1
     * @param user2
     * @return true if friendship already exists, else false
     */
    public boolean addFriendship(String user1, String user2){
        boolean doesExist = checkIfFriendshipExists(user1,user2);
        String query = "";
        if (!doesExist){
            if (user1.compareTo(user2) < 0){
                query = "INSERT INTO user_info.friends_list VALUE ('"+user1+"','"+user2+"',NOW());";
            }else{
                query = "INSERT INTO user_info.friends_list VALUE ('"+user2+"','"+user1+"',NOW());";
            }
            super.insertQuery(query);
        }
        return doesExist;
    }

    /**
     * Checks if a friendship exists between 2 users
     * @param user1
     * @param user2
     * @return true if friendship exists, else false
     */
    public boolean checkIfFriendshipExists(String user1, String user2){
        String query = "";
        if (user1.compareTo(user2) < 0){
            query = "SELECT * FROM user_info.friends_list WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
        }else{
            query = "SELECT * FROM user_info.friends_list WHERE user1 = '"+user2+"' AND user2 = '"+user1+"';";
        }
        ArrayList<Object> friendships = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,friendships);
        super.selectQuery(query,consumer);
        return ! friendships.isEmpty();
    }

    /**
     * Removes a friendship between two users
     * @param user1
     * @param user2
     * @return true if friendship exists, else false
     */
    public boolean removeFriendship(String user1, String user2){
        boolean doesExist = checkIfFriendshipExists(user1,user2);
        String query = "";
        if (doesExist){
            if (user1.compareTo(user2) < 0){
                query = "DELETE FROM user_info.friends_list WHERE user1 = '"+user1+"' AND user2 = '"+user2+"';";
            }else{
                query = "DELETE FROM user_info.friends_list WHERE user1 = '"+user2+"' AND user2 = '"+user1+"';";
            }
            super.insertQuery(query);
        }
        return doesExist;

    }

    /**
     * Gets list of all friends of a particular user
     * @param user friends of this user are retrieved
     * @return List of all friends of user
     */
    public List<Friend> getFriends(String user){
        String query = "SELECT * FROM user_info.friends_list WHERE user1 = '"+user+"' OR user2 = '"+user+"';";
        ArrayList<Object> friendships = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,friendships);
        super.selectQuery(query,consumer);
        return createFriendsList(friendships,user);
    }

    /**
     * Creates list of Friends from list of general Objects
     * @param friendships list of friends as general objects
     * @param user user whose friends are being listed
     * @return List of Friend objects
     */
    private List<Friend> createFriendsList(List<Object> friendships, String user){
        ArrayList<Friend> friends = new ArrayList<>();
        for (Object f : friendships){
            Friend friend = new Friend();
            if (((Friendship) f).getFriend1().equals(user)){
                friend.setName(((Friendship) f).getFriend2());
                friend.setSince(((Friendship) f).getDateCreated());
            }else{
                friend.setName(((Friendship) f).getFriend1());
                friend.setSince(((Friendship) f).getDateCreated());
            }
            friends.add(friend);
        }
        return friends;
    }

    private void populateArray(ResultSet rs, List<Object> friendships){
        try{
            while(rs.next()){
                Friendship friendship = new Friendship();
                friendship.setFriend1(rs.getString(1));
                friendship.setFriend2(rs.getString(2));
                friendship.setDateCreated(rs.getDate(3).toString());
                friendships.add(friendship);
            }
        }catch(SQLException e){
            System.out.println("Could not extract results from result set");
        }
    }


}
