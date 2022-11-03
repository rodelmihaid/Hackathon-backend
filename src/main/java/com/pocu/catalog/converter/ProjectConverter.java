package com.pocu.catalog.converter;

import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.web.dto.ProjectDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter extends BaseConverter<ProjectDto, ProjectEntity> {

    public ProjectDto fromEntityToDto(ProjectEntity projectEntity) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setId(projectEntity.getId());
        projectDto.setTitle(projectEntity.getTitle());
        projectDto.setDescription(projectEntity.getDescription());
        projectDto.setAttachment(projectEntity.getAttachment());
        projectDto.setDeadline(projectEntity.getDeadline());

        return projectDto;
    }

    public ProjectEntity fromDtoToEntity(ProjectDto projectDto) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(projectDto.getId());
        projectEntity.setTitle(projectDto.getTitle());
        projectEntity.setDescription(projectDto.getDescription());
        projectEntity.setAttachment(projectDto.getAttachment());
        projectEntity.setDeadline(projectDto.getDeadline());

        return projectEntity;
    }
}

