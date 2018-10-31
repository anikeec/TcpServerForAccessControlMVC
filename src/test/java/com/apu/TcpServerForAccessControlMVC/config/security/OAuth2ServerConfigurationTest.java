package com.apu.TcpServerForAccessControlMVC.config.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlMVC.TestParameters;
import com.apu.TcpServerForAccessControlMVC.service.UserRoleService;
import com.apu.TcpServerForAccessControlMVC.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OAuth2ServerConfigurationTest {
    
    @Value("${accesscontrol-security.mobile-app-client-id}")
    private String CLIENT_ID;
    
    @Value("${accesscontrol-security.mobile-app-client-secret}")
    private String CLIENT_PASSWORD;

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    public void testGetAccessTokenAsAdminAndUser() throws Exception {
        
        UserRole userRoleAdmin = userRoleService.findUserRoleByDescription("ROLE_ADMIN");
        UserRole userRoleUser = userRoleService.findUserRoleByDescription("ROLE_USER");
        
        List<UserRole> userRoleList = new ArrayList<>();
        userRoleList.add(userRoleAdmin);
        userRoleList.add(userRoleUser);
        
        SystemUser admin = new SystemUser();
        admin.setFirstName(TestParameters.TEST_USER_FIRST_NAME);
        admin.setEmail(TestParameters.TEST_USER_EMAIL);
        admin.setPassword(passwordEncoder.encode(TestParameters.TEST_USER_PASSWORD));
        admin.setActive(true);
        admin.setPhoneNumber(TestParameters.TEST_USER_PHONE_NUMBER);
        admin.setUserRoleCollection(userRoleList);
        userService.save(admin);
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_PASSWORD);
        params.add("username", TestParameters.TEST_USER_EMAIL);
        params.add("password", TestParameters.TEST_USER_PASSWORD);
        
        mvc.perform(post("/oauth/token")
            .params(params)
            .with(httpBasic(CLIENT_ID, CLIENT_PASSWORD))
            .accept("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andDo(print())
            .andExpect(jsonPath("access_token").isString())
            .andExpect(jsonPath("token_type").value("bearer"))
            .andExpect(jsonPath("refresh_token").isString())
            .andExpect(jsonPath("expires_in").isNumber())
            .andExpect(jsonPath("scope").value("mobile_app"));
    }
    
}
