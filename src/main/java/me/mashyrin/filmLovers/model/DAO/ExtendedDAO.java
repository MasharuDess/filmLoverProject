package me.mashyrin.filmLovers.model.DAO;

import java.sql.SQLException;

/**
 * Extended Data Access Object class that extends DAO class
 *
 * @param <E> - Entity param
 * @param <K> - Entity's ID param
 * @param <V> - Entity's second ID param
 * @author mashyrin
 */
public abstract class ExtendedDAO<E, K, V> extends DAO<E, K> {
    
    /**
     * Call extended selecting by ID function
     *
     * @param id    - first id that selecting record
     * @param secId - second id that selecting record
     * @return returns entity
     * @throws SQLException - SQL error into database
     */
    public abstract E selectById( K id, V secId ) throws SQLException;
    
    /**
     * Call extended deleting by ID function
     *
     * @param id    - first id that selecting record
     * @param secId - second id that selecting record
     * @throws SQLException - SQL error into database
     */
    public abstract void deleteById( K id, V secId ) throws SQLException;
}
