package me.mashyrin.filmLovers.view.activities;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.FilmworkerDAO;
import me.mashyrin.filmLovers.model.DAO.FilmworkersRoleDAO;
import me.mashyrin.filmLovers.model.DAO.RoleDAO;
import me.mashyrin.filmLovers.model.entities.Film;
import me.mashyrin.filmLovers.model.entities.Filmworker;
import me.mashyrin.filmLovers.model.entities.Role;
import me.mashyrin.filmLovers.view.Config;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.*;

import java.sql.SQLException;
import java.util.ArrayList;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.ACTORS_ADD_ACTIVITY_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.CONGRATULATION_TITLE;
import static me.mashyrin.filmLovers.view.Config.TITLES.MAIN_TITLE;

public class ActorsRoleAddActivity implements Activity {
    private JPanel rootPanel;
    private JButton addButton;
    private JButton exitButton;
    private JButton backButton;
    private JPanel headerPanel;
    private JPanel footerPanel;
    private JLabel mainTitle;
    private JComboBox filmworkerBox;
    private JComboBox roleBox;
    private JLabel filmworkerLabel;
    private JLabel roleLabel;
    private JLabel filmLabel;
    private Film film;
    private ArrayList<Filmworker> filmworkerList;
    
    public ActorsRoleAddActivity( Film film ) {
        System.out.println();
        mainTitle.setText( MAIN_TITLE );
        Config.TITLES.setTitle( ACTORS_ADD_ACTIVITY_TITLE );
        this.film = film;
        filmLabel.setText( film.getName() );
        initBoxes();
        initListeners();
    }
    
    private void initBoxes() {
        try {
            ArrayList<Role> roleList = new RoleDAO().selectAll();
            for( Role role : roleList ) {
                roleBox.addItem( role.getRoleName() );
            }
            roleBox.setSelectedIndex( -1 );
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки списка ролей", ERROR_TITLE );
            System.err.println( e.toString() );
        }
        
        try {
            filmworkerList = new FilmworkerDAO().selectAll();
            for( Filmworker filmworker : filmworkerList ) {
                filmworkerBox.addItem( filmworker.getName() + " " + filmworker.getSurname()
                        + ", " + filmworker.getBirthday() );
            }
            filmworkerBox.setSelectedIndex( -1 );
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки списка актеров", ERROR_TITLE );
            System.err.println( e.toString() );
        }
    }
    
    private void initListeners() {
        backButton.addActionListener( eventListeners -> Main.getMainActivity().setLastForm() );
        
        exitButton.addActionListener( eventListeners -> {
            Main.setCurrentUser( null );
            Main.getMainActivity().clearStackFrame();
            Main.getMainActivity().setNewForm( new MainActivity() );
        } );
        
        addButton.addActionListener( eventListener -> {
            if( roleBox.getSelectedIndex() == -1 ) {
                OptionPane.showMessage( "Вы не выбрали роль", ERROR_TITLE );
            } else if( filmworkerBox.getSelectedIndex() == -1 ) {
                OptionPane.showMessage( "Вы не выбрали актера", ERROR_TITLE );
            } else {
                try {
                    new FilmworkersRoleDAO().insert( roleBox.getSelectedIndex(),
                            filmworkerList.get( filmworkerBox.getSelectedIndex() ).getFilmworkerId(),
                            film.getFilmId() );
                    OptionPane.showMessage( "Вы успешно добавили актера фильма", CONGRATULATION_TITLE );
                    Main.getMainActivity().setLastForm();
                } catch( SQLException e ) {
                    OptionPane.showMessage( "Ошибка добавления", ERROR_TITLE );
                    System.err.println( e.toString() );
                }
            }
        } );
    }
    
    @Override
    public JPanel getRootPanel() {
        return rootPanel;
    }
    
    @Override
    public void reinit() {
        Config.TITLES.setTitle( ACTORS_ADD_ACTIVITY_TITLE );
    }
}
