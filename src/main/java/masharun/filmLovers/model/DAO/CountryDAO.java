package masharun.filmLovers.model.DAO;

import masharun.filmLovers.model.entities.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CountryDAO extends DAO<Country, Integer> {
    
    @Override
    public ArrayList<Country> selectAll() throws SQLException {
        ArrayList<Country> countryArrayList = new ArrayList<>();
        try( Statement statement = connection.createStatement() ) {
            ResultSet resultSet = statement.executeQuery(
                    new StringBuilder( "SELECT * FROM get_country" ).toString() );
            
            while( resultSet.next() ) {
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM get_country WHERE country_id = ?;" );
            preparedStatement.setInt( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next() ) {
                result.setName( resultSet.getString( "country_name" ) );
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
