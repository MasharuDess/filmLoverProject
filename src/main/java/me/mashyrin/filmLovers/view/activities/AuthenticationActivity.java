package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.UserDAO;
import me.mashyrin.filmLovers.model.Hasher;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.AUTHENTICATION_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

/**
 * Authentication activity class
 *
 * @author mashyrin
 */
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
    
    /**
     * Authentication activity constructor
     */
    public AuthenticationActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( AUTHENTICATION_ACTIVITY_TITLE );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListener -> Main.getMainActivity().setLastForm() );
        
        enterButton.addActionListener( eventListener -> {
            try {
                Main.setCurrentUser( new UserDAO().getIfExists( loginField.getText(),
                        Hasher.getSecurePassword( passwordField.getText() ) ) );
            } catch( SQLException | NullPointerException | NoSuchAlgorithmException e ) {
                OptionPane.showMessage( "Ошибка входа в аккаунт", ERROR_TITLE );
                System.err.println( e.toString() );
            }
            if( Main.getCurrentUser().getLogin() != null ) {
                Main.getMainActivity().clearStackFrame();
                OptionPane.showMessage( "Вы успешно вошли в аккаунт", CONGRATULATION_TITLE );
                if( Main.getCurrentUser().getRole().equals( "A" ) ) {
                    Main.getMainActivity().setNewForm( new AdminActivity() );
                } else {
                    Main.getMainActivity().setNewForm( new FilmsActivity() );
                }
            } else {
                OptionPane.showMessage( "Ошибка ввода логина/пароля", ERROR_TITLE );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( AUTHENTICATION_ACTIVITY_TITLE );
    }
}
