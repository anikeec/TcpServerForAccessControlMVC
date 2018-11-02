package com.apu.TcpServerForAccessControlMVC.security;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.apu.TcpServerForAccessControlDB.entity.SystemUser;
import com.apu.TcpServerForAccessControlDB.entity.UserRole;
import com.apu.TcpServerForAccessControlMVC.TestParameters;
import com.apu.TcpServerForAccessControlMVC.service.UserRoleService;
import com.apu.TcpServerForAccessControlMVC.service.UserService;

public class SecurityMockMvcUtils {
    
    public static void addTestUserToDatabase(UserService userService, 
                                                UserRoleService userRoleService, 
                                                PasswordEncoder passwordEncoder) {
        UserRole userRoleAdmin = userRoleService.findUserRoleByDescription("ROLE_ADMIN");
        UserRole userRoleUser = userRoleService.findUserRoleByDescription("ROLE_USER");
        
        Set<UserRole> userRoleList = new HashSet<>();
        userRoleList.add(userRoleAdmin);
        userRoleList.add(userRoleUser);
        
        SystemUser admin = new SystemUser();
        admin.setUserId(TestParameters.TEST_USER_ID);
        admin.setFirstName(TestParameters.TEST_USER_FIRST_NAME);
        admin.setEmail(TestParameters.TEST_USER_EMAIL);
        admin.setPassword(passwordEncoder.encode(TestParameters.TEST_USER_PASSWORD));
        admin.setActive(true);
        admin.setPhoneNumber(TestParameters.TEST_USER_PHONE_NUMBER);
        admin.setUserRoleCollection(userRoleList);
        userService.save(admin);   
    }
    
    /**
    * Allows to get an access token for the given user in the context of a spring (unit) test
    * using MockMVC.
    *
    * @param mvc the MockMvc instance
    * @param username the username
    * @param password the password
    * @return the <code>access_token</code> to be used in the <code>Authorization</code> header
    * @throws Exception if no token could be obtained.
    */
    public static String obtainAccessToken(MockMvc mvc, 
                                            String username, 
                                            String password,
                                            String clientId, 
                                            String clientPassword) throws Exception {
        
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("client_secret", clientPassword);
        params.add("username", username);
        params.add("password", password);
        
        ResultActions result
            = mvc.perform(post("/oauth/token")
                    .params(params)
                    .with(httpBasic(clientId, clientPassword))
                    .accept("application/json;charset=UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"));
        
        String resultString = result.andReturn().getResponse().getContentAsString();
        
        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
    
    public static String bearer(String accessToken) {
        return "Bearer " + accessToken;
    }
    
}