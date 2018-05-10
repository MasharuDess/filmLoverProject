package me.mashyrin.filmLovers.model.entities;

/**
 * Countries entity class
 *
 * @author mashyrin
 */
public class Country {
    private Integer countryId;
    private String name;
    
    /**
     * Empty country constructor
     */
    public Country() {
    }
    
    /**
     * Another country constructor
     *
     * @param countryId
     */
    public Country( Integer countryId ) {
        this( countryId, null );
    }
    
    /**
     * Full country constructor
     *
     * @param countryId
     * @param name
     */
    public Country( Integer countryId, String name ) {
        this.countryId = countryId;
        this.name = name;
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
}
