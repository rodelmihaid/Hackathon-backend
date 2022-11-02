package com.pocu.catalog.converter;

import com.pocu.catalog.entity.User;
import com.pocu.catalog.web.dto.StudentDto;
import com.pocu.catalog.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends BaseConverter<UserDto , User > {

    private final SubjectConverter subjectConverter;

    @Autowired
    public UserConverter(SubjectConverter subjectConverter) {
        this.subjectConverter = subjectConverter;
    }

    @Override
    public UserDto fromEntityToDto(User entity) {
        UserDto studentDto = new UserDto();
        studentDto.setId(entity.getId());
        studentDto.setFirstName(entity.getFirstName());
        studentDto.setLastName(entity.getLastName());
        studentDto.setEnrolledSubjects(subjectConverter.fromEntitiesToDtos(entity.getEnrolledSubjects()));
        studentDto.setEmail(entity.getEmail());
        studentDto.setUniversity(entity.getUniversity());
        studentDto.setFaculty(entity.getFaculty());
        studentDto.setRegistrationDate(entity.getRegistrationDate());
        studentDto.setAuthorities(entity.getAuthorities());
        studentDto.setProfileImage(entity.getProfileImage());

        return studentDto;
    }

    @Override
    public User fromDtoToEntity(UserDto dto) {
        User studentEntity = new User();
        studentEntity.setId(dto.getId());
        studentEntity.setFirstName(dto.getFirstName());
        studentEntity.setLastName(dto.getLastName());
        studentEntity.setEnrolledSubjects(subjectConverter.fromDtosToEntities(dto.getEnrolledSubjects()));
        studentEntity.setEmail(dto.getEmail());
        studentEntity.setUniversity(dto.getUniversity());
        studentEntity.setFaculty(dto.getFaculty());
        studentEntity.setRegistrationDate(dto.getRegistrationDate());
        studentEntity.setAuthorities(dto.getAuthorities());
        studentEntity.setProfileImage(dto.getProfileImage());

        return studentEntity;
    }

//    @Override
//    public ProjectEntity fromDtoToEntity(ProjectDto dto) {
//        return null;
//    }
}
