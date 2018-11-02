package com.apu.TcpServerForAccessControlMVC.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlMVC.TestParameters;
import com.apu.TcpServerForAccessControlMVC.mock.TestUsers;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcUserRoleService;
import com.apu.TcpServerForAccessControlMVC.service.i.MvcUserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ApplicationUserDetailsServiceTest {
    
    @Autowired
    private MvcUserService userService;
    
    @Autowired
    private MvcUserRoleService userRoleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
//    @Ignore
    public void givenExistingUsername_whenLoadingUser_userIsReturned() {
        SecurityMockMvcUtils.addTestUserToDatabase(userService, userRoleService, passwordEncoder);
        
        ApplicationUserDetailsService service = new ApplicationUserDetailsService(userService, userRoleService);
        
        List<SystemUser> users = userService.findByEmail(TestParameters.TEST_USER_EMAIL);        
        Assert.notEmpty(users, "Error. No one user is exist.");
        Assert.isTrue(users.get(0).equals(TestUsers.getUser()), "Error. No one user is exist.");
        
        UserDetails userDetails = service.loadUserByUsername(TestParameters.TEST_USER_EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(TestParameters.TEST_USER_EMAIL);
        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority)
        .contains("ROLE_USER"); 
        assertThat(userDetails).isInstanceOfSatisfying(ApplicationUserDetails.class,
        applicationUserDetails -> {
            assertThat(applicationUserDetails.getUserId())
            .isEqualTo(TestUsers.getUser().getUserId());
        });
    }

    
}
