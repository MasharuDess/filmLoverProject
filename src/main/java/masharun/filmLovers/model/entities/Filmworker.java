package masharun.filmLovers.model.entities;

public class Filmworker {
    private String name;
    private String surname;
    private Integer birthday;
    private Integer filmworkerId;
    private Integer countryId;
    
    public Filmworker() {
    }
    
    public Filmworker( String name, String surname, Integer birthday,
                       Integer filmworkerId, Integer countryId ) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
        this.filmworkerId = filmworkerId;
        this.countryId = countryId;
    }
    
    public Integer getBirthday() {
        return birthday;
    }
    
    public void setBirthday( Integer birthday ) {
        this.birthday = birthday;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setSurname( String surname ) {
        this.surname = surname;
    }
    
    public Integer getCountryId() {
        return countryId;
    }
    
    public void setCountryId( Integer countryId ) {
        this.countryId = countryId;
    }
    
    public Integer getFilmworkerId() {
        return filmworkerId;
    }
    
    public void setFilmworkerId( Integer filmworkerId ) {
        this.filmworkerId = filmworkerId;
    }
}
