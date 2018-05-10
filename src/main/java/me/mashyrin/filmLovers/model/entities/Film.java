package me.mashyrin.filmLovers.model.entities;

import java.sql.Date;

/**
 * Films entity class
 *
 * @author mashyrin
 */
public class Film {
    private Date releaseDate;
    private Integer budget;
    private Double score;
    private Double criticScore;
    private String comment;
    private Integer filmId;
    private Integer genreId;
    private String name;
    private Integer countryId;
    
    /**
     * Empty film constructor
     */
    public Film() {
    }
    
    /**
     * Full film constructor
     *
     * @param filmId
     * @param genreId
     * @param name
     * @param countryId
     * @param comment
     * @param score
     * @param criticScore
     * @param budget
     * @param releaseDate
     */
    public Film( Integer filmId, Integer genreId, String name, Integer countryId, String comment,
                 Double score, Double criticScore, Integer budget, Date releaseDate ) {
        this.filmId = filmId;
        this.genreId = genreId;
        this.name = name;
        this.countryId = countryId;
        this.comment = comment;
        this.score = score;
        this.criticScore = criticScore;
        this.budget = budget;
        this.releaseDate = releaseDate;
    }
    
    /**
     * @return release date
     */
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    /**
     * @param releaseDate
     */
    public void setReleaseDate( Date releaseDate ) {
        this.releaseDate = releaseDate;
    }
    
    /**
     * @return score
     */
    public Double getScore() {
        return score;
    }
    
    /**
     * @param score
     */
    public void setScore( Double score ) {
        this.score = score;
    }
    
    /**
     * @return critic score
     */
    public Double getCriticScore() {
        return criticScore;
    }
    
    /**
     * @param criticScore
     */
    public void setCriticScore( Double criticScore ) {
        this.criticScore = criticScore;
    }
    
    /**
     * @return budget
     */
    public Integer getBudget() {
        return budget;
    }
    
    /**
     * @param budget
     */
    public void setBudget( Integer budget ) {
        this.budget = budget;
    }
    
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name
     */
    public void setName( String name ) {
        this.name = name;
    }
    
    /**
     * @return comment
     */
    public String getComment() {
        return comment;
    }
    
    /**
     * @param comment
     */
    public void setComment( String comment ) {
        this.comment = comment;
    }
    
    /**
     * @return country id
     */
    public Integer getCountryId() {
        return countryId;
    }
    
    /**
     * @param countryId
     */
    public void setCountryId( Integer countryId ) {
        this.countryId = countryId;
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
    
    /**
     * @return genre id
     */
    public Integer getGenreId() {
        return genreId;
    }
    
    /**
     * @param genreId
     */
    public void setGenreId( Integer genreId ) {
        this.genreId = genreId;
    }
    
}
