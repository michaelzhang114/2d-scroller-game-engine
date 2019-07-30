/**
 * This class was used as a data structure to store friend data as individual objects. The Friend object stores the name of a friend and time they became a friend. To use this class, declare a Friend object and fill each of the fields with the setter methods
 * @author Connor Ghazaleh
 */

package Database;

public class Friend {
    String name = "";
    String since = "";

    public Friend(){

    }

    /**
     * Set friend field
     * @param friend
     */
    public void setName(String friend) {
        this.name = friend;
    }

    /**
     * set since field (when this person became a friend)
     * @param since
     */
    public void setSince(String since) {
        this.since = since;
    }

    /**
     * Get value in name field
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Get value in since field
     * @return
     */
    public String getSince() {
        return since;
    }

    /**
     * Set string representation of a friend object
     * @return
     */
    @Override
    public String toString(){
        return "{"+name+","+since+"}";
    }
}
