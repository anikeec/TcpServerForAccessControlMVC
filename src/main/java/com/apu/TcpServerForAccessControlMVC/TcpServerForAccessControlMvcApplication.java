package com.apu.TcpServerForAccessControlMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
@EntityScan(basePackages = {"com.apu.TcpServerForAccessControlDB.entity"}) 
@ImportResource("/META-INF/spring/appConfig.xml")
@PropertySource("application.properties")
public class TcpServerForAccessControlMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TcpServerForAccessControlMvcApplication.class, args);
	}
}
