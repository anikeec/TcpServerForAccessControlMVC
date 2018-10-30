package com.apu.TcpServerForAccessControlMVC.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
@ComponentScan({"com.apu.TcpServerForAccessControlDB","com.apu.TcpServerForAccessControlMVC"})
public class AppConfigTest {
    
}
