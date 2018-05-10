package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.FilmDAO;
import me.mashyrin.filmLovers.model.entities.Film;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;

import java.sql.SQLException;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.ADD_REVIEW_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

/**
 * Adding review activity class
 *
 * @author mashyrin
 */
public class AddReviewActivity implements Activity {
    private JPanel rootPanel;
    private JTextArea textArea;
    private JButton addButton;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JLabel filmLabel;
    private JPanel focusPanel;
    private Film film;
    
    /**
     * Adding review activity constructor
     *
     * @param film
     */
    public AddReviewActivity( Film film ) {
        System.out.println();
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ADD_REVIEW_ACTIVITY_TITLE );
        this.film = film;
        filmLabel.setText( film.getName() );
        initListeners();
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        addButton.addActionListener( eventListener -> {
            try {
                new FilmDAO().addReview( film.getFilmId(), textArea.getText() );
                OptionPane.showMessage( "Вы успешно добавили рецензию", CONGRATULATION_TITLE );
                Main.getMainActivity().setLastForm();
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка добавления рецензии", ERROR_TITLE );
                System.err.println( e.toString() );
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( ADD_REVIEW_ACTIVITY_TITLE );
    }
}
