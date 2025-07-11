package com.jocata.userservice.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "user_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole() {}

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
        this.id = new UserRoleId(user.getId(), role.getId());
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (this.id == null) {
            this.id = new UserRoleId();
        }
        this.id.setUserId(user.getId());
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
        if (this.id == null) {
            this.id = new UserRoleId();
        }
        this.id.setRoleId(role.getId());
    }
}
