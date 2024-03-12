package com.simplon.ReactionService;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long postId;
    @Enumerated(EnumType.STRING)
    private TypeReaction typeReaction;
    private LocalDateTime dateDeReaction;
    public TypeReaction getTypeReaction() {
        return typeReaction;
    }

}
