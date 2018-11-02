package com.apu.TcpServerForAccessControlMVC.service.i;

import java.util.List;

import com.apu.TcpServerForAccessControlDB.entity.UserRole;

public interface MvcUserRoleService {

    public List<UserRole> findAll();
    
    public List<UserRole> findByUserRoleId(Integer userRoleId);
    
    public List<UserRole> findByDescription(String description);
    
    public UserRole findUserRoleByDescription(String description);
    
    public UserRole save(UserRole userRole);
    
    public void delete(UserRole entity);
    
}
