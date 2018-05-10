package me.mashyrin.filmLovers.model.tableModels;

import me.mashyrin.filmLovers.model.DAO.CountryDAO;
import me.mashyrin.filmLovers.model.DAO.FilmworkerDAO;
import me.mashyrin.filmLovers.model.DAO.FilmworkersRoleDAO;
import me.mashyrin.filmLovers.model.DAO.RoleDAO;
import me.mashyrin.filmLovers.model.entities.Film;
import me.mashyrin.filmLovers.model.entities.Filmworker;
import me.mashyrin.filmLovers.model.entities.FilmworkersRole;
import me.mashyrin.filmLovers.view.OptionPane;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static me.mashyrin.filmLovers.view.Config.ERRORS.ERROR_TITLE;

/**
 * Actors table model class
 *
 * @author mashyrin
 */
public class ActorsTableModel implements TableModel {
    
    private FilmworkersRoleDAO filmworkersRoleDAO = new FilmworkersRoleDAO();
    private Set<TableModelListener> listeners = new HashSet<>();
    private Film film;
    
    public ActorsTableModel( Film film ) {
        super();
        this.film = film;
    }
    
    @Override
    public int getRowCount() {
        try {
            ArrayList<FilmworkersRole> filmworkersRoleList = filmworkersRoleDAO.selectAll();
            ArrayList<FilmworkersRole> buffList = new ArrayList<>();
            for( FilmworkersRole filmworkersRole : filmworkersRoleList ) {
                if( filmworkersRole.getFilmId().equals( film.getFilmId() ) ) {
                    buffList.add( filmworkersRole );
                }
            }
            return buffList.size();
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", ERROR_TITLE );
            System.err.println( e.toString() );
            return 0;
        }
    }
    
    @Override
    public int getColumnCount() {
        return 5;
    }
    
    @Override
    public String getColumnName( int columnIndex ) {
        switch( columnIndex ) {
            case 0:
                return "Имя";
            case 1:
                return "Фамилия";
            case 2:
                return "Возраст";
            case 3:
                return "Страна";
            case 4:
                return "Роль";
        }
        return null;
    }
    
    @Override
    public Class<?> getColumnClass( int columnIndex ) {
        if( columnIndex == 2 ) {
            return Integer.class;
        } else {
            return String.class;
        }
    }
    
    @Override
    public boolean isCellEditable( int rowIndex, int columnIndex ) {
        return false;
    }
    
    @Override
    public Object getValueAt( int rowIndex, int columnIndex ) {
        try {
            ArrayList<FilmworkersRole> filmworkersRoleList = filmworkersRoleDAO.selectAll();
            ArrayList<FilmworkersRole> buffList = new ArrayList<>();
            for( FilmworkersRole filmworkersRole : filmworkersRoleList ) {
                if( filmworkersRole.getFilmId().equals( film.getFilmId() ) ) {
                    buffList.add( filmworkersRole );
                }
            }
            FilmworkersRole filmworkersRole = buffList.get( rowIndex );
            Filmworker filmworker = new FilmworkerDAO().selectById( filmworkersRole.getFilmworkerId() );
            
            switch( columnIndex ) {
                case 0:
                    return filmworker.getName();
                case 1:
                    return filmworker.getSurname();
                case 2:
                    return filmworker.getBirthday();
                case 3:
                    return ( new CountryDAO().selectById( filmworker.getCountryId() ).getName() );
                case 4:
                    return ( new RoleDAO().selectById( filmworkersRole.getRoleId() ).getRoleName() );
            }
        } catch( SQLException | NullPointerException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", ERROR_TITLE );
        }
        return null;
    }
    
    public FilmworkersRole getValueAt( int rowIndex ) {
        try {
            return filmworkersRoleDAO.selectAll().get( rowIndex );
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", ERROR_TITLE );
            System.err.println( e.toString() );
        }
        return null;
    }
    
    @Override
    public void setValueAt( Object aValue, int rowIndex, int columnIndex ) {
        //Nothing
    }
    
    @Override
    public void addTableModelListener( TableModelListener tableModelListener ) {
        listeners.add( tableModelListener );
    }
    
    @Override
    public void removeTableModelListener( TableModelListener tableModelListener ) {
        listeners.remove( tableModelListener );
    }
}
