package me.mashyrin.filmLovers.model.entities;

/**
 * Films workers entity class
 *
 * @author mashyrin
 */
public class Filmworker {
    private String name;
    private String surname;
    private Integer birthday;
    private Integer filmworkerId;
    private Integer countryId;
    
    /**
     * Empty films worker constructor
     */
    public Filmworker() {
    }
    
    /**
     * Full films worker constructor
     *
     * @param name
     * @param surname
     * @param birthday
     * @param filmworkerId
     * @param countryId
     */
    public Filmworker( String name, String surname, Integer birthday,
                       Integer filmworkerId, Integer countryId ) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.filmworkerId = filmworkerId;
        this.countryId = countryId;
    }
    
    /**
     * @return birthday
     */
    public Integer getBirthday() {
        return birthday;
    }
    
    /**
     * @param birthday
     */
    public void setBirthday( Integer birthday ) {
        this.birthday = birthday;
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
     * @return surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * @param surname
     */
    public void setSurname( String surname ) {
        this.surname = surname;
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
}
