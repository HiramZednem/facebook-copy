package com.codqueto.facebook_copy.repositories;

import com.codqueto.facebook_copy.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
