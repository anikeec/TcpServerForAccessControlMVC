package com.apu.TcpServerForAccessControlMVC.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.apu.TcpServerForAccessControlDB.entity.User;
import com.apu.TcpServerForAccessControlMVC.service.UserService;

//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        User user = userService.findUserByEmail(email);
        if(user != null) {
            String passwordHash = user.getPassword();
            if(passwordEncoder.matches(password, passwordHash)) { 
                return new UsernamePasswordAuthenticationToken(email, password, new ArrayList<>());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
