package masharun.filmLovers.view.forms;

import masharun.filmLovers.Main;
import masharun.filmLovers.models.DAO.ScoreDAO;
import masharun.filmLovers.models.entities.Score;
import masharun.filmLovers.models.tableModels.PersonalAreaTableModel;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonalAreaForm implements Form {
    private JPanel rootPanel;
    private JPanel HeaderPanel;
    private JPanel FooterPanel;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JLabel userInfoLabel;
    private JTable table;
    private JButton editButton;
    private JButton deleteButton;
    private JTextField searchField;
    private TableRowSorter<TableModel> rowSorter;
    
    public PersonalAreaForm() {
        init();
        initListeners();
    }
    
    private void init() {
        Main.setTitle( "Личный кабинет" );
        rowSorter = new TableRowSorter<>( table.getModel() );
        if ( Main.getCurrentUser().getName() == null && Main.getCurrentUser().getSurname() == null ) {
            userInfoLabel.setText( Main.getCurrentUser().getLogin() );
        } else {
            userInfoLabel.setText( new StringBuilder( Main.getCurrentUser().getName() )
                    .append( " " )
                    .append( Main.getCurrentUser().getSurname() )
                    .append( ", ")
                    .append( Main.getCurrentUser().getBirthday())
                    .toString()
            );
        }
        
        table.setModel( new PersonalAreaTableModel() );
    }
    
    private void initListeners() {
        deleteButton.addActionListener( eventListeners -> {
            try {
                ArrayList<Score> scoreList = new ScoreDAO().selectAll();
                ArrayList<Score> buffList = new ArrayList<>();
                for( Score score : scoreList ) {
                    if ( score.getLogin().equals( Main.getCurrentUser().getLogin() )) {
                        buffList.add( score );
                    }
                }
                //System.err.println( buffScore );
                //System.err.println( buffList.get( table.getSelectedRow() - 1 ).getFilmId() );
                //System.err.println( buffList.toString() );
                new ScoreDAO().deleteById(
                        buffList.get( table.getSelectedRow() ).getFilmId(), Main.getCurrentUser().getLogin() );
                table.updateUI();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления пользователя", "Ошибка" );
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
        
        backButton.addActionListener( eventListeners ->
            Main.getMainForm().setLastForm()
        );
    
        editButton.addActionListener( eventListener -> {
            Main.getMainForm().pushStackFrame( this );
            Main.getMainForm().setNewForm( new UpdateUserForm() );
        } );
        
        exitButton.addActionListener( eventListeners -> {
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
        Main.setTitle( "Добавление фильма" );
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
