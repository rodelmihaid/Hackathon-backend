package com.pocu.catalog.converter;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.web.dto.SubjectDto;
import com.pocu.catalog.web.dto.TeacherDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.pocu.catalog.util.TestConstants.*;

@RunWith(MockitoJUnitRunner.class)
public class TeacherConverterTest {

    @InjectMocks
    private TeacherConverter teacherConverter;

    @Mock
    private SubjectConverter subjectConverter;

    @Test
    public void fromEntityToDto() {
        Mockito.when(subjectConverter.fromEntitiesToDtos(Mockito.any())).thenReturn(getMockSubject());

        TeacherDto teacherDto = teacherConverter.fromEntityToDto(getMockEntity());

        Assert.assertNotNull(teacherDto);
        Assert.assertEquals(ID, teacherDto.getId());
        Assert.assertEquals(FIRST_NAME, teacherDto.getFirstName());
        Assert.assertEquals(LAST_NAME, teacherDto.getLastName());
        Assert.assertEquals(CNP, teacherDto.getCnp());
        Assert.assertEquals(SALARY, teacherDto.getSalary());

        List<SubjectDto> subjects = teacherDto.getSubjects();
        Assert.assertNotNull(subjects);
        Assert.assertEquals(1, subjects.size());
        Assert.assertEquals(SUBJECT_ID, subjects.get(0).getId());

    }

    @Test
    public void fromDtoToEntity() {
        Mockito.when(subjectConverter.fromDtosToEntities(Mockito.any())).thenReturn(getMockSubjectEntity());

        TeacherEntity teacherEntity = teacherConverter.fromDtoToEntity(getMockDto());

        Assert.assertNotNull(teacherEntity);
        Assert.assertEquals(ID, teacherEntity.getId());
        Assert.assertEquals(FIRST_NAME, teacherEntity.getFirstName());
        Assert.assertEquals(LAST_NAME, teacherEntity.getLastName());
        Assert.assertEquals(CNP, teacherEntity.getCnp());
        Assert.assertEquals(SALARY, teacherEntity.getSalary());

        List<SubjectEntity> subjects = teacherEntity.getSubjects();
        Assert.assertNotNull(subjects);
        Assert.assertEquals(1, subjects.size());
        Assert.assertEquals(SUBJECT_ID, subjects.get(0).getId());
    }



    private List<SubjectDto> getMockSubject() {
        SubjectDto subjectDto = new SubjectDto();
        subjectDto.setId(SUBJECT_ID);


        return Arrays.asList(subjectDto);
    }

    private List<SubjectEntity> getMockSubjectEntity() {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(SUBJECT_ID);

        return Collections.singletonList(subjectEntity);
    }

    private TeacherEntity getMockEntity() {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setFirstName(FIRST_NAME);
        teacherEntity.setLastName(LAST_NAME);
        teacherEntity.setCnp(CNP);
        teacherEntity.setSalary(SALARY);
        teacherEntity.setId(ID);

        return teacherEntity;
    }

    private TeacherDto getMockDto() {
        TeacherDto dto = new TeacherDto();
        dto.setFirstName(FIRST_NAME);
        dto.setLastName(LAST_NAME);
        dto.setSalary(SALARY);
        dto.setCnp(CNP);
        dto.setId(ID);

        return dto;
    }
}
