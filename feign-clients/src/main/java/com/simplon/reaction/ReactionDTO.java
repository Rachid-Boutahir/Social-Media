package com.simplon.reaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReactionDTO {
    private Long id;
    private Long userId;
    private Long postId;
    private TypeReaction typeReaction;
    private LocalDateTime dateDeReaction;
}
