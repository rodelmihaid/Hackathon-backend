package com.pocu.catalog.converter;

import com.pocu.catalog.entity.ProjectStudentEntity;
import com.pocu.catalog.web.dto.ProjectStudentDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectStudentConverter extends BaseConverter<ProjectStudentDto, ProjectStudentEntity> {


    @Override
    public ProjectStudentDto fromEntityToDto(ProjectStudentEntity entity) {
        ProjectStudentDto dto = new ProjectStudentDto();
       if (entity.getProject() != null) {
           dto.setProjectId(entity.getProject().getId());
       }

        if (entity.getStudent() != null) {
            dto.setUserEmail(entity.getStudent().getEmail());
        }

        dto.setId(entity.getId());
        dto.setAttachment(entity.getAttachment());
        dto.setDescription(entity.getDescription());
        dto.setSolution(entity.getSolution());
        return dto;

    }


    @Override
    public ProjectStudentEntity fromDtoToEntity(ProjectStudentDto dto) {
        ProjectStudentEntity entity = new ProjectStudentEntity();
        entity.setId(dto.getId());
        entity.setDescription(dto.getDescription());
        entity.setAttachment(dto.getAttachment());
        entity.setSolution(dto.getSolution());
        return entity;
    }

}
