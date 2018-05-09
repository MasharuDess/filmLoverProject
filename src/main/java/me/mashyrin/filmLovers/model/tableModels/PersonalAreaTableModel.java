package me.mashyrin.filmLovers.model.tableModels;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.DAO.FilmDAO;
import me.mashyrin.filmLovers.model.DAO.GenreDAO;
import me.mashyrin.filmLovers.model.DAO.ScoreDAO;
import me.mashyrin.filmLovers.model.entities.Score;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;

public class PersonalAreaTableModel implements TableModel {
    
    private ScoreDAO scoreDAO = new ScoreDAO();
    private Set<TableModelListener> listeners = new HashSet<>();
    
    @Override
    public int getRowCount() {
        try {
            ArrayList<Score> scoreList = scoreDAO.selectAll();
            ArrayList<Score> buffList = new ArrayList<>();
            for( Score score : scoreList ) {
                if ( score.getLogin().equals( Main.getCurrentUser().getLogin())) {
                    buffList.add( score );
                }
            }
            return buffList.size();
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", ERROR_TITLE );
            System.err.println( e.toString() );
            return 0;
        }
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }
    
    @Override
    public String getColumnName( int columnIndex ) {
        switch( columnIndex ) {
            case 0:
                return "Название фильма";
            case 1:
                return "Жанр";
            case 2:
                return "Ваша оценка";
        }
    
        return null;
    }
    
    @Override
    public Class<?> getColumnClass( int columnIndex ) {
        if ( columnIndex == 0 || columnIndex == 1 ) {
            return String.class;
        } else if ( columnIndex == 2 ) {
            return Double.class;
        }
        return null;
    }
    
    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex ) {
        return false;
    }
    
    @Override
    public Object getValueAt( int rowIndex, int columnIndex ) {
        try {
            ArrayList<Score> scoreList = scoreDAO.selectAll();
            ArrayList<Score> buffList = new ArrayList<>();
            for( Score score : scoreList ) {
                if ( score.getLogin().equals( Main.getCurrentUser().getLogin())) {
                    buffList.add( score );
                }
            }
            Score score = buffList.get( rowIndex );
        
            switch ( columnIndex ) {
                case 0:
                    return ( new FilmDAO().selectById( score.getFilmId() ).getName());
                case 1:
                    return ( new GenreDAO().selectById( new FilmDAO().selectById(
                            score.getFilmId() ).getGenreId()).getGenre());
                case 2:
                    return score.getScore();
            }
        } catch ( SQLException | NullPointerException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", ERROR_TITLE );
        }
        return null;
    }
    
    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
        //Nothing
    }
    
    @Override
    public void addTableModelListener( TableModelListener l ) {
        listeners.add( l );
    }
    
    @Override
    public void removeTableModelListener( TableModelListener l ) {
        listeners.remove( l );
    }
}
