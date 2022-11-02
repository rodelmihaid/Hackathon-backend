package com.pocu.catalog.converter;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.web.dto.SubjectDto;
import org.springframework.stereotype.Component;

@Component
public class SubjectConverter extends BaseConverter<SubjectDto, SubjectEntity> {

    public SubjectDto fromEntityToDto(SubjectEntity subjectEntity) {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(subjectEntity.getId());
        subjectDto.setOptional(subjectEntity.getOptional());
        subjectDto.setName(subjectEntity.getName());
        subjectDto.setCreditPoints(subjectEntity.getCreditPoints());

        return subjectDto;
    }

    public SubjectEntity fromDtoToEntity(SubjectDto subjectDto) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(subjectDto.getId());
        subjectEntity.setName(subjectDto.getName());
        subjectEntity.setOptional(subjectDto.getOptional());
        subjectEntity.setCreditPoints(subjectDto.getCreditPoints());

        return subjectEntity;
    }
}
