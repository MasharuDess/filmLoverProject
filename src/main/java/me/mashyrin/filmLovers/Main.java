package me.mashyrin.filmLovers;

import me.mashyrin.filmLovers.controller.ConnectionManager;
import me.mashyrin.filmLovers.model.entities.User;
import me.mashyrin.filmLovers.view.OptionPane;
import me.mashyrin.filmLovers.view.activities.MainActivity;

import java.sql.SQLException;

/**
 * Main program class
 *
 * @author mashyrin
 */
public class Main {
    private static final BaseActivity MAIN_ACTIVITY = new BaseActivity();
    private static User currentUser;
    
    /**
     * Starting program and calling base activity
     *
     * @param args
     */
    public static void main( String[] args ) {
        try {
            ConnectionManager.initialize();
        } catch( SQLException | ClassNotFoundException e ) {
            OptionPane.showMessage( e.toString(), "Ошибка подключения" );
        }
        MAIN_ACTIVITY.setNewForm( new MainActivity() );
        MAIN_ACTIVITY.setVisible( true );
    }
    
    /**
     * Setting application title
     *
     * @param title
     */
    public static void setTitle( String title ) {
        MAIN_ACTIVITY.setTitle( title );
    }
    
    /**
     * @return current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }
    
    /**
     * @param currentUser
     */
    public static void setCurrentUser( User currentUser ) {
        Main.currentUser = currentUser;
    }
    
    /**
     * @return base activity
     */
    public static BaseActivity getMainActivity() {
        return MAIN_ACTIVITY;
    }
}
