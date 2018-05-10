package me.mashyrin.filmLovers.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static me.mashyrin.filmLovers.controller.ConnectionConfig.*;

/**
 * Connection to database class
 *
 * @author Mashyrin
 */
public class ConnectionManager {
    
    private static Connection connection;
    
    /**
     * Creating connection object to connect to database
     *
     * @throws SQLException           - SQL error into database
     * @throws ClassNotFoundException - Throws when connection class not found
     */
    public static void initialize() throws SQLException, ClassNotFoundException {
        if( connection == null ) {
            Class.forName( DRIVER_PATH );
            connection = DriverManager.getConnection( URL, USER, PASSWORD );
        }
    }
    
    /**
     * Closing connection to database
     */
    public static void close() {
        try {
            connection.close();
            connection = null;
        } catch( SQLException e ) {
            System.err.println( e.toString() );
        }
    }
    
    /**
     * @return returns connection object
     */
    public static Connection getConnection() {
        return connection;
    }
    
}
