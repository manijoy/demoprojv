package com.wipro.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wipro.restapi.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
