package com.simplon.GroupeUserService;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "groupUsers")
public class GroupUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupUser_seq")
    @SequenceGenerator(name = "groupUser_seq", sequenceName = "groupUser_seq", allocationSize = 1)
    private Long idGroupUser;

    private Long idGroup;

    private Long idUsers;
}