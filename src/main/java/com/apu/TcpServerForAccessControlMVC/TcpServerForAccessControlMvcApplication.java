package com.apu.TcpServerForAccessControlMVC;

import java.io.PrintStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.apu.TcpServerForAccessControlMVC.logging.LoggingOutputStream;

@SpringBootApplication
@EnableJpaRepositories(basePackages= {"com.apu.TcpServerForAccessControlDB.repository"})
@EntityScan(basePackages = {"com.apu.TcpServerForAccessControlDB.entity"}) 
//@ImportResource("/META-INF/spring/appConfig.xml")
//@PropertySource("application.properties")
public class TcpServerForAccessControlMvcApplication {
    
    private static final Logger logger = LogManager.getLogger(TcpServerForAccessControlMvcApplication.class);

	public static void main(String[] args) {
	    System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
	    System.setOut(new PrintStream(new LoggingOutputStream(LogManager.getLogger("sysoutLog"), Level.ALL), true));
		SpringApplication.run(TcpServerForAccessControlMvcApplication.class, args);
	}
}
