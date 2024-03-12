package com.simplon.ReactionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Page<Reaction> findByPostId(Long postId, Pageable pageable);
    Page<Reaction> findByUserId(Long userId, Pageable pageable);
    Optional<Reaction> findReactionByUserIdAndPostId(long userId,Long postId);

}
