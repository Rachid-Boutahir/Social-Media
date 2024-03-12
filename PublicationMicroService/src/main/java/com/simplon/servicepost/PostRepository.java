package com.simplon.servicepost;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByDeletedFalse();
    List<Post> findByUserIdAndDeletedFalse(long userId);

    List<Post> findByGroupIdAndDeletedFalse(long groupId);
}
