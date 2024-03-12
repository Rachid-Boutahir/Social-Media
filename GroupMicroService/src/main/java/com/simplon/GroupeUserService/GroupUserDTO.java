package com.simplon.GroupeUserService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class GroupUserDTO {

    private Long idGroupUser;

    private Long idGroup;

    private Long idUsers;

}
