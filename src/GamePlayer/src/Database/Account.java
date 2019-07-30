/**
 * This class was used as a data structure to store account data as individual objects. The Account class stores login information such as username and password as well as other relevant account information such as email, display name, and the date that the account was created. To use this class, declare an account object and fill each of the fields with the setter methods.
 * @author Connor Ghazaleh
 */

package Database;

public class Account extends Object{
    private String username = "";
    private String password = "";
    private String email = "";
    private String displayName = "";
    private String dateCreated = "";

    public Account(){

    }

    /**
     * Sets the username field
     * @param username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Sets the password field
     * @param password
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Sets the email field
     * @param email
     */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Sets the displayName field
     * @param displayName
     */
    public void setDisplayName(String displayName){
       this.displayName = displayName;
    }

    /**
     * Sets the dateCreated field
     * @param dateCreated
     */
    public void setDateCreated(String dateCreated){
        this.dateCreated = dateCreated;
    }

    /**
     * Returns the value of the username field
     * @return
     */
    public String getUsername(){
        return username;
    }

    /**
     * Returns the value of the password field
     * @return
     */
    public String getPassword(){
        return password;
    }

    /**
     * Returns the value of the email field
     * @return
     */
    public String getEmail(){
        return email;
    }

    /**
     * Returns the value of the displayName field
     * @return
     */
    public String getDisplayName(){
        return displayName;
    }

    /**
     * Returns the value of the dateCreated field
     * @return
     */
    public String getDateCreated(){
        return dateCreated;
    }

    /**
     * Sets string representation of the account object
     * @return
     */
    @Override
    public String toString(){
        String s = "{User: "+username+" | Pass: "+password+" | Email: "+email+" | DisplayName: "+displayName+"}\n";
        return s;
    }

}
