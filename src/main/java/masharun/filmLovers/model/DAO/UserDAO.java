package masharun.filmLovers.model.DAO;

import masharun.filmLovers.model.entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAO<User, String> {
    
    @Override
    public ArrayList<User> selectAll() throws SQLException {
        ArrayList<User> userArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_film_user" ).toString() );
            
            while( resultSet.next() ) {
                User result = new User();
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
                userArrayList.add( result );
            }
        }
        
        return userArrayList;
    }
    
    @Override
    public User selectById( String id ) throws SQLException {
        User result = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_film_user WHERE login = ?;" );
            preparedStatement.setString( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
    
    @Override
    public void deleteById( String id ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT delete_user(?)" );
            preparedStatement.setString( 1, id );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    @Override
    public User insert( User entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT add_user(?,?)" );
            preparedStatement.setString( 1, entity.getLogin() );
            preparedStatement.setString( 2, entity.getPassword() );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return entity;
    }
    
    @Override
    public void update( User entity ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT edit_user(?,?,?,?,?,?)" );
            preparedStatement.setString( 1, entity.getName() );
            preparedStatement.setString( 2, entity.getSurname() );
            preparedStatement.setInt( 3, entity.getBirthday() );
            preparedStatement.setString( 4, entity.getLogin() );
            preparedStatement.setString( 5, entity.getPassword() );
            preparedStatement.setString( 6, entity.getRole() );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    public void update( String login, String password ) throws SQLException {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement( "SELECT edit_user(null,null,null,?,?,'U')" );
            preparedStatement.setString( 1, login );
            preparedStatement.setString( 2, password );
            preparedStatement.executeQuery();
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
    }
    
    public User getIfExists( String id, String password ) throws SQLException {
        User result = new User();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_film_user WHERE login = ? AND password = ?;" );
            preparedStatement.setString( 1, id );
            preparedStatement.setString( 2, password );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
                result.setRole( resultSet.getString( "role" ) );
            }
        } finally {
            if( preparedStatement != null ) {
                preparedStatement.close();
            }
        }
        return result;
    }
}
