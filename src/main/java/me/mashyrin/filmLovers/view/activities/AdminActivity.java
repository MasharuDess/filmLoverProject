package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.UserDAO;
import me.mashyrin.filmLovers.model.entities.User;
import me.mashyrin.filmLovers.model.tableModels.AdminTableModel;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.ADMIN_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

/**
 * Admin activity class
 *
 * @author mashyrin
 */
public class AdminActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JLabel mainTitle;
    private JButton deleteButton;
    private JTable table;
    private JLabel yourInfoLabel;
    private JTextField searchField;
    private JComboBox roleComboBox;
    private JButton updateRoleButton;
    private TableRowSorter<TableModel> rowSorter;
    
    /**
     * Admin activity constructor
     */
    public AdminActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ADMIN_ACTIVITY_TITLE );
        init();
        initListeners();
    }
    
    private void init() {
        yourInfoLabel.setText( Main.getCurrentUser().getLogin() );
        table.setModel( new AdminTableModel() );
        rowSorter = new TableRowSorter<>( table.getModel() );
        table.setRowSorter( rowSorter );
    }
    
    private void initListeners() {
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        table.getSelectionModel().addListSelectionListener( selectionListener -> {
            roleComboBox.setEnabled( true );
            roleComboBox.setVisible( true );
            
            if( table.getValueAt( table.getSelectedRow(), 4 ).equals( "A" ) ) {
                roleComboBox.setSelectedIndex( 0 );
            } else if( table.getValueAt( table.getSelectedRow(), 4 ).equals( "U" ) ) {
                roleComboBox.setSelectedIndex( 1 );
            } else if( table.getValueAt( table.getSelectedRow(), 4 ).equals( "C" ) ) {
                roleComboBox.setSelectedIndex( 2 );
            } else if( table.getValueAt( table.getSelectedRow(), 4 ).equals( "S" ) ) {
                roleComboBox.setSelectedIndex( 3 );
            }
        } );
        
        updateRoleButton.addActionListener( eventListener -> {
            try {
                User user = new UserDAO().selectById(
                        table.getValueAt( table.getSelectedRow(), 0 ).toString() );
                user.setRole( roleComboBox.getSelectedItem().toString().substring( 0, 1 ) );
                new UserDAO().update( user );
                
                table.updateUI();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка загрузки данных", ERROR_TITLE );
                System.err.println( e.toString() );
            }
        } );
        
        deleteButton.addActionListener( eventListener -> {
            try {
                String buffLogin = table.getModel().getValueAt( table.getSelectedRow(), 0 ).toString();
                if( buffLogin.equals( "root" ) ) {
                    OptionPane.showMessage( "root не может быть удалён", ERROR_TITLE );
                } else if( buffLogin.equals( Main.getCurrentUser().getLogin() ) ) {
                    OptionPane.showMessage( "Нельзя удалить собственный аккаунт", ERROR_TITLE );
                } else {
                    try {
                        new UserDAO().deleteById( buffLogin );
                        table.updateUI();
                    } catch( SQLException e ) {
                        OptionPane.showMessage( "Ошибка удаления пользователя", ERROR_TITLE );
                        System.err.println( e.toString() );
                    }
                }
            } catch( ArrayIndexOutOfBoundsException e ) {
                OptionPane.showMessage( "Вы не выбрали запись", ERROR_TITLE );
                System.err.println( e.toString() );
            }
        } );
        
        searchField.getDocument().addDocumentListener( new DocumentListener() {
            @Override
            public void insertUpdate( DocumentEvent e ) {
                String text = searchField.getText();
                
                if( text.trim().length() == 0 ) {
                    rowSorter.setRowFilter( null );
                } else {
                    rowSorter.setRowFilter( RowFilter.regexFilter( "(?i)" + text ) );
                }
            }
            
            @Override
            public void removeUpdate( DocumentEvent e ) {
                insertUpdate( e );
            }
            
            @Override
            public void changedUpdate( DocumentEvent e ) {
                //Nothing
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( ADMIN_ACTIVITY_TITLE );
    }
}
