package com.codqueto.facebook_copy.repositories;

import com.codqueto.facebook_copy.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByMail(String mail);
}
