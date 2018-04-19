package masharun.filmLovers.connection;

import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static masharun.filmLovers.connection.ConnectionConfig.*;

public class ConnectionManager {
    
    private static Connection connection;
    
    public static void initialize() throws SQLException, ClassNotFoundException {
        if ( connection == null ) {
            Class.forName( DRIVER_PATH );
            connection = DriverManager.getConnection( URL, USER, PASSWORD );
        }
    }
    
    public static void close() {
        try {
            connection.close();
            connection = null;
        } catch ( SQLException e ) {
            System.err.println( e.toString() );
        }
    }
    
    public static Connection getConnection() {
        return connection;
    }
    
}
