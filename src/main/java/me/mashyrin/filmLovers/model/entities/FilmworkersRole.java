package me.mashyrin.filmLovers.model.entities;

/**
 * Films workers roles entity class
 *
 * @author mashyrin
 */
public class FilmworkersRole {
    private Integer roleId;
    private Integer filmworkerId;
    private Integer filmId;
    
    /**
     * Empty film worker's role constructor
     */
    public FilmworkersRole() {
    }
    
    /**
     * Full film worker's role constructor
     *
     * @param roleId
     * @param filmworkerId
     * @param filmId
     */
    public FilmworkersRole( Integer roleId, Integer filmworkerId, Integer filmId ) {
        this.roleId = roleId;
        this.filmId = filmId;
        this.filmworkerId = filmworkerId;
    }
    
    /**
     * @return film worker's id
     */
    public Integer getFilmworkerId() {
        return filmworkerId;
    }
    
    /**
     * @param filmworkerId
     */
    public void setFilmworkerId( Integer filmworkerId ) {
        this.filmworkerId = filmworkerId;
    }
    
    /**
     * @return role id
     */
    public Integer getRoleId() {
        return roleId;
    }
    
    /**
     * @param roleId
     */
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
    
    /**
     * @return film id
     */
    public Integer getFilmId() {
        return filmId;
    }
    
    /**
     * @param filmId
     */
    public void setFilmId( Integer filmId ) {
        this.filmId = filmId;
    }
}
