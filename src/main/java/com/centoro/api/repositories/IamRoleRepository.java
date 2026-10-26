package com.centoro.api.repositories;

import com.centoro.api.entities.IamRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IamRoleRepository extends JpaRepository<IamRole, Long> {
}