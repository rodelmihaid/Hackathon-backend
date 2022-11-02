package com.pocu.catalog.service;

import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.exception.TeacherNotFoundException;
import com.pocu.catalog.repository.TeacherRepository;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pocu.catalog.util.TestConstants.ID;
import static com.pocu.catalog.util.TestConstants.FIRST_NAME;
import static com.pocu.catalog.util.TestConstants.LAST_NAME;

@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private SubjectService subjectService;

    private static List<TeacherEntity> mockTeachers;

    @BeforeClass
    public static void setup() {
        mockTeachers = new ArrayList<>();
        mockTeachers.add(getMockTeacher());
        mockTeachers.add(getMockTeacher());
        mockTeachers.add(getMockTeacher());
    }

    @Test
    public void getTeacher_Found() {
        Mockito.when(teacherRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(getMockTeacher()));

        TeacherEntity teacher = teacherService.getTeacher(ID);

        Assert.assertNotNull(teacher);
        Assert.assertEquals(ID, teacher.getId());
        Assert.assertEquals(FIRST_NAME, teacher.getFirstName());
        Assert.assertEquals(LAST_NAME, teacher.getLastName());
        Mockito.verify(teacherRepository, Mockito.times(1)).findById(ID);
    }

    @Test(expected = TeacherNotFoundException.class)
    public void getTeacher_NotFound() {
        Mockito.when(teacherRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        TeacherEntity teacherEntity = teacherService.getTeacher(ID);
    }

    @Test
    public void getTeacherByFirstNameAndLastName() {
        Mockito.when(teacherRepository.findByFirstNameAndLastName(Mockito.eq(FIRST_NAME), Mockito.eq(LAST_NAME)))
                .thenReturn(mockTeachers);
        List<TeacherEntity> teachers = teacherService.getAllTeachersByName(FIRST_NAME, LAST_NAME);

        Assert.assertNotNull(teachers);
        Assert.assertEquals(3, teachers.size());
        Mockito.verify(teacherRepository, Mockito.times(1))
                .findByFirstNameAndLastName(Mockito.eq(FIRST_NAME), Mockito.eq(LAST_NAME));
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByFirstName(Mockito.anyString());
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByLastName(Mockito.anyString());
    }

    @Test
    public void getTeacherByFirstName() {
        Mockito.when(teacherRepository.findByFirstName(Mockito.eq(FIRST_NAME)))
                .thenReturn(mockTeachers);
        List<TeacherEntity> teachers = teacherService.getAllTeachersByName(FIRST_NAME, null);

        Assert.assertNotNull(teachers);
        Assert.assertEquals(3, teachers.size());
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(teacherRepository, Mockito.times(1))
                .findByFirstName(Mockito.eq(FIRST_NAME));
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByLastName(Mockito.anyString());
    }

    @Test
    public void getTeacherByLastName() {
        Mockito.when(teacherRepository.findByLastName(Mockito.eq(LAST_NAME)))
                .thenReturn(mockTeachers);
        List<TeacherEntity> teachers = teacherService.getAllTeachersByName(null, LAST_NAME);

        Assert.assertNotNull(teachers);
        Assert.assertEquals(3, teachers.size());
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(teacherRepository, Mockito.times(0))
                .findByFirstName(Mockito.anyString());
        Mockito.verify(teacherRepository, Mockito.times(1))
                .findByLastName(LAST_NAME);
    }


    private static TeacherEntity getMockTeacher() {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setId(ID);
        teacherEntity.setFirstName(FIRST_NAME);
        teacherEntity.setLastName(LAST_NAME);

        return teacherEntity;
    }
}
