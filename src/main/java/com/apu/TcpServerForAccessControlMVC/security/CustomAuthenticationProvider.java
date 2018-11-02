package com.apu.TcpServerForAccessControlMVC.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlMVC.service.UserService;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcUserService;

//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MvcUserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        Authentication result = null;
        
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        SystemUser user = userService.findUserByEmail(email);
        if(user != null) {
            String passwordHash = user.getPassword();
            if(passwordEncoder.matches(password, passwordHash)) { 
                result = new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            }
        }
        
        if (result == null || !result.isAuthenticated()) {
            throw new AccessDeniedException("User with such username and password has not found.");
        }
        
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
