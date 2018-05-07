package masharun.filmLovers;

import masharun.filmLovers.controller.ConnectionManager;
import masharun.filmLovers.model.entities.User;
import masharun.filmLovers.view.OptionPane;
import masharun.filmLovers.view.activities.MainActivity;

import java.sql.SQLException;

public class Main {
    
    private static final BaseActivity MAIN_ACTIVITY = new BaseActivity();
    private static User currentUser;
    
    public static void main( String[] args ) {
        System.err.println();
        try {
            ConnectionManager.initialize();
        } catch( SQLException | ClassNotFoundException e ) {
            OptionPane.showMessage( e.toString(), "Ошибка подключения" );
        }
        MAIN_ACTIVITY.setNewForm( new MainActivity() );
        MAIN_ACTIVITY.setVisible( true );
    }
    
    public static void setTitle( String title ) {
        MAIN_ACTIVITY.setTitle( title );
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void setCurrentUser( User currentUser ) {
        Main.currentUser = currentUser;
    }
    
    public static BaseActivity getMainActivity() {
        return MAIN_ACTIVITY;
    }
}
