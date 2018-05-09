package me.mashyrin.filmLovers.model.entities;

public class Role {
    private Integer roleId;
    private String roleName;
    
    public Role() {
    }
    
    public Role( Integer roleId, String roleName ) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return roleName;
    }
    
    public void setRoleName( String roleName ) {
        this.roleName = roleName;
    }
    
    public Integer getRoleId() {
        return roleId;
    }
    
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
}
