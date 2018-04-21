package masharun.filmLovers.view.forms;

import masharun.filmLovers.Main;
import masharun.filmLovers.models.DAO.CountryDAO;
import masharun.filmLovers.models.DAO.FilmDAO;
import masharun.filmLovers.models.DAO.GenreDAO;
import masharun.filmLovers.models.entities.Country;
import masharun.filmLovers.models.entities.Film;
import masharun.filmLovers.models.entities.Genre;
import masharun.filmLovers.view.OptionPane;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FilmEditForm implements Form {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton editButton;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JLabel nameLabel;
    private JTextField nameField;
    private JTextField budgetField;
    private JComboBox countryComboBox;
    private JComboBox genreComboBox;
    private JTextField releaseDateField;
    private JTextField commentField;
    private JLabel budgetLabel;
    private JLabel countryLabel;
    private JLabel genreLabel;
    private JLabel releaseDateLabel;
    private JLabel commentLabel;
    private JLabel dateFormatLabel;
    private Film film;
    
    public FilmEditForm() {
        Main.setTitle( "Добавление фильма" );
        editButton.setText( "Добавить" );
        initBoxes();
        initAddListeners();
    }
    
    public FilmEditForm( Film film ) {
        Main.setTitle( "Настройка фильма" );
        this.film = film;
        init();
        initEditListeners();
    }
    
    private void init() {
        nameField.setText( film.getName());
        if ( film.getBudget() != null ) {
            budgetField.setText( film.getBudget().toString() );
        }
        if ( film.getReleaseDate() != null ) {
            releaseDateField.setText( film.getReleaseDate().toString() );
        }
        if ( film.getComment() != null ) {
            commentField.setText( film.getComment() );
        }
        initBoxes();
    }
    
    private void initBoxes() {
        try {
            ArrayList<Country> countryList = new CountryDAO().selectAll();
            for ( Country country : countryList ) {
                countryComboBox.addItem( country.getName() );
            }
            countryComboBox.setSelectedIndex( film.getCountryId());
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки списка стран", "Ошибка" );
            System.err.println( e.toString() );
        }
        
        try {
            ArrayList<Genre> countryList = new GenreDAO().selectAll();
            for ( Genre genre : countryList ) {
                genreComboBox.addItem( genre.getGenre() );
            }
            genreComboBox.setSelectedIndex( film.getGenreId());
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки списка жанров", "Ошибка" );
            System.err.println( e.toString() );
        }
    }
    
    private void initAddListeners() {
        footerListeners();
        editButton.addActionListener( eventListeners -> {
            if ( !nameField.getText().equals( "" ) ) {
                film = new Film();
                film.setName( nameField.getText() );
                film.setGenreId( genreComboBox.getSelectedIndex() );
                film.setCountryId( countryComboBox.getSelectedIndex() );
                try {
                    if( !budgetField.getText().equals( "" ) ) {
                        film.setBudget( Integer.parseInt( budgetField.getText() ) );
                    } else {
                        film.setBudget( 1 );
                    }
                } catch( NumberFormatException e ) {
                    OptionPane.showMessage( "Неверный формат числа", "Ошибка" );
                    System.err.println( e.toString() );
                }
                DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
                try {
                    if( !releaseDateField.getText().equals( "" ) ) {
                        film.setReleaseDate( new Date( ( dateFormat.parse( releaseDateField.getText() ) ).getTime() ) );
                    } else {
                        film.setReleaseDate( null );
                    }
                } catch( ParseException e ) {
                    OptionPane.showMessage( "Неверный формат даты", "Ошибка" );
                    System.err.println( e.toString() );
                }
                film.setComment( commentField.getText() );
                FilmDAO filmDAO = new FilmDAO();
                try {
                    film.setFilmId( filmDAO.insert( film ).getFilmId() );
                    filmDAO.update( film );
                    OptionPane.showMessage( "Вы успешно добавили фильм", "Поздравляем" );
                    Main.getMainForm().setLastForm();
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка добавления фильма", "Ошибка" );
                    System.err.println( e.toString() );
                }
                Main.getMainForm().clearStackFrame();
                Main.getMainForm().setNewForm( new FilmsForm() );
            } else {
                OptionPane.showMessage( "Вы не ввели название фильма", "Ошибка" );
            }
        } );
    }
    
    private void initEditListeners() {
        footerListeners();
        editButton.addActionListener( eventListener -> {
            film.setName( nameField.getText() );
            film.setGenreId( genreComboBox.getSelectedIndex() );
            film.setCountryId( countryComboBox.getSelectedIndex() );
            try {
                film.setBudget( Integer.parseInt( budgetField.getText() ) );
            } catch ( NumberFormatException e ) {
                OptionPane.showMessage( "Неверный формат числа", "Ошибка" );
                System.err.println( e.toString() );
            }
            DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
            try {
                film.setReleaseDate( new Date(( dateFormat.parse( releaseDateField.getText())).getTime()));
            } catch ( ParseException e ) {
                OptionPane.showMessage( "Неверный формат даты", "Ошибка" );
                System.err.println( e.toString() );
            }
            film.setComment( commentField.getText());
            try {
                new FilmDAO().update( film );
                OptionPane.showMessage( "Вы успешно изменили запись", "Поздравляем" );
            } catch ( SQLException e ) {
                OptionPane.showMessage( "Ошибка изменения данных о фильме", "Ошибка" );
                System.err.println( e.toString() );
            }
        } );
    }
    
    private void footerListeners() {
        exitButton.addActionListener( eventListener -> {
            Main.setCurrentUser( null );
            Main.getMainForm().clearStackFrame();
            Main.getMainForm().setNewForm( new MainForm() );
        } );
    
        backButton.addActionListener( eventListener ->
                Main.getMainForm().setLastForm()
        );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Main.setTitle( "Добавление фильма" );
    }
}
