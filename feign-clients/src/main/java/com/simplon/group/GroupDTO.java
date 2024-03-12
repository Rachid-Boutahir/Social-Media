package com.simplon.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class GroupDTO {

    private Long idGroup;

    private Long idAdmin;

    private String groupName;

    private TypePrivacy Privacy;

    private String groupDescription;

    private LocalDateTime groupDateCreation;

}
