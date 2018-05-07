package masharun.filmLovers.view.activities;

import masharun.filmLovers.Main;
import masharun.filmLovers.model.DAO.FilmworkerDAO;
import masharun.filmLovers.model.DAO.FilmworkersRoleDAO;
import masharun.filmLovers.model.entities.Film;
import masharun.filmLovers.model.entities.Filmworker;
import masharun.filmLovers.model.entities.FilmworkersRole;
import masharun.filmLovers.model.tableModels.ActorsTableModel;
import masharun.filmLovers.model.tableModels.AllActorsTableModel;
import masharun.filmLovers.view.Config;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.sql.SQLException;
import java.util.ArrayList;

import static masharun.filmLovers.view.Config.TITLES.ACTORS_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.ALL_ACTORS_FORM_TITLE;
import static masharun.filmLovers.view.Config.TITLES.MAIN_TITLE;

public class ActorsActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JLabel mainTitle;
    private JButton backButton;
    private JButton exitButton;
    private JButton addActorButton;
    private JButton editActorButton;
    private JButton deleteActorButton;
    private JTextField searchField;
    private JLabel filmLabel;
    private JTable table;
    private JPanel footerPanel;
    private JButton openAllActorsButton;
    private Film film;
    private TableRowSorter<TableModel> rowSorter;
    private ArrayList<Filmworker> filmworkersList;
    private ArrayList<FilmworkersRole> filmworkersRolesList;
    private AllActorsTableModel allActorsTableModel;
    private ActorsTableModel actorsTableModel;
    
    public ActorsActivity() {
        openAllActorsButton.setVisible( false );
        openAllActorsButton.setEnabled( false );
        filmLabel.setVisible( false );
        Config.TITLES.setTitle( ALL_ACTORS_FORM_TITLE );
        try {
            filmworkersList = new FilmworkerDAO().selectAll();
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки актеров", "Ошибка" );
            System.err.println( e.toString() );
        }
        allActorsTableModel = new AllActorsTableModel();
        init( allActorsTableModel );
    }
    
    public ActorsActivity( Film film ) {
        this.film = film;
        editActorButton.setVisible( false );
        editActorButton.setEnabled( false );
        filmLabel.setText( film.getName() );
        Config.TITLES.setTitle( ACTORS_FORM_TITLE );
        try {
            filmworkersRolesList = new FilmworkersRoleDAO().selectAll();
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки актеров", "Ошибка" );
            System.err.println( e.toString() );
        }
        actorsTableModel = new ActorsTableModel( film );
        init( actorsTableModel );
    }
    
    private void init( TableModel tableModel ) {
        if( !Main.getCurrentUser().getRole().equals( "S" ) ) {
            addActorButton.setEnabled( false );
            addActorButton.setVisible( false );
            deleteActorButton.setEnabled( false );
            deleteActorButton.setVisible( false );
            editActorButton.setEnabled( false );
            editActorButton.setVisible( false );
        }
        mainTitle.setText( MAIN_TITLE );
        table.setModel( tableModel );
        rowSorter = new TableRowSorter<>( table.getModel() );
        table.setRowSorter( rowSorter );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
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
        
        addActorButton.addActionListener( eventListener -> {
            if( film == null ) {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm( new ActorsEditActivity() );
            } else {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm( new ActorsRoleAddActivity( film ) );
            }
        } );
        
        deleteActorButton.addActionListener( eventListener -> {
            try {
                if( film == null ) {
                    new FilmworkerDAO().deleteById(
                            allActorsTableModel.getValueAt( table.getSelectedRow() ).getFilmworkerId() );
                } else {
                    FilmworkersRole filmworkersRole = actorsTableModel.getValueAt( table.getSelectedRow() );
                    new FilmworkersRoleDAO().deleteById(
                            filmworkersRole.getRoleId(),
                            filmworkersRole.getFilmworkerId(),
                            filmworkersRole.getFilmId() );
                }
                table.updateUI();
            } catch( ArrayIndexOutOfBoundsException e ) {
                OptionPane.showMessage( "Вы не выбрали запись", "Ошибка" );
                System.err.println( e.toString() );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления записи", "Ошибка" );
                System.err.println( e.toString() );
            }
        } );
        
        editActorButton.addActionListener( eventListener -> {
            try {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm(
                        new ActorsEditActivity( allActorsTableModel.getValueAt( table.getSelectedRow() ) ) );
            } catch( ArrayIndexOutOfBoundsException e ) {
                OptionPane.showMessage( "Вы не выбрали запись", "Ошибка" );
                System.err.println( e.toString() );
            }
        } );
        
        openAllActorsButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm( new ActorsActivity() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        if( film == null ) {
            Config.TITLES.setTitle( ALL_ACTORS_FORM_TITLE );
        } else {
            Config.TITLES.setTitle( ACTORS_FORM_TITLE );
        }
        table.updateUI();
    }
}
