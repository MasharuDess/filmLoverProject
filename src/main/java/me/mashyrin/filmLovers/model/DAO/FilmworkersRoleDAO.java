package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.controller.ConnectionManager;
import me.mashyrin.filmLovers.model.entities.FilmworkersRole;

import java.sql.*;
import java.util.ArrayList;

public class FilmworkersRoleDAO {
    
    private Connection connection = ConnectionManager.getConnection();
    
    public ArrayList<FilmworkersRole> selectAll() throws SQLException {
        ArrayList<FilmworkersRole> filmworkersRoleArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_filmworkers_role" ).toString() );
            
            while( resultSet.next() ) {
                FilmworkersRole result = new FilmworkersRole();
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setFilmworkerId( resultSet.getInt( "filmworker_id" ) );
                result.setRoleId( resultSet.getInt( "role_id" ) );
                filmworkersRoleArrayList.add( result );
            }
        }
        return filmworkersRoleArrayList;
        
    }
    
    public void deleteById( Integer roleId, Integer filmworkerId, Integer filmId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_filmworkers_role(?,?,?)" );
            preparedStatement.setInt( 1, roleId );
            preparedStatement.setInt( 2, filmworkerId );
            preparedStatement.setInt( 3, filmId );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    public void insert( Integer roleId, Integer filmworkerId, Integer filmId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_filmworkers_role(?,?,?)" );
            preparedStatement.setInt( 1, roleId );
            preparedStatement.setInt( 2, filmworkerId );
            preparedStatement.setInt( 3, filmId );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
}
