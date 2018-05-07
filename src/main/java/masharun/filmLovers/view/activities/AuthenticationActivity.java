package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.UserDAO;
import masharun.filmLovers.model.Hasher;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static masharun.filmLovers.view.Config.TITLES.AUTHENTICATION_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;

public class AuthenticationActivity implements Activity {
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
    
    public AuthenticationActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( AUTHENTICATION_FORM_TITLE );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListener -> Main.getMainActivity().setLastForm() );
        
        enterButton.addActionListener( eventListener -> {
            try {
                Main.setCurrentUser( new UserDAO().getIfExists( loginField.getText(),
                        Hasher.getSecurePassword( passwordField.getText() ) ) );
            } catch( SQLException | NullPointerException | NoSuchAlgorithmException e ) {
                OptionPane.showMessage( "Ошибка входа в аккаунт", "Ошибка" );
                System.err.println( e.toString() );
            }
            if( Main.getCurrentUser().getLogin() != null ) {
                Main.getMainActivity().clearStackFrame();
                OptionPane.showMessage( "Вы успешно вошли в аккаунт", "Поздравляем" );
                if( Main.getCurrentUser().getRole().equals( "A" ) ) {
                    Main.getMainActivity().setNewForm( new AdminActivity() );
                } else {
                    Main.getMainActivity().setNewForm( new FilmsActivity() );
                }
            } else {
                OptionPane.showMessage( "Ошибка ввода логина/пароля", "Ошибка" );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( AUTHENTICATION_FORM_TITLE );
    }
}
