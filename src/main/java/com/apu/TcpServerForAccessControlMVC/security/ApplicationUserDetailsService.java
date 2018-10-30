package com.apu.TcpServerForAccessControlMVC.security;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
    private final SystemUserRepository userRepository;
    
    @Autowired
    public ApplicationUserDetailsService(SystemUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        List<SystemUser> userList = userRepository.findByEmail(username);
        User user = null;
        if((userList != null)&&(userList.size() > 0)) {
            SystemUser sUser = userList.get(0);
            user = new User();
            user.setEmail(sUser.getEmail());
            user.setFirstName(sUser.getFirstName());
            user.setSecondName(sUser.getSecondName());
            Set<UserRole> roles = new LinkedHashSet<>();
            roles.add(UserRole.ADMIN);
            roles.add(UserRole.CAPTAIN);
            user.setRoles(roles);
        } else {
            throw new UsernameNotFoundException("User with email %s could not be found - " + username);
        }
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
//        String.format("User with email %s could not be found", username)));
        return new ApplicationUserDetails(user);
    }
    
}
