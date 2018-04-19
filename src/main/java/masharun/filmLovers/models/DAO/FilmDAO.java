package masharun.filmLovers.models.DAO;

import masharun.filmLovers.models.entities.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FilmDAO extends DAO<Film, Integer> {
    
    @Override
    public ArrayList<Film> selectAll() throws SQLException {
        ArrayList<Film> filmArrayList = new ArrayList<>();
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM film" )
                    .toString());
            
            while ( resultSet.next()) {
                Film result = new Film();
                result.setName( resultSet.getString( "name" ) );
                result.setBudget( resultSet.getInt( "budget" ) );
                result.setComment( resultSet.getString( "comment" ) );
                result.setCountryId( resultSet.getInt ( "country_id" ) );
                result.setCriticScore( resultSet.getDouble ( "critic_score" ) );
                result.setFilmId( resultSet.getInt ( "film_id" ) );
                result.setGenreId( resultSet.getInt ( "genre_id" ) );
                result.setReleaseDate( resultSet.getDate ( "release_date" ) );
                result.setScore( resultSet.getDouble ( "score" ) );
                filmArrayList.add( result );
            }
        }
        return filmArrayList;
    }
    
    @Override
    public Film selectById( Integer id ) throws SQLException {
        Film result = new Film();
        
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM film WHERE film_id = " )
                    .append( id )
                    .append( ";" )
                    .toString() );
            
            while( resultSet.next() ) {
                result.setName( resultSet.getString( "name" ) );
                result.setBudget( resultSet.getInt( "budget" ) );
                result.setComment( resultSet.getString( "comment" ) );
                result.setCountryId( resultSet.getInt ( "country_id" ) );
                result.setCriticScore( resultSet.getDouble ( "critic_score" ) );
                result.setFilmId( resultSet.getInt ( "film_id" ) );
                result.setGenreId( resultSet.getInt ( "genre_id" ) );
                result.setReleaseDate( resultSet.getDate ( "release_date" ) );
                result.setScore( resultSet.getDouble ( "score" ) );
            }
        }
        
        return result;
    }
    
    @Override
    public void deleteById( Integer id ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.delete_film(" )
                            .append( id )
                            .append( ");" )
                            .toString()
            );
        }
    }
    
    @Override
    public Film insert( Film entity ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.add_film('" )
                            .append( entity.getName() )
                            .append( "', " )
                            .append( entity.getGenreId() )
                            .append( ");" )
                            .toString()
            );
        }
        return entity;
    }
    
    @Override
    public void update( Film entity ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT edit_film(" )
                            .append( entity.getReleaseDate() == null ? null: "'" + entity.getReleaseDate() + "'")
                            .append( "," )
                            .append( entity.getBudget())
                            .append( ",'" )
                            .append( entity.getComment())
                            .append( "'," )
                            .append( entity.getGenreId() )
                            .append( ",")
                            .append( entity.getCountryId())
                            .append( ",'" )
                            .append( entity.getName())
                            .append( "'," )
                            .append( entity.getFilmId() )
                            .append( ");" )
                            .toString());
        }
    }
}
