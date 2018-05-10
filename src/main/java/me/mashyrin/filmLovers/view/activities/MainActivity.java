package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.controller.ConnectionManager;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import java.sql.SQLException;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_FORM_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

/**
 * Main activity class. Calls when program start
 *
 * @author mashyrin
 */
public class MainActivity implements Activity {
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
    
    /**
     * Main activity constructor
     */
    public MainActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( MAIN_FORM_TITLE );
        init();
        initListeners();
    }
    
    private void init() {
        if( ConnectionManager.getConnection() == null ) {
            reconnectButton.setEnabled( true );
            reconnectButton.setVisible( true );
        }
    }
    
    private void initListeners() {
        logButton.addActionListener( eventListener -> {
            if( ConnectionManager.getConnection() != null ) {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm( new AuthenticationActivity() );
            } else {
                OptionPane.showMessage(
                        "Необходимо установить подключение", ERROR_TITLE );
            }
        } );
        
        regButton.addActionListener( eventListener -> {
            if( ConnectionManager.getConnection() != null ) {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm( new RegistrationActivity() );
            } else {
                OptionPane.showMessage(
                        "Необходимо установить подключение", ERROR_TITLE );
            }
        } );
        
        reconnectButton.addActionListener( eventListener -> {
            try {
                ConnectionManager.initialize();
                reconnectButton.setEnabled( false );
                reconnectButton.setVisible( false );
                OptionPane.showMessage( "Подключение успешно установлено", CONGRATULATION_TITLE );
            } catch( SQLException | ClassNotFoundException e ) {
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
        Config.TITLES.setTitle( MAIN_FORM_TITLE );
    }
}
