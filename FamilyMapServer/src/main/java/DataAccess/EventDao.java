package DataAccess;

import Model.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to access the events of the database through the connection generated in
 * the Database class.
 */
public class EventDao {

    private final Connection conn;

    /**
     * Parameterized constructor used to get the connection.
     * @param conn A database connection generated by the Database class.
     */
    public EventDao (Connection conn){
        this.conn = conn;
    }

    /**
     * Used to insert a new event into the database with all its needed information.
     * @param event An event object that has already been created elsewhere is passed in
     *              and translated into a SQL insert command.
     */
    public void insert (Event event)throws DataAccessException{
        String sql = "INSERT INTO events (event_id, assoc_username, person_id, latitude, longitude, " +
                "country, city, event_type, year) VALUES(?,?,?,?,?,?,?,?,?);";

        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.clearParameters();
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setDouble(4, event.getLatitude());
            stmt.setDouble(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9,event.getYear());

            stmt.executeUpdate();
        }
        catch(SQLException ex){
            ex.printStackTrace();
            throw new DataAccessException("There was an error while trying to insert.");
        }
    }

    /**
     * Used to find a single event in the database.
     * @param event_id The unique event ID that will be used to find the event we want.
     * @return Find the event in the database and return it as an actual Event object.
     */
    public Event find(String event_id)throws DataAccessException{
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE event_id = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, event_id);
            rs = stmt.executeQuery();
            if(rs.next()){
                event = new Event(rs.getString("event_id"), rs.getString("assoc_username"),
                        rs.getString("person_id"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("event_type"),
                        rs.getInt("year"));
                return event;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("There was an error trying to find the given event.");
        }
        finally { //Finally block to make sure the result set gets closed with no errors.
            if(rs != null){
                try{
                    rs.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    throw new DataAccessException("There was an error trying to close the result set");
                }
            }
        }
        return null;
    }

    /**
     * Used to completely clear the Event table
     */
    public void clear() throws DataAccessException{
        String sql = "DELETE FROM events";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while clearing the auth_tokens table.");
        }
    }

    //This function is for deleting all events in the database associated with the passed in username.
    public void deleteList(String username) throws DataAccessException {
        String sql = "DELETE FROM events WHERE assoc_username = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, username);
            stmt.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error encountered while deleting list of events.");
        }
    }

    //Used to get a List of all the events associated with the passed in username.
    public List<Event> getList(String username) throws DataAccessException{
        ResultSet rs = null;
        ArrayList<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM events WHERE assoc_username = ?;";
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while(rs.next()){
                Event event = new Event(rs.getString("event_id"), rs.getString("assoc_username"),
                        rs.getString("person_id"), rs.getDouble("latitude"),
                        rs.getDouble("longitude"), rs.getString("country"),
                        rs.getString("city"), rs.getString("event_type"), rs.getInt("year"));
                events.add(event);
            }
            return events;
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new DataAccessException("Error while getting list of all people from database.");
        }
        finally {
            if(rs != null){
                try{
                    rs.close();
                }
                catch(SQLException e){
                    e.printStackTrace();
                    throw new DataAccessException("There was an error trying to close the result set");
                }
            }
        }
    }
}
