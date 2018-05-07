package masharun.filmLovers.model.tableModels;

import masharun.filmLovers.model.DAO.UserDAO;
import masharun.filmLovers.model.entities.User;
import masharun.filmLovers.view.OptionPane;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AdminTableModel implements TableModel {
    
    private UserDAO userDAO = new UserDAO();
    private Set<TableModelListener> listeners = new HashSet<>();
    
    @Override
    public int getRowCount() {
        try {
            return userDAO.selectAll().size();
        } catch ( SQLException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", "Ошибка" );
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
                return "Логин";
            case 1:
                return "Имя";
            case 2:
                return "Фамилия";
            case 3:
                return "Возраст";
            case 4:
                return "Роль";
        }
        
        return null;
    }
    
    @Override
    public Class<?> getColumnClass( int columnIndex ) {
        if ( columnIndex == 0 || columnIndex == 3 ) {
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
            ArrayList<User> userArrayList = userDAO.selectAll();
            User user = userArrayList.get( rowIndex );
        
            switch ( columnIndex ) {
                case 0:
                    return user.getLogin();
                case 1:
                    return user.getName();
                case 2:
                    return user.getSurname();
                case 3:
                    return user.getBirthday();
                case 4:
                    return user.getRole();
            }
        } catch ( SQLException | NullPointerException e ) {
            OptionPane.showMessage( "Ошибка загрузки записей базы данных", "Ошибка" );
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
