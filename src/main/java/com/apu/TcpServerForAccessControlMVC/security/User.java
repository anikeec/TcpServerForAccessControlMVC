package com.apu.TcpServerForAccessControlMVC.security;

import java.util.Set;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;

public class User extends SystemUser {

    private Set<UserRole> roles;
    
    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }
    
}
