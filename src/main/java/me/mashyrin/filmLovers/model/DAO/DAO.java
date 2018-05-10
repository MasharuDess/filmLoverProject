package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.controller.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Data Access Object pattern
 *
 * @param <E> - Entity param
 * @param <K> - Entity's ID param
 * @author mashyrin
 */
public abstract class DAO<E, K> {
    
    /**
     * Buff connection
     */
    protected Connection connection = ConnectionManager.getConnection();
    
    /**
     * Call selecting all records function
     *
     * @return returns ArrayList of records
     * @throws SQLException - SQL error into database
     */
    public abstract ArrayList<E> selectAll() throws SQLException;
    
    /**
     * Call selecting by ID function
     *
     * @param id - id that selecting record
     * @return returns entity
     * @throws SQLException - SQL error into database
     */
    public abstract E selectById( K id ) throws SQLException;
    
    /**
     * Call deleting by ID function
     *
     * @param id - id that selecting record
     * @throws SQLException - SQL error into database
     */
    public abstract void deleteById( K id ) throws SQLException;
    
    /**
     * Call inserting function
     *
     * @param entity
     * @return returns entity
     * @throws SQLException - SQL error into database
     */
    public abstract E insert( E entity ) throws SQLException;
    
    /**
     * Call updating function
     *
     * @param entity
     * @throws SQLException - SQL error into database
     */
    public abstract void update( E entity ) throws SQLException;
}
