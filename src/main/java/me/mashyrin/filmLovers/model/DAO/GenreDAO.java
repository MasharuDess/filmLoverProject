package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.model.entities.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GenreDAO extends DAO<Genre, Integer> {
    
    @Override
    public ArrayList<Genre> selectAll() throws SQLException {
        ArrayList<Genre> genreArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_genre" ).toString() );
            
            while( resultSet.next() ) {
                Genre result = new Genre();
                result.setGenre( resultSet.getString( "genre" ) );
                result.setGenreId( resultSet.getInt( "genre_id" ) );
                genreArrayList.add( result );
            }
        }
        return genreArrayList;
    }
    
    @Override
    public Genre selectById( Integer id ) throws SQLException {
        Genre result = new Genre();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_genre WHERE genre_id = ?;" );
            preparedStatement.setInt( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setGenre( resultSet.getString( "genre" ) );
                result.setGenreId( resultSet.getInt( "genre_id" ) );
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
        //can't be deleted
    }
    
    @Override
    @Deprecated
    public Genre insert( Genre entity ) throws SQLException {
        //can't be inserted
        return null;
    }
    
    @Override
    @Deprecated
    public void update( Genre entity ) throws SQLException {
        //can't be updated
    }
}
