package me.mashyrin.filmLovers.model.entities;

import java.sql.Date;

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
    
    public Film() {
    }
    
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
    
    public Date getReleaseDate() {
        return releaseDate;
    }
    
    public void setReleaseDate( Date releaseDate ) {
        this.releaseDate = releaseDate;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore( Double score ) {
        this.score = score;
    }
    
    public Double getCriticScore() {
        return criticScore;
    }
    
    public void setCriticScore( Double criticScore ) {
        this.criticScore = criticScore;
    }
    
    public Integer getBudget() {
        return budget;
    }
    
    public void setBudget( Integer budget ) {
        this.budget = budget;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment( String comment ) {
        this.comment = comment;
    }
    
    public Integer getCountryId() {
        return countryId;
    }
    
    public void setCountryId( Integer countryId ) {
        this.countryId = countryId;
    }
    
    public Integer getFilmId() {
        return filmId;
    }
    
    public void setFilmId( Integer filmId ) {
        this.filmId = filmId;
    }
    
    public Integer getGenreId() {
        return genreId;
    }
    
    public void setGenreId( Integer genreId ) {
        this.genreId = genreId;
    }
    
}
