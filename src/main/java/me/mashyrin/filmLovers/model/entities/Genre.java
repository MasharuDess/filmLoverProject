package me.mashyrin.filmLovers.model.entities;

/**
 * Genres entity class
 *
 * @author mashyrin
 */
public class Genre {
    private Integer genreId;
    private String genre;
    
    /**
     * Empty genre's constructor
     */
    public Genre() {
    }
    
    /**
     * Another genre's constructor
     *
     * @param genreId
     */
    public Genre( Integer genreId ) {
        this( genreId, null );
    }
    
    /**
     * Full genre's constructor
     *
     * @param genreId
     * @param genre
     */
    public Genre( Integer genreId, String genre ) {
        this.genreId = genreId;
        this.genre = genre;
    }
    
    /**
     * @param genreId
     */
    public void setGenreId( Integer genreId ) {
        this.genreId = genreId;
    }
    
    /**
     * @return genre id
     */
    public Integer getGenreId() {
        return genreId;
    }
    
    /**
     * @return genre
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * @param genre
     */
    public void setGenre( String genre ) {
        this.genre = genre;
    }
}
