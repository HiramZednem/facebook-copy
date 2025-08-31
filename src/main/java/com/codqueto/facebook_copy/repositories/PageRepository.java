package com.codqueto.facebook_copy.repositories;

import com.codqueto.facebook_copy.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PageRepository extends JpaRepository<PageEntity, Long> {
    Optional<PageEntity> findPageByTitle(String title);
}
