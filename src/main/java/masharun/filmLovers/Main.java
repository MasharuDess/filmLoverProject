package masharun.filmLovers;

import masharun.filmLovers.connection.ConnectionManager;
import masharun.filmLovers.models.entities.User;
import masharun.filmLovers.view.OptionPane;
import masharun.filmLovers.view.forms.MainForm;

import java.sql.SQLException;

import static masharun.filmLovers.view.Config.MAIN_TITLE;

public class Main {
    
    private static final BaseForm MAIN_FORM = new BaseForm();
    private static User currentUser;
    
    public static void main( String[] args ) {
        try {
            ConnectionManager.initialize();
        } catch ( SQLException | ClassNotFoundException e ) {
            OptionPane.showMessage( e.toString(), "Ошибка подключения" );
        }
        MAIN_FORM.setNewForm( new MainForm() );
        MAIN_FORM.setVisible( true );
    }
    
    public static void setTitle() {
        MAIN_FORM.setTitle( MAIN_TITLE );
    }
    
    public static void setTitle( String title ) {
        MAIN_FORM.setTitle( MAIN_TITLE + " - " + title );
    }
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
    public static void setCurrentUser( User currentUser ) {
        Main.currentUser = currentUser;
    }
    
    public static BaseForm getMainForm() {
        return MAIN_FORM;
    }
}
