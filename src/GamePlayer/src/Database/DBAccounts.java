/**
 * This class is responsible for retrieving or storing any information pertinent to accounts in a mySQL database. This class extends DBManager so it can use 2 general query methods that either retrieve data or modify data in the database. This class depends on having an active database set up that it can query and depends on the methods in the superclass DBManager. This class should be used for any manipulation or retrieval of the information storing account data. To do so, declare an instance of it in code and call the public methods listed below with appropriate input parameters.
 * @author Connor Ghazaleh
 */
package Database;

import Accounts.ErrorHandler;
import Accounts.MakeAlert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DBAccounts extends DBManager{

    private ErrorHandler myErrorHandler = new ErrorHandler();
    private MakeAlert myAlert = new MakeAlert();

    public DBAccounts(){
        super();
    }

    /**
     * Defines a new account and stores it in a mySQL database
     * @param username username for account
     * @param pass password for account
     * @param email email for account
     * @param displayName display name for account
     */
    public void createAccount(String username, String pass, String email, String displayName){
        ArrayList<Object> accounts = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,accounts);
        super.selectQuery("SELECT * FROM user_info.account_info " +
                "WHERE username = '"+username+"';",consumer);
        if (!accounts.isEmpty()){
            System.out.println(accounts);
            System.out.println("Username is already taken");
            myErrorHandler.showError("usernameExists");
        }else{
            super.insertQuery("INSERT INTO user_info.account_info VALUE (" +
                    "'"+username+"'," +
                    "'"+pass+"'," +
                    "'"+email+"'," +
                    "'"+displayName+"'," +
                    "NOW()," +
                    "NULL);");
        }
    }

    /**
     * Changes the email linked to a particular account
     * @param username username for the account
     * @param email new email for the account
     * @return
     */
    public boolean changeEmail(String username, String email){
        boolean doesExist = checkForAccount(username);
        if (doesExist){
            String query = "UPDATE user_info.account_info SET email = '"+email+"' WHERE username = '"+username+"'";
            super.insertQuery(query);
        }
        return doesExist;
    }

    /**
     * Deletes an account from the mySQL database
     * @param username username linked to account to be deleted
     */
    public void deleteAccount(String username){
        String query = "DELETE FROM user_info.account_info WHERE username = '"+username+"'";
        super.insertQuery(query);
    }

    /**
     * Checks that a username/password combination is valid. Returns false if password is incorrect or account does not exist.
     * @param username username for attempted login
     * @param password password for attempted login
     * @return true for succesful login, else false
     */
    public boolean authenticate(String username, String password){
        Account acct = retrieveAccount(username);
        if (acct != null) {
            return acct.getPassword().equals(password);
        }
        return false;
    }

    /**
     * Retrieves all account data associated with a particular username
     * @param username username for account retrieval
     * @return Account object for specified username
     */
    public Account retrieveAccount(String username){
        ArrayList<Object> accounts = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,accounts);
        String query = "SELECT * FROM user_info.account_info WHERE username = '"+username+"'";
        super.selectQuery(query,consumer);
        if (accounts.isEmpty()){
            return null;
        }else {
            return ((Account) accounts.get(0));
        }
    }

    /**
     * Retrieves entirety of accounts table in mySQL database, i.e. retrieves account data for every account.
     * @return List of account objects
     */
    public List<Account> retrieveAllAccounts(){
        ArrayList<Object> accounts = new ArrayList<>();
        ArrayList<Account> allAccounts = new ArrayList<>();
        Consumer<ResultSet> consumer = (x) -> populateArray(x,accounts);
        String query = "SELECT * FROM user_info.account_info;";
        super.selectQuery(query,consumer);
        for (Object acct : accounts){
            allAccounts.add((Account)acct);
        }
        return allAccounts;
    }

    /**
     * Check that account with specified username exists
     * @param username username that is being searched
     * @return true if account exists, else false
     */
    public boolean checkForAccount(String username){
        Account acct = retrieveAccount(username);
        return acct != null;
    }



    private void populateArray(ResultSet rs, List<Object> accounts){
        try{
            while(rs.next()){
                Account acct = new Account();
                acct.setUsername(rs.getString(1));
                acct.setPassword(rs.getString(2));
                acct.setEmail(rs.getString(3));
                acct.setDisplayName(rs.getString(4));
                acct.setDateCreated(rs.getDate(5).toString());
                accounts.add(acct);
            }
        }catch(SQLException e){
            System.out.println("Could not extract results from result set");
        }
    }


}
