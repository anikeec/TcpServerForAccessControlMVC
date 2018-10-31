package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcFullService;

@Service
@Profile("dev")
public class UserService implements MvcFullService<SystemUser> {

    @Autowired
    private SystemUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<SystemUser> findAll() {
        return userRepository.findAll();
    }
    
    public List<SystemUser> findById(Integer userId) {
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
    
    @Override
    public SystemUser save(SystemUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }
    
    public void delete(SystemUser entity) {
        userRepository.delete(entity);
    }

    @Override
    public Page<SystemUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<SystemUser> findAllByPage(Integer page) {
        throw new UnsupportedOperationException("Method has not supported yet!");
    }
    
}
