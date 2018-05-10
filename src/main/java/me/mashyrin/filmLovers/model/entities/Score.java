package me.mashyrin.filmLovers.model.entities;

/**
 * Scores entity class
 *
 * @author mashyrin
 */
public class Score {
    
    private Double score;
    private String role;
    private String login;
    private Integer filmId;
    
    /**
     * Empty score constructor
     */
    public Score() {
    }
    
    /**
     * Full score constructor
     *
     * @param score
     * @param role
     * @param login
     * @param filmId
     */
    public Score( Double score, String role, String login, Integer filmId ) {
        this.filmId = filmId;
        this.score = score;
        this.role = role;
        this.login = login;
    }
    
    /**
     * @param score
     */
    public void setScore( Double score ) {
        this.score = score;
    }
    
    /**
     * @return score
     */
    public Double getScore() {
        return score;
    }
    
    /**
     * @param role
     */
    public void setRole( String role ) {
        this.role = role;
    }
    
    /**
     * @return role
     */
    public String getRole() {
        return role;
    }
    
    /**
     * @param filmId
     */
    public void setFilmId( Integer filmId ) {
        this.filmId = filmId;
    }
    
    /**
     * @return film id
     */
    public Integer getFilmId() {
        return filmId;
    }
    
    /**
     * @param login
     */
    public void setLogin( String login ) {
        this.login = login;
    }
    
    /**
     * @return login
     */
    public String getLogin() {
        return login;
    }
}
