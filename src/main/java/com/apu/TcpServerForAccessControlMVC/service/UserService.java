package com.apu.TcpServerForAccessControlMVC.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.User;
import com.apu.TcpServerForAccessControlDB.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findUserByEmail(String email) {
        List<User> userList = userRepository.findByEmail(email);
        if((userList != null) && (userList.size() > 0))
            return userList.get(0);
        return null;
    }
    
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setActive(1);
//        Role userRole = roleRepository.findByRole("ADMIN");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userRepository.save(user);
    }
    
}
