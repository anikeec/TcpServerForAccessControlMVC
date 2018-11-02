package com.apu.TcpServerForAccessControlMVC.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apu.TcpServerForAccessControlMVC.restcontroller.utils.SpringRestClient;

@RestController
@RequestMapping("/api/test/")
public class RestRunTestController {
    
    @Autowired
    private SpringRestClient springRestClient;
    
    @GetMapping("/user")
    public String checkViewUserRestSecurity() {
        if(springRestClient.testRestUser())
            return "Test OK";
        else
            return "Test FALSE";
    }
    
}
