package com.pocu.catalog.converter;

import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.web.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherConverter extends BaseConverter<TeacherDto, TeacherEntity> {

    private final SubjectConverter subjectConverter;

    @Autowired
    public TeacherConverter(SubjectConverter subjectConverter) {
        this.subjectConverter = subjectConverter;
    }


    @Override
    public TeacherDto fromEntityToDto(TeacherEntity entity) {
        TeacherDto dto = new TeacherDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDateOfBirth(entity.getBirthDate());
        dto.setSubjects(subjectConverter.fromEntitiesToDtos(entity.getSubjects()));
        dto.setCnp(entity.getCnp());
        dto.setSalary(entity.getSalary());
        return dto;
    }

    @Override
    public TeacherEntity fromDtoToEntity(TeacherDto dto) {
        TeacherEntity entity = new TeacherEntity();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCnp(dto.getCnp());
        entity.setBirthDate(dto.getDateOfBirth());
        entity.setSubjects(subjectConverter.fromDtosToEntities(dto.getSubjects()));
        entity.setSalary(dto.getSalary());
        return entity;
    }
}
