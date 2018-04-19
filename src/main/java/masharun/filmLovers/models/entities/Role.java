package masharun.filmLovers.models.entities;

public class Role {
    private Integer roleId;
    private String name;
    
    public Role() {
    }
    
    public Role ( Integer roleId ) {
        this( roleId, null );
    }
    
    public Role ( Integer roleId, String name ) {
        this.roleId = roleId;
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name ) {
        this.name = name;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
}
