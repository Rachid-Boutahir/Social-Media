package com.simplon.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class GroupUserDTO {

    private Long idGroupUser;

    private Long idGroup;

    private Long idUsers;

}
