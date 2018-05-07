package masharun.filmLovers.model.tableModels;

import masharun.filmLovers.model.DAO.CountryDAO;
import masharun.filmLovers.model.DAO.FilmworkerDAO;
import masharun.filmLovers.model.entities.Filmworker;
import masharun.filmLovers.view.OptionPane;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class AllActorsTableModel implements TableModel {
    
    private FilmworkerDAO filmworkerDAO = new FilmworkerDAO();
    private Set<TableModelListener> listeners = new HashSet<>();
    
    @Override
    public int getRowCount() {
        try {
            return filmworkerDAO.selectAll().size();
        } catch( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", "Ошибка" );
            System.err.println( e.toString() );
            return 0;
        }
    }
    
    @Override
    public int getColumnCount() {
        return 4;
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
            Filmworker filmworker = filmworkerDAO.selectAll().get( rowIndex );
        
            switch ( columnIndex ) {
                case 0:
                    return filmworker.getName();
                case 1:
                    return filmworker.getSurname();
                case 2:
                    return filmworker.getBirthday();
                case 3:
                    return ( new CountryDAO().selectById( filmworker.getCountryId() ).getName() );
            }
        } catch ( SQLException | NullPointerException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", "Ошибка" );
            System.err.println( e.toString() );
        }
        return null;
    }
    
    public Filmworker getValueAt( int rowIndex ) {
        try {
            return filmworkerDAO.selectAll().get( rowIndex );
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", "Ошибка" );
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
