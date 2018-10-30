package com.apu.TcpServerForAccessControlMVC.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUserDetails extends org.springframework.security.core.userdetails.User {
    
    private static final String ROLE_PREFIX = "ROLE_";
//    private final UserId userId;
    private final User user;
    
    public ApplicationUserDetails(User user) {//User
        super(user.getEmail(), user.getPassword(), createAuthorities(user.getRoles()));
        this.user = user;
    }
    
//    public UserId getUserId() {
//        return userId;
//    }
    
   
    private static Collection<SimpleGrantedAuthority> createAuthorities(Set<UserRole> roles) {
        return roles.stream()
                .map(userRole -> new SimpleGrantedAuthority(ROLE_PREFIX + userRole
                .name()))
                .collect(Collectors.toSet());
    }
}
