package masharun.filmLovers.model.entities;

public class Genre {
    private Integer genreId;
    private String genre;
    
    public Genre() {
    }
    
    public Genre( Integer genreId ) {
        this( genreId, null );
    }
    
    public Genre( Integer genreId, String genre ) {
        this.genreId = genreId;
        this.genre = genre;
    }
    
    public void setGenreId( Integer genreId ) {
        this.genreId = genreId;
    }
    
    public Integer getGenreId() {
        return genreId;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre( String genre ) {
        this.genre = genre;
    }
}
