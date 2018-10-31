package com.apu.TcpServerForAccessControlMVC.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;

@Service
@Profile("test")
public class UserServiceMock extends UserService {
    
    List<SystemUser> userCollection = new ArrayList<>();
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<SystemUser> findAll() {
        throw new UnsupportedOperationException("Not implemented, yet");
    }
    
    public List<SystemUser> findById(Integer userId) {
        List<SystemUser> userList = new ArrayList<>();        
        for(SystemUser user:userCollection) {
            if(user.getUserId().equals(userId))
                userList.add(user);
        }
        return userList;
    }
    
    public List<SystemUser> findByActive(Boolean active) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }
    
    public List<SystemUser> findByEmail(String email) {        
        List<SystemUser> userList = new ArrayList<>();        
        for(SystemUser user:userCollection) {
            if(user.getEmail().equals(email))
                userList.add(user);
        }
        return userList;
    }
    
    public SystemUser findUserByEmail(String email) {
        List<SystemUser> userList = this.findByEmail(email);
        if((userList != null) && (userList.size() > 0))
            return userList.get(0);
        return null;
    }
    
    public void saveUser(SystemUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.save(user);
    }
    
    public SystemUser save(SystemUser entity) {
        userCollection.add(entity);
        return entity;
    }
    
    public void delete(SystemUser entity) {
        userCollection.remove(entity);
    }

    @Override
    public Page<SystemUser> findAll(Pageable pageable) {
        Page<SystemUser> page = 
                new PageImpl<>(userCollection, pageable, userCollection.size());
        return page;
    }

    @Override
    public List<SystemUser> findAllByPage(Integer page) {
        throw new UnsupportedOperationException("Not implemented, yet");
    }
    
    
    
}
