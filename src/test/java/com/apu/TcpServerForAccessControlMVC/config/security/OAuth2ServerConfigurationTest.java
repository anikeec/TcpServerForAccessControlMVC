package com.apu.TcpServerForAccessControlMVC.config.security;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlMVC.TestParameters;
import com.apu.TcpServerForAccessControlMVC.mock.TestUsers;
import com.apu.TcpServerForAccessControlMVC.security.SecurityMockMvcUtils;
import com.apu.TcpServerForAccessControlMVC.service.UserRoleService;
import com.apu.TcpServerForAccessControlMVC.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OAuth2ServerConfigurationTest {
    
    private final String HEADER_AUTHORIZATION = "Authorization";
    
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
//    @Ignore
    public void testGetAccessTokenAsAdminAndUser() throws Exception {
        
        SecurityMockMvcUtils.addTestUserToDatabase(userService, userRoleService, passwordEncoder);
        
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
    
    @Test
//    @Ignore
    public void givenNotAuthenticated_whenAskingCard_forbidden() throws Exception {
        mvc.perform(get("/api/user/view"))
            .andExpect(status().isUnauthorized());
    }
    
    @Test
    public void givenAuthenticatedAsOfficer_whenAskingMyDetails_detailsReturned() throws Exception {
        
        SecurityMockMvcUtils.addTestUserToDatabase(userService, userRoleService, passwordEncoder); 
        
        String accessToken = SecurityMockMvcUtils.obtainAccessToken( mvc, 
                                                TestParameters.TEST_USER_EMAIL,
                                                TestParameters.TEST_USER_PASSWORD,
                                                CLIENT_ID,
                                                CLIENT_PASSWORD);
//        when(userService.findByUserId(TestUsers.getUser().getUserId()).get(0))
//            .thenReturn(Optional.of(TestUsers.getUser()));
        ResultActions resultActions = mvc.perform(get("/api/user/view")
            .header(HEADER_AUTHORIZATION, SecurityMockMvcUtils.bearer(accessToken)))
            .andDo(MockMvcResultHandlers.print());            
        
        resultActions
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$[0].userId").exists())
            .andExpect(jsonPath("$[0].email").value(TestParameters.TEST_USER_EMAIL))
            .andExpect(jsonPath("$[0].roleUserCollection").isArray())
            .andExpect(jsonPath("$[0].roleUserCollection[0]").value("ROLE_ADMIN"))
            .andExpect(jsonPath("$[0].roleUserCollection[1]").value("ROLE_USER"))
            ;
    }
    
}
