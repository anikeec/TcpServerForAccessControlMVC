package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;

@Service
public class UserService {

    @Autowired
    private SystemUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }
    
    public List<SystemUser> findByUserId(Integer userId) {
        return userRepository.findByUserId(userId);
    }
    
    public List<SystemUser> findByActive(Boolean active) {
        return userRepository.findByActive(active);
    }
    
    public List<SystemUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public SystemUser findUserByEmail(String email) {
        List<SystemUser> userList = userRepository.findByEmail(email);
        if((userList != null) && (userList.size() > 0))
            return userList.get(0);
        return null;
    }
    
    public void saveUser(SystemUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    
    public <S extends SystemUser> S save(S entity) {
        return userRepository.save(entity);
    }
    
    public void delete(SystemUser entity) {
        userRepository.delete(entity);
    }
    
}
