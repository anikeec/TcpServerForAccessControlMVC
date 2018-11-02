package com.apu.TcpServerForAccessControlMVC.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcUserRoleService;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcUserService;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    
    private final MvcUserService userService;
    
    private final MvcUserRoleService userRoleService;
    
    @Autowired
    public ApplicationUserDetailsService(MvcUserService userService, MvcUserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) {
        List<SystemUser> userList = userService.findByEmail(username);
        SystemUser user = null;
        if((userList != null)&&(userList.size() > 0)) {
            SystemUser sUser = userList.get(0);
            user = new SystemUser();
            user.setUserId(sUser.getUserId());
            user.setFirstName(sUser.getFirstName());
            user.setSecondName(sUser.getSecondName());
            user.setEmail(sUser.getEmail());            
            user.setPassword(sUser.getPassword());
            user.setPhoneNumber(sUser.getPhoneNumber());
            user.setActive(sUser.getActive());            
            user.setCardCollection(sUser.getCardCollection());
            user.setUserRoleCollection(sUser.getUserRoleCollection());
        } else {
            throw new UsernameNotFoundException("User with email %s could not be found - " + username);
        }
//        User user = userService.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(
//        String.format("User with email %s could not be found", username)));
        return new ApplicationUserDetails(user);
    }
    
}
