package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.FilmDAO;
import me.mashyrin.filmLovers.model.entities.Film;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;

import java.sql.SQLException;
import java.util.Map;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.ERRORS.NO_REVIEW_ERROR;
import static me.mashyrin.filmLovers.view.Config.ERRORS.SORRY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.REVIEW_ACTIVITY_TITLE;

public class ReviewActivity implements Activity {
    private JPanel rootPanel;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JButton exitButton;
    private JButton backButton;
    private JLabel mainTitle;
    private JTextArea textArea;
    private JComboBox comboBox;
    private JButton addReviewButton;
    private JButton deleteReviewButton;
    private JLabel reviewLabel;
    private Film film;
    private Map reviewMap;
    
    public ReviewActivity( Film film ) {
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( REVIEW_ACTIVITY_TITLE );
        this.film = film;
        init();
        initListeners();
    }
    
    private void init() {
        try {
            reviewMap = new FilmDAO().selectFilmReview( film.getFilmId() );
            java.util.Iterator iterator = reviewMap.entrySet().iterator();
            while( iterator.hasNext() ) {
                Map.Entry pair = ( Map.Entry ) iterator.next();
                comboBox.addItem( pair.getKey() );
            }
            comboBox.setSelectedIndex( 0 );
            textArea.setText( reviewMap.get( comboBox.getItemAt( 0 ) ).toString() );
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки рецензий", ERROR_TITLE );
            System.err.println( e.toString() );
        } catch( IllegalArgumentException e ) {
            comboBox.setVisible( false );
            comboBox.setEnabled( false );
            textArea.setText( NO_REVIEW_ERROR );
        }
        if( Main.getCurrentUser().getRole().equals( "C" ) ) {
            addReviewButton.setEnabled( true );
            addReviewButton.setVisible( true );
        }
        if( Main.getCurrentUser().getLogin().equals( comboBox.getItemAt( comboBox.getSelectedIndex() ) ) ) {
            deleteReviewButton.setEnabled( true );
            deleteReviewButton.setVisible( true );
            reviewLabel.setVisible( true );
        }
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        comboBox.addActionListener( eventListener -> {
            textArea.setText( reviewMap.get( comboBox.getItemAt( comboBox.getSelectedIndex() ) ).toString() );
            if( Main.getCurrentUser().getLogin().equals( comboBox.getItemAt( comboBox.getSelectedIndex() ) ) ) {
                deleteReviewButton.setEnabled( true );
                deleteReviewButton.setVisible( true );
                reviewLabel.setVisible( true );
            } else {
                deleteReviewButton.setEnabled( false );
                deleteReviewButton.setVisible( false );
                reviewLabel.setVisible( false );
            }
        } );
        
        addReviewButton.addActionListener( eventListener -> {
            if( reviewMap.get( Main.getCurrentUser().getLogin() ) == null ) {
                Main.getMainActivity().pushStackFrame( this );
                Main.getMainActivity().setNewForm( new AddReviewActivity( film ) );
            } else {
                OptionPane.showMessage( "Вы уже добавили рецензию к этому фильму", SORRY_TITLE );
            }
        } );
        
        deleteReviewButton.addActionListener( eventListener -> {
            try {
                reviewMap.remove( Main.getCurrentUser().getLogin() );
                if( comboBox.getItemCount() == 1 ) {
                    new FilmDAO().deleteReview( film.getFilmId() );
                    OptionPane.showMessage( "Вы успешно удалили рецензию", CONGRATULATION_TITLE );
                    Main.getMainActivity().setLastForm();
                    return;
                } else if( comboBox.getSelectedIndex() == 0 ) {
                    comboBox.setSelectedIndex( comboBox.getSelectedIndex() + 1 );
                    comboBox.removeItemAt( comboBox.getSelectedIndex() - 1 );
                } else {
                    comboBox.setSelectedIndex( comboBox.getSelectedIndex() - 1 );
                    comboBox.removeItemAt( comboBox.getSelectedIndex() + 1 );
                }
                textArea.setText( reviewMap.get( comboBox.getItemAt( comboBox.getSelectedIndex() ) ).toString() );
                new FilmDAO().deleteReview( film.getFilmId() );
                OptionPane.showMessage( "Вы успешно удалили рецензию", CONGRATULATION_TITLE );
            } catch( SQLException e ) {
                OptionPane.showMessage( "Ошибка удаления рецензии", ERROR_TITLE );
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
        Config.TITLES.setTitle( REVIEW_ACTIVITY_TITLE );
    }
}
