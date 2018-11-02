package com.apu.TcpServerForAccessControlMVC.mock;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlMVC.TestParameters;

public class TestUsers {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    private static final SystemUser USER_TEST = new SystemUser(); 
    
    public static SystemUser getUser() {
        Set<UserRole> userRoleCollection = new HashSet<>();
        userRoleCollection.add(new UserRole("ROLE_USER"));        
        
        USER_TEST.setUserId(TestParameters.TEST_USER_ID);
        USER_TEST.setFirstName(TestParameters.TEST_USER_FIRST_NAME);
        USER_TEST.setPhoneNumber(TestParameters.TEST_USER_PHONE_NUMBER);
        USER_TEST.setEmail(TestParameters.TEST_USER_EMAIL);
        USER_TEST.setPassword(passwordEncoder.encode(TestParameters.TEST_USER_PASSWORD));
        USER_TEST.setActive(TestParameters.TEST_USER_ACTIVE);
        USER_TEST.setUserRoleCollection(userRoleCollection);
        
        return USER_TEST;
    }
    
}
