package masharun.filmLovers.models.DAO;

import masharun.filmLovers.models.Hasher;
import masharun.filmLovers.models.entities.User;
import masharun.filmLovers.view.OptionPane;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAO <User, String> {
    
    @Override
    public ArrayList<User> selectAll() throws SQLException {
        ArrayList<User> userArrayList = new ArrayList<>();
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM get_film_user" )
                    .toString());
            
            while ( resultSet.next()) {
                User result = new User();
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setName( resultSet.getString ( "name" ) );
                result.setSurname( resultSet.getString ( "surname" ) );
                result.setBirthday( resultSet.getInt ( "birthday" ) );
                userArrayList.add( result );
            }
        }
        
        return userArrayList;
    }
    
    @Override
    public User selectById( String id ) throws SQLException {
        User result = new User();
        
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM get_film_user WHERE login = '" )
                    .append( id )
                    .append( "';" )
                    .toString() );
            
            while( resultSet.next() ) {
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setRole( resultSet.getString( "role" ) );
                result.setName( resultSet.getString ( "name" ) );
                result.setSurname( resultSet.getString ( "surname" ) );
                result.setBirthday( resultSet.getInt ( "birthday" ) );
            }
        }
        
        return result;
    }
    
    @Override
    public void deleteById( String id ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.delete_user('" )
                            .append( id )
                            .append( "');" )
                            .toString()
            );
        }
    }
    
    @Override
    public User insert( User entity ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.add_user('" )
                            .append( entity.getLogin() )
                            .append( "', '" )
                            .append( entity.getPassword() )
                            .append( "');" )
                            .toString()
            );
        }
        return entity;
    }
    
    @Override
    public void update( User entity ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.edit_user('" )
                            .append( entity.getName())
                            .append( "','" )
                            .append( entity.getSurname())
                            .append( "'," )
                            .append( entity.getBirthday())
                            .append( ",'" )
                            .append( entity.getLogin() )
                            .append( "','")
                            .append( entity.getPassword())
                            .append( "','" )
                            .append( entity.getRole())
                            .append( "');" )
                            .toString());
        }
    }
    
    public void update ( String login, String password ) throws SQLException {
        try ( Statement statement = connection.createStatement() ) {
            statement.executeQuery(
                    new StringBuilder( "SELECT public.edit_user( null, null, null, '" )
                            .append( login )
                            .append( "','")
                            .append( password )
                            .append( "','" )
                            .append( "U" )
                            .append( "');" )
                            .toString());
        }
    }
    
    public User getIfExists( String id, String password ) throws SQLException {
        User result = new User();

        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM get_film_user WHERE login= '" )
                    .append( id )
                    .append( "' AND password= '" )
                    .append( password )
                    .append( "';" )
                    .toString() );

            while( resultSet.next() ) {
                result.setLogin( resultSet.getString( "login" ) );
                result.setPassword( resultSet.getString( "password" ) );
                result.setName( resultSet.getString( "name" ) );
                result.setSurname( resultSet.getString( "surname" ) );
                result.setBirthday( resultSet.getInt( "birthday" ) );
                result.setRole( resultSet.getString( "role" ) );
            }
        }

        return result;
    }
}
