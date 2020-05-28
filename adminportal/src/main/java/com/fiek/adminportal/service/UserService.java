package com.fiek.adminportal.service;

import com.fiek.adminportal.domain.User;
import com.fiek.adminportal.domain.security.PasswordResetToken;
import com.fiek.adminportal.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    User createUser(User user, Set<UserRole> userRoles) throws Exception;
    User save(User user);
}
