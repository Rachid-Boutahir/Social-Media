package com.simplon.ReactionService;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ReactionMapper {
    ReactionMapper INSTANCE = Mappers.getMapper(ReactionMapper.class);
    ReactionDTO toDTO(Reaction reaction);
    Reaction toEntity(ReactionDTO reactionDTO);
}

