package com.fiek.adminportal;

import com.fiek.adminportal.domain.User;
import com.fiek.adminportal.domain.security.Role;
import com.fiek.adminportal.domain.security.UserRole;
import com.fiek.adminportal.service.UserService;
import com.fiek.adminportal.utility.SecurityUtility;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
public class AdminportalApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(AdminportalApplication.class, args);
	}

//	@Bean(initMethod = "start", destroyMethod = "stop")
//	public Server h2Server() throws SQLException {
//		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
//	}

	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
		cookieSerializer.setSameSite("None");
		cookieSerializer.setUseSecureCookie(true);
		return cookieSerializer;
	}

	@Override
	public void run(String... args) throws Exception {

		User user1 = new User();

		user1.setUsername("admin");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("admin"));
		user1.setEmail("admin@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(0);
		role1.setName("ROLE_ADMIN");
		userRoles.add(new UserRole(user1,role1));

		userService.createUser(user1,userRoles);


//		File src = new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\travelGuide\\src\\main\\resources\\static\\images\\location");
//		File dest = new File("C:\\Users\\TECHCOM\\git\\Projects\\KosovoTravel&Booking\\adminportal\\src\\main\\resources\\static\\images\\location");
//		FileSystemUtils.copyRecursively(src, dest);
	}
}
