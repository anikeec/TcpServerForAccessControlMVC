package com.apu.TcpServerForAccessControlMVC.restcontroller.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class SpringRestClient {
    
    private String REST_SERVICE_URI = "http://localhost:8090/api/";
    private String AUTH_SERVER_URI = "http://localhost:8090/oauth/token"; 
    private String QPM_ACCESS_TOKEN = "?access_token=";
    private String TEST_USER_EMAIL = "admin@gmail.com";
    private String TEST_USER_PASSWORD = "admin_password";
    
    @Value("${accesscontrol-security.mobile-app-client-id}")
    private String CLIENT_ID;
    
    @Value("${accesscontrol-security.mobile-app-client-secret}")
    private String CLIENT_PASSWORD;


    /*
     * Prepare HTTP Headers.
     */
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send
     * client-credentials.
     */
    private HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = CLIENT_ID + ":" + CLIENT_PASSWORD;
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then
     * be send with each request.
     */
//    @SuppressWarnings({ "unchecked" })
    private AuthTokenInfo sendTokenRequest() {
        
        String qpmPasswordGrant = "?grant_type=password&username=" 
                                        + TEST_USER_EMAIL 
                                        + "&password=" 
                                        + TEST_USER_PASSWORD;
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());

        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        
        LinkedHashMap<String, Object> map = null;
        try {
            ResponseEntity<Object> response = 
                    restTemplate.exchange(AUTH_SERVER_URI + qpmPasswordGrant, 
                                           HttpMethod.POST, request, Object.class);        
            map = (LinkedHashMap<String, Object>)response.getBody();
        } catch(RestClientException e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        
        AuthTokenInfo tokenInfo = null;
        if (map != null) {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String) map.get("access_token"));
            tokenInfo.setToken_type((String) map.get("token_type"));
            tokenInfo.setRefresh_token((String) map.get("refresh_token"));
            tokenInfo.setExpires_in((int) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
            System.out.println(tokenInfo);
        } else {
            System.out.println("Error. Test user is not exist.");

        }
        return tokenInfo;
    }
    
    /*
     * Send a GET request to get list of all users.
     */
//    @SuppressWarnings({ "unchecked", "rawtypes" })
    private boolean listAllUsers(AuthTokenInfo tokenInfo){
        if(tokenInfo == null)   return false;
//        Assert.notNull(tokenInfo, "Error. Authenticate first please.");
 
        System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        
        List<LinkedHashMap<String, Object>> usersMap = null;
        try {
            ResponseEntity<List> response = 
                    restTemplate.exchange(REST_SERVICE_URI 
                                            + "/user/view" 
                                            + QPM_ACCESS_TOKEN 
                                            + tokenInfo.getAccess_token(),
                                            HttpMethod.GET, request, List.class);
            usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
        } catch(RestClientException e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
        }
        
        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : "
                                    + "id=" + map.get("userId")
                                    + ", name=" + map.get("firstName")
                                    + ", email=" + map.get("email"));
            }
            return true;
        } else {
            System.out.println("Error. Test user is not exist.");
        }
        return false;
    }
    
    public boolean testRestUser() {        
        AuthTokenInfo tokenInfo = sendTokenRequest();
        return listAllUsers(tokenInfo);        
    }

}
