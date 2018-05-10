package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.CountryDAO;
import me.mashyrin.filmLovers.model.DAO.FilmDAO;
import me.mashyrin.filmLovers.model.DAO.GenreDAO;
import me.mashyrin.filmLovers.model.DAO.ScoreDAO;
import me.mashyrin.filmLovers.model.entities.Film;
import me.mashyrin.filmLovers.model.entities.Score;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.FILMS_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

/**
 * Films activity class
 *
 * @author mashyrin
 */
public class FilmsActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JLabel mainTitle;
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
    private JTextField searchField;
    private JButton actorButton;
    private JPanel cardPanel;
    private JButton reviewButton;
    private JScrollPane scrollPane;
    private ArrayList<Film> filmList;
    
    /**
     * Films activity constructor
     */
    public FilmsActivity() {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( FILMS_ACTIVITY_TITLE );
        init();
        initListeners();
    }
    
    private void init() {
        try {
            filmList = new FilmDAO().selectAll();
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки данных о фильмах", ERROR_TITLE );
            System.err.println( e.toString() );
        }
        for( Film film : filmList ) {
            filmComboBox.addItem( film.getName() );
        }
        fillNote( 0 );
        
        if( Main.getCurrentUser().getRole().equals( "S" ) ) {
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
                    Main.getCurrentUser().getLogin() ).getScore() == null ) {
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
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки оценки", ERROR_TITLE );
            System.err.println( e.toString() );
        }
        filmNameLabel.setText( filmList.get( index ).getName() );
        if( filmList.get( index ).getComment() != null ) {
            textPane.setText( filmList.get( index ).getComment() );
        }
        if( filmList.get( index ).getScore() != null ) {
            userScore.setText( String.format( "%(.1f", filmList.get( index ).getScore() ) );
        }
        if( filmList.get( index ).getCriticScore() != null ) {
            criticScore.setText( String.format( "%(.1f", filmList.get( index ).getCriticScore() ) );
        }
        if( filmList.get( index ).getBudget() != null ) {
            budget.setText( filmList.get( index ).getBudget().toString() );
        }
        if( filmList.get( index ).getReleaseDate() != null ) {
            releaseDate.setText( filmList.get( index ).getReleaseDate().toString() );
        }
        if( filmList.get( index ).getCountryId() != null ) {
            try {
                countryLabel.setText( new CountryDAO().selectById(
                        filmList.get( index ).getCountryId() ).getName() );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка загрузки данных о стране", ERROR_TITLE );
                System.err.println( e.toString() );
            }
        }
        if( filmList.get( index ).getGenreId() != null ) {
            try {
                genre.setText( new GenreDAO().selectById( filmList.get( index ).getGenreId() ).getGenre() );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка загрузки данных о жанре", ERROR_TITLE );
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
            score.setScore( ( double ) scoreSlider.getValue() );
            try {
                new ScoreDAO().insert( score );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка добавления оценки", ERROR_TITLE );
                System.err.println( e.toString() );
            }
            
            scoreSlider.setVisible( false );
            scoreSlider.setEnabled( false );
            setScoreButton.setVisible( false );
            setScoreButton.setEnabled( false );
            
            if( Main.getCurrentUser().getRole().equals( "C" ) ) {
                try {
                    criticScore.setText( String.format( "%(.1f", new FilmDAO().selectById( filmList.get(
                            filmComboBox.getSelectedIndex() ).getFilmId() ).getCriticScore() ) );
                    filmList.get( filmComboBox.getSelectedIndex() ).setCriticScore(
                            ( double ) scoreSlider.getValue() );
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка загрузки оценки", ERROR_TITLE );
                    System.err.println( e.toString() );
                }
            } else {
                try {
                    userScore.setText( String.format( "%(.1f", new FilmDAO().selectById( filmList.get(
                            filmComboBox.getSelectedIndex() ).getFilmId() ).getScore() ) );
                    filmList.get( filmComboBox.getSelectedIndex() ).setScore(
                            ( double ) scoreSlider.getValue() );
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка загрузки оценки", ERROR_TITLE );
                    System.err.println( e.toString() );
                }
            }
        } );
        
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        filmComboBox.addActionListener( eventListener -> fillNote( filmComboBox.getSelectedIndex() ) );
        
        personalAreaButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm( new PersonalAreaActivity() );
        } );
        
        deleteFilmButton.addActionListener( eventListener -> {
            try {
                new FilmDAO().deleteById( filmList.get( filmComboBox.getSelectedIndex() ).getFilmId() );
                filmList.remove( filmComboBox.getSelectedIndex() );
                if( filmComboBox.getSelectedIndex() == 0 ) {
                    filmComboBox.setSelectedIndex( filmComboBox.getSelectedIndex() + 1 );
                    filmComboBox.removeItemAt( filmComboBox.getSelectedIndex() - 1 );
                } else {
                    filmComboBox.setSelectedIndex( filmComboBox.getSelectedIndex() - 1 );
                    filmComboBox.removeItemAt( filmComboBox.getSelectedIndex() + 1 );
                }
                fillNote( filmComboBox.getSelectedIndex() );
                filmComboBox.updateUI();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления фильма", ERROR_TITLE );
                System.err.println( e.toString() );
            }
        } );
        
        reviewButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm(
                    new ReviewActivity( filmList.get( filmComboBox.getSelectedIndex() ) ) );
        } );
        
        editFilmButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm(
                    new FilmEditActivity( filmList.get( filmComboBox.getSelectedIndex() ) ) );
        } );
        
        addFilmButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm( new FilmEditActivity() );
        } );
        
        actorButton.addActionListener( eventListener -> {
            Main.getMainActivity().pushStackFrame( this );
            Main.getMainActivity().setNewForm(
                    new ActorsActivity( filmList.get( filmComboBox.getSelectedIndex() ) ) );
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( FILMS_ACTIVITY_TITLE );
        if( filmComboBox.getItemCount() < filmList.size() ) {
            filmComboBox.addItem( filmList.get( filmList.size() ).getName() );
        }
        filmComboBox.updateUI();
        fillNote( filmComboBox.getSelectedIndex() );
    }
}
