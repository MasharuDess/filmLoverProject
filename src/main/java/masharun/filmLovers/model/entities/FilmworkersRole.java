package masharun.filmLovers.model.entities;

public class FilmworkersRole {
    private Integer roleId;
    private Integer filmworkerId;
    private Integer filmId;
    
    public FilmworkersRole() {
    }
    
    public FilmworkersRole( Integer roleId, Integer filmworkerId, Integer filmId ) {
        this.roleId = roleId;
        this.filmId = filmId;
        this.filmworkerId = filmworkerId;
    }
    
    public Integer getFilmworkerId() {
        return filmworkerId;
    }
    
    public void setFilmworkerId( Integer filmworkerId ) {
        this.filmworkerId = filmworkerId;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
    
    public Integer getFilmId() {
        return filmId;
    }
    
    public void setFilmId( Integer filmId ) {
        this.filmId = filmId;
    }
}
