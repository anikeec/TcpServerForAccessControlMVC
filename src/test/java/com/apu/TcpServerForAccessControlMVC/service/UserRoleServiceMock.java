package com.apu.TcpServerForAccessControlMVC.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.UserRole;

@Service
@Profile("test")
public class UserRoleServiceMock extends UserRoleService {

    List<UserRole> itemCollection = new ArrayList<>();
    
    @Override
    public List<UserRole> findAll() {
        return itemCollection;
    }

    @Override
    public List<UserRole> findByUserRoleId(Integer userRoleId) {
        List<UserRole> retList = new ArrayList<>();        
        for(UserRole item:itemCollection) {
            if(item.getUserRoleId().equals(userRoleId))
                retList.add(item);
        }
        return retList;
    }

    @Override
    public List<UserRole> findByDescription(String description) {
        List<UserRole> retList = new ArrayList<>();        
        for(UserRole item:itemCollection) {
            if(item.getDescription().equals(description))
                retList.add(item);
        }
        return retList;
    }

    @Override
    public UserRole findUserRoleByDescription(String description) {
        List<UserRole> retList = this.findByDescription(description);
        if((retList != null) && (retList.size() > 0))
            return retList.get(0);
        return null;
    }

    @Override
    public UserRole save(UserRole userRole) {
        itemCollection.add(userRole);
        return userRole;
    }

    @Override
    public void delete(UserRole entity) {
        itemCollection.remove(entity);
    }    
    
}
