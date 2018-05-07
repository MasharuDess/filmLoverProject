package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.UserDAO;
import masharun.filmLovers.model.entities.User;
import masharun.filmLovers.model.tableModels.AdminTableModel;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;

import static masharun.filmLovers.view.Config.TITLES.ADMIN_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;

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
    
    public AdminActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ADMIN_FORM_TITLE );
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
                OptionPane.showMessage( "Ошибка загрузки данных", "Ошибка" );
                System.err.println( e.toString() );
            }
        } );
        
        deleteButton.addActionListener( eventListener -> {
            try {
                String buffLogin = table.getModel().getValueAt( table.getSelectedRow(), 0 ).toString();
                if( buffLogin.equals( "root" ) ) {
                    OptionPane.showMessage( "root не может быть удалён", "Ошибка" );
                } else if( buffLogin.equals( Main.getCurrentUser().getLogin() ) ) {
                    OptionPane.showMessage( "Нельзя удалить собственный аккаунт", "Ошибка" );
                } else {
                    try {
                        new UserDAO().deleteById( buffLogin );
                        table.updateUI();
                    } catch( SQLException e ) {
                        OptionPane.showMessage( "Ошибка удаления пользователя", "Ошибка" );
                        System.err.println( e.toString() );
                    }
                }
            } catch( ArrayIndexOutOfBoundsException e ) {
                OptionPane.showMessage( "Вы не выбрали запись", "Ошибка" );
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
        Config.TITLES.setTitle( ADMIN_FORM_TITLE );
    }
}
