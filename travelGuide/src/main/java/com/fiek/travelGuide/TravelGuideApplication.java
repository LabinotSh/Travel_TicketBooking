package com.fiek.travelGuide;

import com.fiek.travelGuide.domain.User;
import com.fiek.travelGuide.domain.security.Role;
import com.fiek.travelGuide.domain.security.UserRole;
import com.fiek.travelGuide.service.UserService;
import com.fiek.travelGuide.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class TravelGuideApplication  implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TravelGuideApplication.class, args);
	}

	@Override
	public void run(String ...args) throws Exception{
		User user1 = new User();
		user1.setFirstName("Labinot");
		user1.setLastName("Sherifi");
		user1.setUsername("labinot");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("willsherifi@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1,role1));

		userService.createUser(user1,userRoles);
	}

}
