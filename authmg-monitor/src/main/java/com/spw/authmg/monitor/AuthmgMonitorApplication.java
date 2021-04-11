package com.spw.authmg.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class AuthmgMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthmgMonitorApplication.class, args);
	}

}
