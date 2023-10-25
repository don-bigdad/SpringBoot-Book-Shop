package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Role;
import com.example.springbootbookshop.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(RoleName name);
}
