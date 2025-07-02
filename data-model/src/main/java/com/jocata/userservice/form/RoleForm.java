package com.jocata.userservice.form;

public class RoleForm {
    private String id;
    private String roleName;
    private String createdAt;
    private String updatedAt;
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}
