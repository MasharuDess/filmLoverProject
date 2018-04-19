package masharun.filmLovers.view.forms;

import masharun.filmLovers.Main;
import masharun.filmLovers.models.DAO.UserDAO;
import masharun.filmLovers.models.Hasher;
import masharun.filmLovers.models.entities.User;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

public class AuthenticationForm implements Form {
    private JPanel rootPanel;
    private JLabel mainTitle;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton backButton;
    private JButton enterButton;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JTextField loginField;
    private JPasswordField passwordField;
    
    public AuthenticationForm() {
        
        Main.setTitle( "Аутентификация" );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListener ->
            Main.getMainForm().setLastForm()
        );
        
        enterButton.addActionListener( eventListener -> {
            try {
                Main.setCurrentUser( new UserDAO().getIfExists( loginField.getText(),
                        Hasher.getSecurePassword( passwordField.getText())));
            } catch ( SQLException | NullPointerException | NoSuchAlgorithmException e ) {
                OptionPane.showMessage( "Ошибка входа в аккаунт", "Ошибка" );
                System.err.println( e.toString() );
            }
            if ( Main.getCurrentUser().getLogin() != null ) {
                Main.getMainForm().clearStackFrame();
                OptionPane.showMessage( "Вы успешно вошли в аккаунт", "Поздравляем" );
                if ( Main.getCurrentUser().getRole().equals( "A" ) ) {
                    Main.getMainForm().setNewForm( new AdminForm() );
                } else {
                    Main.getMainForm().setNewForm( new FilmsForm() );
                }
            } else {
                OptionPane.showMessage("Ошибка ввода логина/пароля", "Ошибка" );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle( "Аутентификация" );
    }
}
