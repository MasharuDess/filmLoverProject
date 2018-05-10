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
import static me.mashyrin.filmLovers.view.Config.NUMBERS.CURRENT_YEAR;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.UPDATE_USER_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.NUMBERS.YEAR_COUNT;

/**
 * Update user activity class
 *
 * @author mashyrin
 */
public class UpdateUserActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JLabel mainTitle;
    private JButton exitButton;
    private JButton backButton;
    private JButton updateButton;
    private JTextField nameField;
    private JTextField surnameField;
    private JComboBox birthdayComboBox;
    private JLabel birthdayLabel;
    private JButton editPasswordButton;
    private JLabel userInfoLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JPasswordField repeatPasswordField;
    private JPasswordField passwordField;
    private JLabel passwordLabel;
    private JLabel repeatPassword;
    
    /**
     * Update user activity constructor
     */
    public UpdateUserActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( UPDATE_USER_ACTIVITY_TITLE );
        reinit();
        initFields();
        initListeners();
    }
    
    private void initFields() {
        nameField.setText( Main.getCurrentUser().getName() );
        surnameField.setText( Main.getCurrentUser().getSurname() );
        birthdayComboBox.setSelectedIndex( YEAR_COUNT - Main.getCurrentUser().getBirthday() );
    }
    
    private void initListeners() {
        
        updateButton.addActionListener( eventListener -> {
            Main.getCurrentUser().setName( nameField.getText() );
            Main.getCurrentUser().setSurname( surnameField.getText() );
            Main.getCurrentUser().setBirthday( CURRENT_YEAR - Integer.parseInt( birthdayComboBox.getItemAt(
                    birthdayComboBox.getSelectedIndex() ).toString() ) );
            try {
                new UserDAO().update( Main.getCurrentUser() );
                reinit();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка изменения пользователя", ERROR_TITLE );
            }
        } );
        
        editPasswordButton.addActionListener( eventListener -> {
            if( passwordField.getText().equals( repeatPasswordField.getText() ) ) {
                try {
                    Main.getCurrentUser().setPassword( Hasher.getSecurePassword(
                            passwordField.getText() ) );
                    new UserDAO().update( Main.getCurrentUser() );
                    OptionPane.showMessage( "Пароль успешно изменён", CONGRATULATION_TITLE );
                } catch( SQLException | NoSuchAlgorithmException e ) {
                    OptionPane.showMessage( "Ошибка изменения пароля", ERROR_TITLE );
                }
            } else {
                OptionPane.showMessage( "Неверное повторное введение пароля", ERROR_TITLE );
            }
        } );
        
        backButton.addActionListener( eventListener ->
                Main.getMainActivity().setLastForm()
        );
        
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( UPDATE_USER_ACTIVITY_TITLE );
        if( Main.getCurrentUser().getName() == null && Main.getCurrentUser().getSurname() == null ) {
            userInfoLabel.setText( Main.getCurrentUser().getLogin() );
        } else {
            userInfoLabel.setText( new StringBuilder( Main.getCurrentUser().getName() )
                    .append( " " )
                    .append( Main.getCurrentUser().getSurname() )
                    .append( ", " )
                    .append( Main.getCurrentUser().getBirthday() )
                    .toString()
            );
        }
    }
}
