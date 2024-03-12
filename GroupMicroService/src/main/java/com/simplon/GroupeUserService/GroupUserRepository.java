package com.simplon.GroupeUserService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupUserRepository extends JpaRepository<GroupUsers, Long> {

    List<GroupUsers> findByIdGroup(Long IdGroup);
    List<GroupUsers> findByIdUsers(Long IdUsers);
    GroupUsers findByIdGroupAndIdUsers(Long IdGroup, Long IdUsers);
}