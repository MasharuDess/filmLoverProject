package masharun.filmLovers.view.forms;

import masharun.filmLovers.Main;
import masharun.filmLovers.models.DAO.CountryDAO;
import masharun.filmLovers.models.DAO.FilmDAO;
import masharun.filmLovers.models.DAO.GenreDAO;
import masharun.filmLovers.models.DAO.ScoreDAO;
import masharun.filmLovers.models.entities.Film;
import masharun.filmLovers.models.entities.Score;
import masharun.filmLovers.view.OptionPane;
import org.postgresql.core.SqlCommand;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilmsForm implements Form {
    private JPanel rootPanel;
    private JPanel HeaderPanel;
    private JPanel FooterPanel;
    private JButton exitButton;
    private JLabel MainTitle;
    private JButton personalAreaButton;
    private JComboBox filmComboBox;
    private JTextPane textPane;
    private JLabel filmNameLabel;
    private JLabel userScore;
    private JLabel criticScore;
    private JLabel criticScoreLabel;
    private JLabel userScoreLabel;
    private JLabel countryLabel;
    private JLabel budget;
    private JLabel genre;
    private JLabel budgetLabel;
    private JButton editFilmButton;
    private JLabel releaseDateLabel;
    private JLabel releaseDate;
    private JButton deleteFilmButton;
    private JButton addFilmButton;
    private JButton setScoreButton;
    private JSlider scoreSlider;
    private JTextField textField1;
    private JButton actorButton;
    private JPanel CardPanel;
    private JScrollPane scrollPane;
    private ArrayList<Film> filmList;
    
    public FilmsForm()
    {
        Main.setTitle( "Фильмы" );
        init();
        initListeners();
    }
    
    private void init() {
        try {
            filmList = new FilmDAO().selectAll();
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки данных о фильмах", "Ошибка" );
            System.err.println( e.toString() );
        }
        for( Film film : filmList ) {
            filmComboBox.addItem( film.getName() );
        }
        fillNote( 0 );
        
        if ( Main.getCurrentUser().getRole().equals( "S" ) ) {
            editFilmButton.setVisible( true );
            editFilmButton.setEnabled( true );
            deleteFilmButton.setEnabled( true );
            deleteFilmButton.setVisible( true );
            addFilmButton.setVisible( true );
            addFilmButton.setEnabled( true );
        }
    }
    
    private void fillNote( int index ) {
        try {
            if( new ScoreDAO().selectById( filmList.get( index ).getFilmId(),
                    Main.getCurrentUser().getLogin()).getScore() == null ) {
                scoreSlider.setVisible( true );
                scoreSlider.setEnabled( true );
                setScoreButton.setVisible( true );
                setScoreButton.setEnabled( true );
            } else {
                scoreSlider.setVisible( false );
                scoreSlider.setEnabled( false );
                setScoreButton.setVisible( false );
                setScoreButton.setEnabled( false );
            }
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки оценки", "Ошибка" );
            System.err.println( e.toString() );
        }
        filmNameLabel.setText( filmList.get( index ).getName() );
        if ( filmList.get( index ).getComment() != null ) {
            textPane.setText( filmList.get( index ).getComment() );
        }
        if ( filmList.get( index ).getScore() != null ) {
            userScore.setText( String.format("%(.1f", filmList.get( index ).getScore()));
        }
        if ( filmList.get( index ).getCriticScore() != null ) {
            criticScore.setText( String.format("%(.1f", filmList.get( index ).getCriticScore()));
        }
        if ( filmList.get( index ).getBudget() != null ) {
            budget.setText( filmList.get( index ).getBudget().toString() );
        }
        if ( filmList.get( index ).getReleaseDate() != null ) {
            releaseDate.setText( filmList.get( index ).getReleaseDate().toString() );
        }
        if ( filmList.get( index ).getCountryId() != null ) {
            try {
                countryLabel.setText( new CountryDAO().selectById( filmList.get( index ).getCountryId() ).getName() );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка загрузки данных о стране", "Ошибка" );
                System.err.println( e.toString() );
            }
        }
        if ( filmList.get( index ).getGenreId() != null ) {
            try {
                genre.setText( new GenreDAO().selectById( filmList.get( index ).getGenreId() ).getGenre() );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка загрузки данных о жанреу", "Ошибка" );
                System.err.println( e.toString() );
            }
        }
    }
    
    private void initListeners() {
        
        setScoreButton.addActionListener( eventListener -> {
            Score score = new Score();
            score.setLogin( Main.getCurrentUser().getLogin() );
            score.setFilmId( filmList.get( filmComboBox.getSelectedIndex() ).getFilmId() );
            score.setRole( Main.getCurrentUser().getRole() );
            score.setScore(( double ) scoreSlider.getValue() );
            try {
                new ScoreDAO().insert( score );
            } catch ( SQLException e ) {
                OptionPane.showMessage( "Ошибка добавления оценки", "Ошибка" );
                System.err.println( e.toString() );
            }
            
            scoreSlider.setVisible( false );
            scoreSlider.setEnabled( false );
            setScoreButton.setVisible( false );
            setScoreButton.setEnabled( false );
            
            if ( Main.getCurrentUser().getRole().equals( "C" ) ) {
                try {
                    criticScore.setText( new FilmDAO().selectById( filmList.get( filmComboBox
                            .getSelectedIndex() ).getFilmId() ).getCriticScore().toString() );
                } catch ( SQLException e ) {
                    OptionPane.showMessage( "Ошибка загрузки оценки", "Ошибка" );
                    System.err.println( e.toString() );
                }
            } else {
                try {
                    userScore.setText( new FilmDAO().selectById( filmList.get( filmComboBox
                            .getSelectedIndex() ).getFilmId() ).getScore().toString() );
                } catch ( SQLException e ) {
                    OptionPane.showMessage( "Ошибка загрузки оценки", "Ошибка" );
                    System.err.println( e.toString() );
                }
            }
        } );
        
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainForm().clearStackFrame();
            Main.getMainForm().setNewForm( new MainForm() );
        } );
        
        filmComboBox.addActionListener( eventListener ->
            fillNote( filmComboBox.getSelectedIndex() )
        );
        
        personalAreaButton.addActionListener( eventListener -> {
            Main.getMainForm().pushStackFrame( this );
            Main.getMainForm().setNewForm( new PersonalAreaForm() );
        } );
        
        deleteFilmButton.addActionListener( eventListener -> {
            try {
                new FilmDAO().deleteById( filmList.get( filmComboBox.getSelectedIndex() ).getFilmId() );
                filmList.remove( filmComboBox.getSelectedIndex() );
                filmComboBox.setSelectedIndex( filmComboBox.getSelectedIndex() - 1 );
                filmComboBox.removeItemAt( filmComboBox.getSelectedIndex() + 1 );
                fillNote( filmComboBox.getSelectedIndex() );
                filmComboBox.updateUI();
            } catch ( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления фильма", "Ошибка" );
                System.err.println( e.toString() );
            }
        } );
        
        editFilmButton.addActionListener( eventListener -> {
            Main.getMainForm().pushStackFrame( this );
            Main.getMainForm().setNewForm( new FilmEditForm( filmList.get( filmComboBox.getSelectedIndex() )));
        } );
        
        addFilmButton.addActionListener( eventListener -> {
            Main.getMainForm().pushStackFrame( this );
            Main.getMainForm().setNewForm( new FilmEditForm() );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle( "Фильма" );
        filmComboBox.updateUI();
        fillNote( filmComboBox.getSelectedIndex() );
    }
}
