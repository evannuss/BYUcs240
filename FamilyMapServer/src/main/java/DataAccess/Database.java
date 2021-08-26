package DataAccess;

import java.sql.*;

/**
 * This class handles the opening and closing of the database connection,
 * as well as the ability to clear all the tables in the database.
 */
public class Database {

    private Connection conn;

    /**
     * Used to open a connection to the database.
     * @return Return the connection that was created.
     */
    public Connection openConnection() throws DataAccessException {
        System.out.println("Opening connection...");
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Used to get the connection that's created.
     * @return Return the connection created by openConnection().
     */
    public Connection getConnection()throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }
    /**
     * Used to close the database connection at the end of a transaction.
     * @param commit Used to determine if we should commit the changes made in
     *               the transaction or if we should rollback those changes.
     */
    public void closeConnection(boolean commit)throws DataAccessException {
        System.out.println("Closing connection...");
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    /**
     * Used to clear all the rows of each table in the database.
     */
    public void clearTables()throws DataAccessException {
        new AuthTokenDao(conn).clear();
        new EventDao(conn).clear();
        new PersonDao(conn).clear();
        new UserDao(conn).clear();
    }
}
