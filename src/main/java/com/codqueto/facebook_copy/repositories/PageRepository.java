package com.codqueto.facebook_copy.repositories;

import com.codqueto.facebook_copy.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageRepository extends JpaRepository<PageEntity, Long> {
}
