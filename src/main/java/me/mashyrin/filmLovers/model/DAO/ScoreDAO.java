package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.Main;
import me.mashyrin.filmLovers.model.entities.Score;
import me.mashyrin.filmLovers.model.entities.ScoreCount;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ScoreDAO extends ExtendedDAO<Score, Integer, String> {
    
    @Override
    public ArrayList<Score> selectAll() throws SQLException {
        ArrayList<Score> scoreArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_score_film_to_user" ).toString() );
            
            while( resultSet.next() ) {
                Score result = new Score();
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setLogin( resultSet.getString( "login" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setScore( resultSet.getDouble( "score" ) );
                scoreArrayList.add( result );
            }
        }
        return scoreArrayList;
    }
    
    public ArrayList<ScoreCount> selectCountScore( String login ) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<ScoreCount> scoreList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(score) AS score_count, score FROM get_score_film_to_user" +
                            " WHERE login = ? GROUP BY score;" );
            preparedStatement.setString( 1, Main.getCurrentUser().getLogin() );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                ScoreCount scoreCount = new ScoreCount();
                scoreCount.setCount( resultSet.getInt( "score_count" ) );
                scoreCount.setScore( resultSet.getInt( "score" ) );
                scoreList.add( scoreCount );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return scoreList;
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_score(?,?,?,?)" );
            preparedStatement.setString( 1, entity.getLogin() );
            preparedStatement.setInt( 2, entity.getFilmId() );
            preparedStatement.setDouble( 3, entity.getScore() );
            preparedStatement.setString( 4, entity.getRole() );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_score_film_to_user WHERE film_id = ? AND login = ?;" );
            preparedStatement.setInt( 1, id );
            preparedStatement.setString( 2, secId );
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while( resultSet.next() ) {
                result.setFilmId( resultSet.getInt( "film_id" ) );
                result.setLogin( resultSet.getString( "login" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setScore( resultSet.getDouble( "score" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        
        return result;
    }
    
    @Override
    public void deleteById( Integer id, String secId ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_score(?,?)" );
            preparedStatement.setString( 1, secId );
            preparedStatement.setInt( 2, id );
            preparedStatement.execute();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
}
