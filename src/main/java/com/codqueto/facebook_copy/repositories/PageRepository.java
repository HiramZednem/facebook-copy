package com.codqueto.facebook_copy.repositories;

import com.codqueto.facebook_copy.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PageRepository extends JpaRepository<PageEntity, Long> {
    Optional<PageEntity> findByTitle(String title);

    //If exist return true if not return false
    Boolean existsByTitle(String title);

    @Modifying
    @Query("DELETE FROM PageEntity WHERE  title=:title")
    void deleteByTitle(String title);
}
