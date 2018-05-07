package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.UserDAO;
import masharun.filmLovers.model.Hasher;
import masharun.filmLovers.model.entities.User;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static masharun.filmLovers.view.Config.CURRENT_YEAR;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static masharun.filmLovers.view.Config.TITLES.REGISTRATION_FORM_TITLE;

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
        Config.TITLES.setTitle( REGISTRATION_FORM_TITLE );
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
                        OptionPane.showMessage( "Такой логин уже сущетвует", "Ошибка" );
                    } else if( !passwordField.getText().equals( repeatPasswordField.getText() ) ) {
                        OptionPane.showMessage( "Неверное повторное введение пароля", "Ошибка" );
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
                        OptionPane.showMessage( "Вы успешно создали аккаунт", "Поздравляем" );
                        Main.getMainActivity().setNewForm( new FilmsActivity() );
                    }
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка создания аккаунта", "Ошибка" );
                    System.err.println( e.toString() );
                } catch( NoSuchAlgorithmException e ) {
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
        Config.TITLES.setTitle( REGISTRATION_FORM_TITLE );
    }
}
