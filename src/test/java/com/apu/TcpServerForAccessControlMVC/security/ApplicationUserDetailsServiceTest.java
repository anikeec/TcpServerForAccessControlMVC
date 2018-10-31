package com.apu.TcpServerForAccessControlMVC.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.repository.SystemUserRepository;
import com.apu.TcpServerForAccessControlMVC.TestParameters;
import com.apu.TcpServerForAccessControlMVC.mock.TestUsers;
import com.apu.TcpServerForAccessControlMVC.repository.SystemUserRepositoryMock;

public class ApplicationUserDetailsServiceTest {
    
//    @Test
//    public void givenExistingUsername_whenLoadingUser_userIsReturned() {
//        SystemUserRepository repository = mock(SystemUserRepositoryMock.class);
//        ApplicationUserDetailsService service = new ApplicationUserDetailsService(repository);
//        when(repository.findByEmail(TestParameters.TEST_USER_EMAIL))
//        .thenReturn(Optional.of(TestUsers.getUser()));
//        
//        UserDetails userDetails = service.loadUserByUsername(TestParameters.TEST_USER_EMAIL);
//        assertThat(userDetails).isNotNull();
//        assertThat(userDetails.getUsername()).isEqualTo(TestParameters.TEST_USER_EMAIL);
//        assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority)
//        .contains("ROLE_USER"); 
//        assertThat(userDetails).isInstanceOfSatisfying(ApplicationUserDetails.class,
//        applicationUserDetails -> {
//            assertThat(applicationUserDetails.getUserId())
//            .isEqualTo(TestUsers.getUser().getUserId());
//        });
//    }

    
}
