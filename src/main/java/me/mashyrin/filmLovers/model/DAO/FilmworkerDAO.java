package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.model.entities.Filmworker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Filmworkers DAO
 */
public class FilmworkerDAO extends DAO<Filmworker, Integer> {
    
    @Override
    public ArrayList<Filmworker> selectAll() throws SQLException {
        ArrayList<Filmworker> filmworkerArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_filmworker" ).toString() );
            
            while( resultSet.next() ) {
                Filmworker result = new Filmworker();
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
                result.setFilmworkerId( resultSet.getInt( "filmworker_id" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
                filmworkerArrayList.add( result );
            }
        }
        
        return filmworkerArrayList;
    }
    
    @Override
    public Filmworker selectById( Integer id ) throws SQLException {
        Filmworker result = new Filmworker();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_filmworker WHERE filmworker_id = ?;" );
            preparedStatement.setInt( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
                result.setFilmworkerId( resultSet.getInt( "filmworker_id" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
    
    @Override
    public void deleteById( Integer id ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_filmworker(?)" );
            preparedStatement.setInt( 1, id );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    @Override
    public Filmworker insert( Filmworker entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_filmworker(?,?,?,?)" );
            preparedStatement.setString( 1, entity.getName() );
            preparedStatement.setString( 2, entity.getSurname() );
            preparedStatement.setInt( 3, entity.getBirthday() );
            preparedStatement.setInt( 4, entity.getCountryId() );
            
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            entity.setFilmworkerId( resultSet.getInt( 1 ) );
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return entity;
    }
    
    @Override
    public void update( Filmworker entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT edit_filmworker(?,?,?,?,?)" );
            preparedStatement.setString( 1, entity.getName() );
            preparedStatement.setString( 2, entity.getSurname() );
            preparedStatement.setInt( 3, entity.getBirthday() );
            preparedStatement.setInt( 4, entity.getCountryId() );
            preparedStatement.setInt( 5, entity.getFilmworkerId() );
            preparedStatement.execute();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
}
