package masharun.filmLovers.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static masharun.filmLovers.controller.ConnectionConfig.*;

public class ConnectionManager {
    
    private static Connection connection;
    
    public static void initialize() throws SQLException, ClassNotFoundException {
        if( connection == null ) {
            Class.forName( DRIVER_PATH );
            connection = DriverManager.getConnection( URL, USER, PASSWORD );
        }
    }
    
    public static void close() {
        try {
            connection.close();
            connection = null;
        } catch( SQLException e ) {
            System.err.println( e.toString() );
        }
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
}
