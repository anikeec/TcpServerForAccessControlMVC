package com.apu.TcpServerForAccessControlMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
@EntityScan(basePackages = {"com.apu.TcpServerForAccessControlDB.entity"})  
public class TcpServerForAccessControlMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpServerForAccessControlMvcApplication.class, args);
	}
}
