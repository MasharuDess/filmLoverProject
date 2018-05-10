package me.mashyrin.filmLovers.model.entities;

/**
 * Roles entity class
 *
 * @author mashyrin
 */
public class Role {
    private Integer roleId;
    private String roleName;
    
    /**
     * Empty role constructor
     */
    public Role() {
    }
    
    /**
     * Full role constructor
     *
     * @param roleId
     * @param roleName
     */
    public Role( Integer roleId, String roleName ) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    /**
     * @return role name
     */
    public String getRoleName() {
        return roleName;
    }
    
    /**
     * @param roleName
     */
    public void setRoleName( String roleName ) {
        this.roleName = roleName;
    }
    
    /**
     * @return role id
     */
    public Integer getRoleId() {
        return roleId;
    }
    
    /**
     * @param roleId
     */
    public void setRoleId( Integer roleId ) {
        this.roleId = roleId;
    }
}
