/**
 * This class is the super class for all of the database classes. It defines the methods selectQuery() and insertQuery() that are common to each of the database classes to be used in the inheritance hierarchy.
 * @author Connor Ghazaleh
 */

package Database;

import java.sql.*;
import java.util.function.Consumer;

public abstract class DBManager {

    private static final String CONNECTION_STRING = "jdbc:mysql://152.3.52.155:3306/user_info?" +
            "useUnicode=true&useJDBCCompliantTimezoneShift=true" +
            "&useLegacyDatetimeCode=false&serverTimezone=EST&useSSL=false";
    private static final String USER = "admin_user";
    private static final String PASS = "voogasalad123!";


    public DBManager(){
        loadJDBC();
    }

    /**
     * Sends a select query to the database to return information based on filters
     * @param query query string that is sent to the database
     * @param c lambda function defining what to do with the results once they are received.
     */
    public void selectQuery(String query, Consumer<ResultSet> c){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            connection = connectToDB();
            statement = makeStatement(connection);
            resultSet = executeReadQuery(statement,query);
            c.accept(resultSet);
        }catch(SQLException e){
            System.out.println("Failed to query database");
        }
        finally {
            closeConnection(connection,statement,resultSet,true);
        }
    }

    /**
     * Sends a query that modifies the data in the database based on certain filters and/or parameters
     * @param query query string that is sent to the database
     */
    public void insertQuery(String query){
        Connection connection = null;
        Statement statement = null;
        int result = 0;
        try{
            connection = connectToDB();
            statement = makeStatement(connection);
            result = executeManipQuery(statement,query);
        }catch(SQLException e){
            System.out.println("Failed to query database");
        }
        finally {
            System.out.println(result);
            closeConnection(connection,statement,null,false);
        }
    }

    private void loadJDBC(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException cnfex) {
            System.out.println("Problem loading MySQL JDBC driver");
        }
    }

    private Connection connectToDB() throws SQLException {
        Connection connection = DriverManager.getConnection(
                    CONNECTION_STRING,
                    USER,
                    PASS);
        return connection;
    }

    private Statement makeStatement(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        return statement;
    }

    private ResultSet executeReadQuery(Statement statement, String queryString) throws SQLException {
        ResultSet resultSet = statement.executeQuery(queryString);
        return resultSet;
    }

    private int executeManipQuery(Statement statement, String queryString) throws SQLException {
        int resultSet = statement.executeUpdate(queryString);
        return resultSet;
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet, boolean isRead){
        try {
            if(null != connection) {
                if (isRead){
                    resultSet.close();
                }
                statement.close();
                connection.close();
            }
        }catch (SQLException sqlex) {
            System.out.println("Could not close connection to database");
        }
    }


}
