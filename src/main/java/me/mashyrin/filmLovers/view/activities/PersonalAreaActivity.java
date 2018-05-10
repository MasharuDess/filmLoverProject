package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.ScoreDAO;
import me.mashyrin.filmLovers.model.entities.Score;
import me.mashyrin.filmLovers.model.tableModels.PersonalAreaTableModel;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.PERSONAL_AREA_ACTIVITY_TITLE;

/**
 * Personal area activity class
 *
 * @author mashyrin
 */
public class PersonalAreaActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JLabel userInfoLabel;
    private JTable table;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;
    private JButton scheduleButton;
    private TableRowSorter<TableModel> rowSorter;
    
    /**
     * Personal area activity constructor
     */
    public PersonalAreaActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( PERSONAL_AREA_ACTIVITY_TITLE );
        init();
        initListeners();
    }
    
    private void init() {
        if( Main.getCurrentUser().getName() == null && Main.getCurrentUser().getSurname() == null ) {
            userInfoLabel.setText( Main.getCurrentUser().getLogin() );
        } else {
            userInfoLabel.setText( new StringBuilder(
                    Main.getCurrentUser().getName() ).append( " " ).append(
                    Main.getCurrentUser().getSurname() ).append( ", " ).append(
                    Main.getCurrentUser().getBirthday() ).toString() );
        }
        table.setModel( new PersonalAreaTableModel() );
        rowSorter = new TableRowSorter<>( table.getModel() );
        table.setRowSorter( rowSorter );
    }
    
    private void initListeners() {
        deleteButton.addActionListener( eventListeners -> {
            try {
                ArrayList<Score> scoreList = new ScoreDAO().selectAll();
                ArrayList<Score> buffList = new ArrayList<>();
                for( Score score : scoreList ) {
                    if( score.getLogin().equals( Main.getCurrentUser().getLogin() ) ) {
                        buffList.add( score );
                    }
                }
                new ScoreDAO().deleteById( buffList.get( table.getSelectedRow() ).getFilmId(),
                        Main.getCurrentUser().getLogin() );
                table.updateUI();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления оценки", ERROR_TITLE );
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
        
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        editButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm( new UpdateUserActivity() );
        } );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        scheduleButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm( new GraphicActivity() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( PERSONAL_AREA_ACTIVITY_TITLE );
        if( Main.getCurrentUser().getName() == null && Main.getCurrentUser().getSurname() == null ) {
            userInfoLabel.setText( Main.getCurrentUser().getLogin() );
        } else {
            userInfoLabel.setText( new StringBuilder(
                    Main.getCurrentUser().getName() ).append( " " ).append(
                    Main.getCurrentUser().getSurname() ).append( ", " ).append(
                    Main.getCurrentUser().getBirthday() ).toString() );
        }
    }
}
