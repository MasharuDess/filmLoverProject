package me.mashyrin.filmLovers.model.DAO;

import me.mashyrin.filmLovers.controller.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class DAO<E, K> {
    
    protected Connection connection = ConnectionManager.getConnection();
    
    public abstract ArrayList<E> selectAll() throws SQLException;
    
    public abstract E selectById( K id ) throws SQLException;
    
    public abstract void deleteById( K id ) throws SQLException;
    
    public abstract E insert( E entity ) throws SQLException;
    
    public abstract void update( E entity ) throws SQLException;
}
