package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlDB.repository.UserRoleRepository;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }
    
    public List<UserRole> findByUserRoleId(Integer userRoleId) {
        return userRoleRepository.findByUserRoleId(userRoleId);
    }
    
    public List<UserRole> findByDescription(String description) {
        return userRoleRepository.findByDescription(description);
    }
    
    public UserRole findUserRoleByDescription(String description) {
        List<UserRole> userRoleList = userRoleRepository.findByDescription(description);
        if((userRoleList != null) && (userRoleList.size() > 0))
            return userRoleList.get(0);
        return null;
    }
    
    public UserRole save(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
    
    public void delete(UserRole entity) {
        userRoleRepository.delete(entity);
    }
    
}
