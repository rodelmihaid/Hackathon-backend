package com.pocu.catalog.service;

import com.pocu.catalog.entity.ProjectStudentEntity;
import com.pocu.catalog.entity.User;
import com.pocu.catalog.enums.UserType;
import com.pocu.catalog.exception.ProjectStudentNotFoundException;
import com.pocu.catalog.repository.ProjectStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@Service
//public class ProjectStudentService {
//    private final ProjectStudentRepository projectStudentRepository;
//    private final ProjectRepository projectRepository;
//    private final StudentRepository studentRepository;
//    private static final String STUDENT_NOT_FOUND_CODE = "STUDENT_NOT_FOUND";
//    @Autowired
//    public ProjectStudentService(ProjectStudentRepository projectStudentRepository, ProjectRepository projectRepository, StudentRepository studentRepository) {
//        this.projectStudentRepository = projectStudentRepository;
//        this.projectRepository = projectRepository;
//        this.studentRepository = studentRepository;
//    }
//
//    public ProjectStudentEntity saveProjectStudent(ProjectStudentDto projectStudentDto) {
//        ProjectEntity projectEntity = projectRepository.findById(projectStudentDto.getProjectId()).get();
//        StudentEntity studentEntity = studentRepository.findById(projectStudentDto.getStudentId()).get();
//        ProjectStudentEntity projectStudent = new ProjectStudentEntity();
//        projectStudent.setProject(projectEntity);
//        projectStudent.setStudent(studentEntity);
//        projectStudent.setSolution(projectStudentDto.getSolution());
//        //projectStudent.setAttachment(projectStudentDto.getAttachment());
//        return projectStudentRepository.save(projectStudent);
//    }
//}

@Service                //this class is a Service bean
public class ProjectStudentService {

    private final static String PROJECTSTUDENT_NOT_FOUND_CODE = "PROJECTSTUDENT_NOT_FOUND";
    private final ProjectStudentRepository projectStudentRepository;
    private final SubjectService subjectService;
    private final UserService userService;

    @Autowired
    public ProjectStudentService(ProjectStudentRepository projectStudentRepository, SubjectService subjectService,
                                 UserService userService) {
        this.projectStudentRepository = projectStudentRepository;
        this.subjectService = subjectService;
        this.userService=userService;
    }

    public List<ProjectStudentEntity> getProjectStudents() {
        List<ProjectStudentEntity> projectStudentList = new ArrayList<>();
        projectStudentRepository.findAll().iterator().forEachRemaining(projectStudentList::add);
        return projectStudentList;
    }

    public ProjectStudentEntity saveProjectStudent(ProjectStudentEntity projectStudentEntity) {
        return projectStudentRepository.save(projectStudentEntity);
    }

    public void deleteProjectStudent(Long id) {
        projectStudentRepository.deleteById(id);
    }

    public void deleteAllProjectsStudents() {
        projectStudentRepository.deleteAll();
    }

    public ProjectStudentEntity getProjectStudent(Long id) {
        Optional<ProjectStudentEntity> projectStudentOptional = projectStudentRepository.findById(id);
        if (projectStudentOptional.isPresent()) {
            return projectStudentOptional.get();
        } else {
            throw new ProjectStudentNotFoundException("ProjectStudent with id " + id + "not found", PROJECTSTUDENT_NOT_FOUND_CODE);
        }
    }

    public List<ProjectStudentEntity> getProjectStudentsByProjectId(Long id) {
        List<ProjectStudentEntity> projectStudentEntities = new ArrayList<>();
        projectStudentRepository.findProjectStudentEntityByProjectId(id).forEach(elem -> projectStudentEntities.add(elem));
        return projectStudentEntities;

    }

    public ProjectStudentEntity getProjectStudentsByProjectIdAndUserEmail(Long id,String userEmail) {
        User user=userService.getUsersByEmail(userEmail).get(0);

        return projectStudentRepository.findProjectStudentEntityByProjectIdAndStudentId(id,user.getId());


    }

    public List<ProjectStudentEntity> getProjectStudentsByProjectIdAndUserIsNotTeacher(Long id) {
        return projectStudentRepository.findProjectStudentEntityByProjectIdAndStudent_Authorities(id, UserType.USER.toString());

    }

    public ProjectStudentEntity addGrade(Long id,String userEmail,String grade) {
        User user=userService.getUsersByEmail(userEmail).get(0);


        ProjectStudentEntity projectStudentEntity= projectStudentRepository.findProjectStudentEntityByProjectIdAndStudentId(id,user.getId());

        projectStudentEntity.setGrade(grade);
       return projectStudentRepository.save(projectStudentEntity);

    }


    public ProjectStudentEntity updateProjectStudent(Long id, ProjectStudentEntity projectStudentToUpdate) {
        ProjectStudentEntity storedProjectStudent = getProjectStudent(id);
        projectStudentToUpdate.setId(id);

        return projectStudentRepository.save(projectStudentToUpdate);

    }

}

//-----------------------------------------------------------------

