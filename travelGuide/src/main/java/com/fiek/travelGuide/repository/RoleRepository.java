package com.fiek.travelGuide.repository;

import com.fiek.travelGuide.domain.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
