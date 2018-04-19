package masharun.filmLovers.view.forms;

import masharun.filmLovers.Main;
import masharun.filmLovers.connection.ConnectionManager;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import java.sql.SQLException;

public class MainForm implements Form {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JLabel mainTitle;
    private JButton logButton;
    private JButton regButton;
    private JPanel footerPanel;
    private JLabel regLabel;
    private JLabel logLabel;
    private JLabel footerShowerLabel;
    private JButton reconnectButton;
    
    public MainForm() {
        
        Main.setTitle();
        init();
        initListeners();
    }
    
    private void init() {
        if ( ConnectionManager.getConnection() == null ) {
            reconnectButton.setEnabled( true );
            reconnectButton.setVisible( true );
        }
    }
    
    private void initListeners() {
        logButton.addActionListener( eventListener -> {
            if ( ConnectionManager.getConnection() != null ) {
                Main.getMainForm().pushStackFrame( this );
                Main.getMainForm().setNewForm( new AuthenticationForm() );
            } else {
                OptionPane.showMessage( "Необходимо установить подключение", "Ошибка подключения" );
            }
        } );
        
        regButton.addActionListener( eventListener -> {
            if ( ConnectionManager.getConnection() != null ) {
                Main.getMainForm().pushStackFrame( this );
                Main.getMainForm().setNewForm( new RegistrationForm() );
            } else {
                OptionPane.showMessage( "Необходимо установить подключение", "Ошибка подключения" );
            }
        } );
        
        reconnectButton.addActionListener( eventListener -> {
            try {
                ConnectionManager.initialize();
                reconnectButton.setEnabled( false );
                reconnectButton.setVisible( false );
                OptionPane.showMessage( "Подключение успешно установлено", "Подключение" );
            } catch ( SQLException | ClassNotFoundException e ){
                OptionPane.showMessage( e.toString(), "Ошибка подключения" );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle();
    }
}
