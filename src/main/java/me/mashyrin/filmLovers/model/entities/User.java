package me.mashyrin.filmLovers.model.entities;

/**
 * User entity class
 *
 * @author mashyrin
 */
public class User {
    private String login;
    private String password;
    private String role;
    private String surname;
    private String name;
    private Integer birthday;
    
    /**
     * Empty user constructor
     */
    public User() {
    }
    
    /**
     * Another user constructor
     *
     * @param login
     * @param password
     */
    public User( String login, String password ) {
        this( login, password,
                "U", null, null, null );
    }
    
    /**
     * Full user constructor
     *
     * @param login
     * @param password
     * @param role
     * @param name
     * @param surname
     * @param birthday
     */
    public User( String login, String password, String role, String name, String surname, Integer birthday ) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
    
    /**
     * @param login
     */
    public void setLogin( String login ) {
        this.login = login;
    }
    
    /**
     * @return login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * @param password
     */
    public void setPassword( String password ) {
        this.password = password;
    }
    
    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @param role
     */
    public void setRole( String role ) {
        this.role = role;
    }
    
    /**
     * @return role
     */
    public String getRole() {
        return role;
    }
    
    /**
     * @param name
     */
    public void setName( String name ) {
        this.name = name;
    }
    
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param surname
     */
    public void setSurname( String surname ) {
        this.surname = surname;
    }
    
    /**
     * @return surname
     */
    public String getSurname() {
        return surname;
    }
    
    /**
     * @param birthday
     */
    public void setBirthday( Integer birthday ) {
        this.birthday = birthday;
    }
    
    /**
     * @return birthday
     */
    public Integer getBirthday() {
        return birthday;
    }
}

