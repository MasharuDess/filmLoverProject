package me.mashyrin.filmLovers.model.entities;

public class Country {
    private Integer countryId;
    private String name;
    
    public Country() {
    }
    
    public Country( Integer countryId ) {
        this( countryId, null );
    }
    
    public Country( Integer countryId, String name ) {
        this.countryId = countryId;
        this.name = name;
    }
    
    public Integer getCountryId() {
        return countryId;
    }
    
    public void setCountryId( Integer countryId ) {
        this.countryId = countryId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
}
