package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.model.entities.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoleDAO extends DAO<Role, Integer> {
    
    @Override
    public ArrayList<Role> selectAll() throws SQLException {
        ArrayList<Role> roleArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_role" ).toString() );
            
            while( resultSet.next() ) {
                Role result = new Role();
                result.setRoleName( resultSet.getString( "role_name" ) );
                result.setRoleId( resultSet.getInt( "role_id" ) );
                roleArrayList.add( result );
            }
        }
        return roleArrayList;
    }
    
    @Override
    public Role selectById( Integer id ) throws SQLException {
        Role result = new Role();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT * FROM get_role WHERE role_id = ?;" );
            preparedStatement.setInt( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setRoleName( resultSet.getString( "role_name" ) );
                result.setRoleId( resultSet.getInt( "role_id" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
    
    @Override
    @Deprecated
    public void deleteById( Integer id ) throws SQLException {
    
    }
    
    @Override
    @Deprecated
    public Role insert( Role entity ) throws SQLException {
        return null;
    }
    
    @Override
    @Deprecated
    public void update( Role entity ) throws SQLException {
    
    }
}
