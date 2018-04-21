package masharun.filmLovers.view.forms;

import jdk.nashorn.internal.ir.Optimistic;
import masharun.filmLovers.Main;
import masharun.filmLovers.models.DAO.UserDAO;
import masharun.filmLovers.models.Hasher;
import masharun.filmLovers.models.entities.User;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.sql.SQLException;

import static masharun.filmLovers.view.Config.CURRENT_YEAR;

public class RegistrationForm implements Form {
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
    
    public RegistrationForm() {
        Main.setTitle( "Регистрация" );
        birthdayComboBox.setSelectedIndex( -1 );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListener ->
            Main.getMainForm().setLastForm()
        );
        
        createButton.addActionListener( eventListener -> {
            if ( !loginField.getText().equals( "" ) && !passwordField.getText().equals( "" ) &&
                    !repeatPasswordField.getText().equals( "" )) {
                try {
                    UserDAO userDAO = new UserDAO();
                    User user = userDAO.selectById( loginField.getText() );
                    if( user.getLogin() != null ) {
                        OptionPane.showMessage( "Такой логин уже сущетвует", "Ошибка" );
                    } else if ( !passwordField.getText().equals( repeatPasswordField.getText() ) ) {
                        OptionPane.showMessage( "Неверное повторное введение пароля", "Ошибка" );
                    } else {
                        user.setLogin( loginField.getText() );
                        user.setRole( "U" );
                        user.setSurname( surnameField.getText() );
                        user.setName( nameField.getText() );
                        if ( birthdayComboBox.getSelectedItem() != null ) {
                            user.setBirthday( CURRENT_YEAR - Integer.parseInt( birthdayComboBox.getItemAt(                                      birthdayComboBox.getSelectedIndex() ).toString() ) );
                        } else {
                            user.setBirthday( 1 );
                        }
                        user.setPassword( Hasher.getSecurePassword( passwordField.getText()) );
                        userDAO.insert( user );
                        userDAO.update( user );
                        Main.setCurrentUser( user );
                        Main.getMainForm().clearStackFrame();
                        OptionPane.showMessage( "Вы успешно создали аккаунт", "Поздравляем" );
                        Main.getMainForm().setNewForm( new FilmsForm() );
                    }
                } catch ( SQLException e ) {
                    OptionPane.showMessage( "Ошибка создания аккаунта", "Ошибка" );
                    System.err.println( e.toString() );
                } catch ( NoSuchAlgorithmException e ) {
                    OptionPane.showMessage( "Ошибка добавление пароля", "Ошибка" );
                    System.err.println( e.toString() );
                }
            } else {
                OptionPane.showMessage( "Вы не ввели обязательное поле", "Ошибка" );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle( "Регистрация" );
    }
}
