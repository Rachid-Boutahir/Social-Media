package com.simplon.NotificationService;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel="spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);
    NotificationDTO toDTO(Notification e);
    Notification toEntity(NotificationDTO o);
}
