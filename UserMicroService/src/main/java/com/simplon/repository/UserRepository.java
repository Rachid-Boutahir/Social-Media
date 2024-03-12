package com.simplon.repository;

import com.simplon.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository interface to manage user data.
 *
 * @Author Ayoub Ait Si Ahmad
 * @Date 2024-02-28
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);

    Page<UserEntity> findAll(Pageable pageable);
}
