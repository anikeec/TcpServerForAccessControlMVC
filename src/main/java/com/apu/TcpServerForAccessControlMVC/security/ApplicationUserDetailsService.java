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
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;
import com.apu.TcpServerForAccessControlMVC.service.UserRoleService;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
    private final SystemUserRepository userRepository;
    
    private final UserRoleService userRoleService;
    
    @Autowired
    public ApplicationUserDetailsService(SystemUserRepository userRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        List<SystemUser> userList = userRepository.findByEmail(username);
        SystemUser user = null;
        if((userList != null)&&(userList.size() > 0)) {
            SystemUser sUser = userList.get(0);
            user = new SystemUser();
            user.setEmail(sUser.getEmail());
            user.setFirstName(sUser.getFirstName());
            user.setSecondName(sUser.getSecondName());
            Set<UserRole> roles = new LinkedHashSet<>();
            
            UserRole urAdmin = userRoleService.findUserRoleByDescription("ROLE_ADMIN");
            UserRole urUser = userRoleService.findUserRoleByDescription("ROLE_USER");
            
            roles.add(urAdmin);
            roles.add(urUser);
            user.setUserRoleCollection(roles);
        } else {
            throw new UsernameNotFoundException("User with email %s could not be found - " + username);
        }
//        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
//        String.format("User with email %s could not be found", username)));
        return new ApplicationUserDetails(user);
    }
    
}
