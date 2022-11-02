package com.pocu.catalog.converter;

import com.pocu.catalog.entity.BaseEntity;
import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.web.dto.BaseDto;
import com.pocu.catalog.web.dto.ProjectDto;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseConverter<D extends BaseDto, E extends BaseEntity> {

    public abstract D fromEntityToDto(E entity);

    public abstract E fromDtoToEntity(D dto);

    public List<D> fromEntitiesToDtos(List<E> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities
                .stream()
                .map(entity -> fromEntityToDto(entity))
                .collect(Collectors.toList());
    }

    public List<E> fromDtosToEntities(List<D> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }

        return dtos
                .stream()
                .map(dto -> fromDtoToEntity(dto))
                .collect(Collectors.toList());
    }

    //public abstract ProjectEntity fromDtoToEntity(ProjectDto dto);
}
