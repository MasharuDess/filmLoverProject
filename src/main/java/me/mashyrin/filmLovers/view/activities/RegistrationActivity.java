package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.UserDAO;
import me.mashyrin.filmLovers.model.Hasher;
import me.mashyrin.filmLovers.model.entities.User;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.NUMBERS.CURRENT_YEAR;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.REGISTRATION_ACTIVITY_TITLE;

public class RegistrationActivity implements Activity {
    private JPanel rootPanel;
    private JPanel footerPanel;
    private JPanel headerPanel;
    private JButton backButton;
    private JLabel mainTitle;
    private JButton createButton;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JPasswordField repeatPasswordField;
    private JTextField nameField;
    private JTextField surnameField;
    private JComboBox birthdayComboBox;
    private JLabel loginLabel;
    private JLabel passwordLabel;
    private JLabel repeatPasswordLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel birthdayLabel;
    private JLabel helpLabel;
    
    public RegistrationActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( REGISTRATION_ACTIVITY_TITLE );
        birthdayComboBox.setSelectedIndex( -1 );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListener ->
                Main.getMainActivity().setLastForm()
        );
        
        createButton.addActionListener( eventListener -> {
            if( !loginField.getText().equals( "" ) && !passwordField.getText().equals( "" ) &&
                    !repeatPasswordField.getText().equals( "" ) ) {
                try {
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.selectById( loginField.getText() );
                    if( user.getLogin() != null ) {
                        OptionPane.showMessage( "Такой логин уже сущетвует", ERROR_TITLE );
                    } else if( !passwordField.getText().equals( repeatPasswordField.getText() ) ) {
                        OptionPane.showMessage( "Неверное повторное введение пароля", ERROR_TITLE );
                    } else {
                        user.setLogin( loginField.getText() );
                        user.setRole( "U" );
                        user.setSurname( surnameField.getText() );
                        user.setName( nameField.getText() );
                        if( birthdayComboBox.getSelectedItem() != null ) {
                            user.setBirthday( CURRENT_YEAR - Integer.parseInt( birthdayComboBox.getItemAt( birthdayComboBox.getSelectedIndex() ).toString() ) );
                        } else {
                            user.setBirthday( 1 );
                        }
                        user.setPassword( Hasher.getSecurePassword( passwordField.getText() ) );
                        userDAO.insert( user );
                        userDAO.update( user );
                        Main.setCurrentUser( user );
                        Main.getMainActivity().clearStackFrame();
                        OptionPane.showMessage( "Вы успешно создали аккаунт", CONGRATULATION_TITLE );
                        Main.getMainActivity().setNewForm( new FilmsActivity() );
                    }
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка создания аккаунта", ERROR_TITLE );
                    System.err.println( e.toString() );
                } catch( NoSuchAlgorithmException e ) {
                    OptionPane.showMessage( "Ошибка добавление пароля", ERROR_TITLE );
                    System.err.println( e.toString() );
                }
            } else {
                OptionPane.showMessage( "Вы не ввели обязательное поле", ERROR_TITLE );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( REGISTRATION_ACTIVITY_TITLE );
    }
}
