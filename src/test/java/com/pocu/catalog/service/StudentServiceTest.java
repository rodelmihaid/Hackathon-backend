package com.pocu.catalog.service;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.exception.StudentAlreadyEnrolledException;
import com.pocu.catalog.exception.StudentNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.pocu.catalog.util.TestConstants.ID;
import static com.pocu.catalog.util.TestConstants.SUBJECT_ID;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private SubjectService subjectService;
    @Captor
    private ArgumentCaptor<StudentEntity> captor;

    @Test(expected = StudentNotFoundException.class)
    public void enroll_StudentNotFound() {
        Mockito.when(studentRepository.findById(Mockito.eq(ID))).thenReturn(Optional.empty());

        studentService.enroll(ID, SUBJECT_ID, true);

        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test(expected = StudentAlreadyEnrolledException.class)
    public void enroll_StudentAlreadyEnrolled() {
        Mockito.when(studentRepository.findById(Mockito.eq(ID))).thenReturn(Optional.of(getMockStudent(true)));
        Mockito.when(subjectService.getSubject(Mockito.eq(SUBJECT_ID))).thenReturn(getMockSubject());

        studentService.enroll(ID, SUBJECT_ID, true);
        Mockito.verify(studentRepository, Mockito.times(0)).save(Mockito.any());
    }

    @Test
    public void enroll_Success() {
        Mockito.when(studentRepository.findById(Mockito.eq(ID))).thenReturn(Optional.of(getMockStudent(false)));
        Mockito.when(subjectService.getSubject(Mockito.eq(SUBJECT_ID))).thenReturn(getMockSubject());


        StudentEntity student = studentService.enroll(ID, SUBJECT_ID, true);

        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any());

        Mockito.verify(studentRepository).save(captor.capture());

        List<SubjectEntity> enrolledSubjects = captor.getValue().getEnrolledSubjects();
        Assert.assertNotNull(enrolledSubjects);
        Assert.assertEquals(1, enrolledSubjects.size());
        Assert.assertEquals(SUBJECT_ID, enrolledSubjects.get(0).getId());
    }

    @Test
    public void unenroll_Success() {
        Mockito.when(studentRepository.findById(Mockito.eq(ID))).thenReturn(Optional.of(getMockStudent(false)));
        Mockito.when(subjectService.getSubject(Mockito.eq(SUBJECT_ID))).thenReturn(getMockSubject());

        StudentEntity student = studentService.enroll(ID, SUBJECT_ID, false);

        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any());
    }

    private SubjectEntity getMockSubject() {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setId(SUBJECT_ID);

        return subjectEntity;
    }

    private StudentEntity getMockStudent(Boolean includeSubject) {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(ID);
        if (includeSubject) {
            studentEntity.setEnrolledSubject(getMockSubject());
        }

        return studentEntity;
    }
}
