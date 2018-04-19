package masharun.filmLovers.models.entities;

public class Score {
    
    private Double score;
    private String role;
    private String login;
    private Integer filmId;
    
    public Score() {
    }
    
    public Score( Double score, String role, String login, Integer filmId ) {
        this.filmId = filmId;
        this.score = score;
        this.role = role;
        this.login = login;
    }
    
    public void setScore( Double score ) {
        this.score = score;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setRole( String role ) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setFilmId( Integer filmId ) {
        this.filmId = filmId;
    }
    
    public Integer getFilmId() {
        return filmId;
    }
    
    public void setLogin( String login ) {
        this.login = login;
    }
    
    public String getLogin() {
        return login;
    }
}
