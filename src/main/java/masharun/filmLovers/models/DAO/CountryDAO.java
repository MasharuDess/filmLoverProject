package masharun.filmLovers.models.DAO;

import masharun.filmLovers.models.entities.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CountryDAO extends DAO<Country, Integer > {
    
    @Override
    public ArrayList<Country> selectAll() throws SQLException {
        ArrayList<Country> countryArrayList = new ArrayList<>();
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM country" )
                    .toString());
        
            while ( resultSet.next()) {
                Country result = new Country();
                result.setName( resultSet.getString( "country_name" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
                countryArrayList.add( result );
            }
        }
        return countryArrayList;
    }
    
    @Override
    public Country selectById( Integer id ) throws SQLException {
        Country result = new Country();
        
        try ( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery( new StringBuilder(
                    "SELECT * FROM country WHERE country_id = " )
                    .append( id )
                    .append( ";" )
                    .toString() );
        
            while( resultSet.next() ) {
                result.setName( resultSet.getString( "country_name" ) );
                result.setCountryId( resultSet.getInt( "country_id" ) );
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
    public Country insert( Country entity ) throws SQLException {
        //can't be inserted
        return null;
    }
    
    @Override
    @Deprecated
    public void update( Country entity ) throws SQLException {
        //can't be updated
    }
}
