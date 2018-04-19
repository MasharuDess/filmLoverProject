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

import static masharun.filmLovers.view.Config.CURRENT_YEAR;
import static masharun.filmLovers.view.Config.YEAR_COUNT;

public class UpdateUserForm implements Form {
    private JPanel rootPanel;
    private JPanel HeaderPanel;
    private JPanel FooterPanel;
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
    
    public UpdateUserForm() {
        Main.setTitle( "Настройка аккаунта" );
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
                    birthdayComboBox.getSelectedIndex() ).toString()));
            try {
                new UserDAO().update( Main.getCurrentUser() );
                reinit();
            } catch ( SQLException e ) {
                OptionPane.showMessage( "Ошибка изменения пользователя", "Ошибка" );
            }
        } );
        
        editPasswordButton.addActionListener( eventListener -> {
            if ( passwordField.getText().equals( repeatPasswordField.getText() ) ) {
                try {
                    Main.getCurrentUser().setPassword(Hasher.getSecurePassword(
                            passwordField.getText()));
                    new UserDAO().update( Main.getCurrentUser() );
                    OptionPane.showMessage( "Пароль успешно изменён", "Поздравляем" );
                } catch ( SQLException | NoSuchAlgorithmException e ) {
                    OptionPane.showMessage( "Ошибка изменения пароля", "Ошибка" );
                }
            } else {
                OptionPane.showMessage( "Неверное повторное введение пароля", "Ошибка" );
            }
        } );
        
        backButton.addActionListener( eventListener ->
            Main.getMainForm().setLastForm()
        );
        
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainForm().clearStackFrame();
            Main.getMainForm().setNewForm( new MainForm() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle( "Настройка аккаунта" );
        if ( Main.getCurrentUser().getName() == null && Main.getCurrentUser().getSurname() == null ) {
            userInfoLabel.setText( Main.getCurrentUser().getLogin() );
        } else {
            userInfoLabel.setText( new StringBuilder( Main.getCurrentUser().getName() )
                    .append( " " )
                    .append( Main.getCurrentUser().getSurname() )
                    .append( ", " )
                    .append( Main.getCurrentUser().getBirthday())
                    .toString()
            );
        }
    }
}
