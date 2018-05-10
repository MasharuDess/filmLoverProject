package me.mashyrin.filmLovers.controller;

/**
 * Connection config class
 *
 * @author Mashyrin
 */
public final class ConnectionConfig {
    
    /**
     * Driver path
     */
    public static final String DRIVER_PATH = "org.postgresql.Driver";
    
    /**
     * URL for connection to database
     */
    public static final String URL = "jdbc:postgresql://localhost:5432/filmLoverDB";
    
    /**
     * Username
     */
    public static final String USER = "postgres";
    
    /**
     * Password
     */
    public static final String PASSWORD = "root";
}
