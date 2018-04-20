package masharun.filmLovers.models.DAO;

import masharun.filmLovers.models.entities.Score;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScoreDAO extends ExtendedDAO <Score, Integer, String> {
    
    @Override
    public ArrayList<Score> selectAll() throws SQLException {
        ArrayList<Score> scoreArrayList = new ArrayList<>();
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM get_score_film_to_user" )
                    .toString());
            
            while ( resultSet.next()) {
                Score result = new Score();
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setLogin( resultSet.getString( "login" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setScore( resultSet.getDouble ( "score" ) );
                scoreArrayList.add( result );
            }
        }
        return scoreArrayList;
    }
    
    @Override
    @Deprecated
    public Score selectById( Integer id ) throws SQLException {
        //doesn't need
        return null;
    }
    
    @Override
    @Deprecated
    public void deleteById( Integer id ) throws SQLException {
        //doesn't need
    }
    
    @Override
    public Score insert( Score entity ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT add_score('" )
                            .append( entity.getLogin() )
                            .append( "', " )
                            .append( entity.getFilmId() )
                            .append( ", " )
                            .append( entity.getScore() )
                            .append( ",'" )
                            .append( entity.getRole() )
                            .append( "');" )
                            .toString()
            );
        }
        return entity;
    }
    
    @Override
    @Deprecated
    public void update( Score entity ) throws SQLException {
        //doesn't need
    }
    
    @Override
    public Score selectById( Integer id, String secId ) throws SQLException {
        Score result = new Score();
        
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM get_score_film_to_user WHERE film_id = " )
                    .append( id )
                    .append( " AND login = '" )
                    .append( secId )
                    .append( "';" )
                    .toString() );
        
            while( resultSet.next() ) {
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setLogin( resultSet.getString( "login" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setScore( resultSet.getDouble ( "score" ) );
            }
        }
        return result;
    }
    
    @Override
    public void deleteById( Integer id, String secId ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            System.err.println( id + " " + secId );
            statement.execute(
                    new StringBuilder( "SELECT * FROM delete_score('" )
                            .append( secId )
                            .append( "'," )
                            .append( id )
                            .append( ");" )
                            .toString()
            );
        }
    }
}
