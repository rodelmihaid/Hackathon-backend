package com.pocu.catalog.converter;

import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.exception.MethodNotSupportedException;
import com.pocu.catalog.web.dto.TeacherBasicDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherBasicDetailsConverter extends BaseConverter<TeacherBasicDetailsDto, TeacherEntity> {

    private final SubjectConverter subjectConverter;

    @Autowired
    public TeacherBasicDetailsConverter(SubjectConverter subjectConverter) {
        this.subjectConverter = subjectConverter;
    }

    @Override
    public TeacherBasicDetailsDto fromEntityToDto(TeacherEntity entity) {
        TeacherBasicDetailsDto detailsDto = new TeacherBasicDetailsDto();
        detailsDto.setId(entity.getId());
        detailsDto.setFirstName(entity.getFirstName());
        detailsDto.setLastName(entity.getLastName());
        detailsDto.setSubjects(subjectConverter.fromEntitiesToDtos(entity.getSubjects()));

        return detailsDto;
    }

    @Override
    public TeacherEntity fromDtoToEntity(TeacherBasicDetailsDto dto) {
        throw new MethodNotSupportedException("Cannot convert from dto to entity for teacher basic dto", "METHOD_NOT_SUPPORTED");
    }
}
