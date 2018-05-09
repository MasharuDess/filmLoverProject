package me.mashyrin.filmLovers.model.entities;

public class User {
    private String login;
    private String password;
    private String role;
    private String surname;
    private String name;
    private Integer birthday;
    
    public User() {
    }
    
    public User( String login, String password ) {
        this( login, password,
                "U", null, null, null );
    }
    
    public User( String login, String password, String role, String name, String surname, Integer birthday ) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }
    
    public void setLogin( String login ) {
        this.login = login;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setPassword( String password ) {
        this.password = password;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setRole( String role ) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setSurname( String surname ) {
        this.surname = surname;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public void setBirthday( Integer birthday ) {
        this.birthday = birthday;
    }
    
    public Integer getBirthday() {
        return birthday;
    }
}

