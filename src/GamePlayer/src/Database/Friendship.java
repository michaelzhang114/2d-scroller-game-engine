/**
 * This class was used as a data structure to store friendship data as individual objects. Friendships store two friend fields and a date field representing that two particular users became friends on a specific date. To use this class, declare a Friendship object and fill each of the fields with the setter methods
 * @author Connor Ghazaleh
 */

package Database;

public class Friendship {
    String friend1 = "";
    String friend2 = "";
    String dateCreated = "";

    public Friendship(){
    }

    /**
     * Set friend1 field
     * @param friend1
     */
    public void setFriend1(String friend1) {
        this.friend1 = friend1;
    }

    /**
     * Set friend2 field
     * @param friend2
     */
    public void setFriend2(String friend2) {
        this.friend2 = friend2;
    }

    /**
     * Set dateCreated field
     * @param dateCreated
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Get dateCreated field
     * @return
     */
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * Get friend1 field
     * @return
     */
    public String getFriend1() {
        return friend1;
    }

    /**
     * Get friend2 field
     * @return
     */
    public String getFriend2() {
        return friend2;
    }
}
