package masharun.filmLovers.models.DAO;

import java.sql.SQLException;

public abstract class ExtendedDAO<E,K,V> extends DAO<E,K> {
    
    public abstract E selectById( K id, V secId ) throws SQLException;
    public abstract void deleteById( K id, V secId ) throws SQLException;
}
