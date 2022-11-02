package com.pocu.catalog.service;

import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.TeacherEntity;
import com.pocu.catalog.exception.TeacherNotFoundException;
import com.pocu.catalog.repository.TeacherRepository;
import com.pocu.catalog.web.dto.TeacherDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service                //this class is a Service bean
public class TeacherService {

    private final static String TEACHER_NOT_FOUND_CODE = "TEACHER_NOT_FOUND";
    private final TeacherRepository teacherRepository;
    private final SubjectService subjectService;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, SubjectService subjectService) {
        this.teacherRepository = teacherRepository;
        this.subjectService = subjectService;
    }

    public List<TeacherEntity> getAllTeachers() {
        List<TeacherEntity> teacherList = new ArrayList<>();
        teacherRepository.findAll().iterator().forEachRemaining(teacherList::add);
        return teacherList;
    }

    public List<TeacherEntity> getAllTeachers(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return teacherRepository.findAll(pageRequest).getContent();
    }

    //firstName and lastName can be both set up or not
    public List<TeacherEntity> getAllTeachersByName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return teacherRepository.findByFirstNameAndLastName(firstName, lastName);
        } else if (firstName != null) {
            return teacherRepository.findByFirstName(firstName);
        } else {
            return teacherRepository.findByLastName(lastName);
        }
    }

    public TeacherEntity saveTeacher(TeacherEntity teacherEntity) {
        return teacherRepository.save(teacherEntity);
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public void deleteTeacherByCnp(String cnp) {
        teacherRepository.deleteByCnp(cnp);
    }

    public void deleteAllTeachers() {
        teacherRepository.deleteAll();
    }

    public TeacherEntity getTeacher(Long id) {
        Optional<TeacherEntity> teacherOptional = teacherRepository.findById(id);
        if (teacherOptional.isPresent()) {
            return teacherOptional.get();
        } else {
            throw new TeacherNotFoundException("Teacher with id " + id + "not found", TEACHER_NOT_FOUND_CODE);
        }
    }



    public TeacherEntity updateTeacher(Long id, TeacherEntity teacherToUpdate) {
        TeacherEntity storedTeacher = getTeacher(id);
        teacherToUpdate.setId(id);

        return teacherRepository.save(teacherToUpdate);

    }

    public TeacherEntity assignSubjectToTeacher(Long teacherId, Long subjectId) {
        SubjectEntity subjectEntity = subjectService.getSubject(subjectId);
        TeacherEntity teacherEntity = getTeacher(teacherId);

        teacherEntity.setSubject(subjectEntity);

        return teacherRepository.save(teacherEntity);
    }


    public List<TeacherEntity> getAllTeachersBySalary(Long minSalary, Integer page, Integer size) {

        return teacherRepository.findBySalaryGreaterThan(minSalary, PageRequest.of(page, size,
                Sort.by("salary").descending().and(Sort.by("firstName").ascending())));
    }

    public Long countTeachersWithSmallSalary(Long maxSalary) {
        return teacherRepository.countAllBySalaryLessThan(maxSalary);
    }
}
