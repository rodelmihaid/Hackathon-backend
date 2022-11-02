package com.pocu.catalog.config;

import com.pocu.catalog.entity.*;
import com.pocu.catalog.enums.UserType;
import com.pocu.catalog.service.*;
import com.pocu.catalog.web.dto.ProjectDto;
import com.pocu.catalog.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@ConditionalOnExpression("${insert.test.data}")
public class DataSetup implements ApplicationRunner {

    private final SubjectService subjectService;
    private final TeacherService teacherService;
    private final UserService userService;
    private final ProjectService projectService;
    private final ProjectStudentService projectStudentService;

    @Autowired
    public DataSetup(SubjectService subjectService, TeacherService teacherService, UserService studentService, ProjectService projectService,
                     ProjectStudentService projectStudentService) {
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.userService = studentService;
        this.projectService = projectService;
        this.projectStudentService=projectStudentService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        SubjectEntity subjectOne = saveSubject("Matematica", true, 5);
        SubjectEntity subjectTwo = saveSubject("POO-JAVA", true, 6);
        SubjectEntity subjectThree = saveSubject("C++", false, 4);
        SubjectEntity subjectFore = saveSubject("Python", false, 5);
        SubjectEntity subjectFive = saveSubject("Romana", true, 5);

        List<SubjectEntity> subjects = new ArrayList<>();
        subjects.add(subjectOne);
        subjects.add(subjectTwo);
        subjects.add(subjectThree);
        subjects.add(subjectFore);
        subjects.add(subjectFive);

        saveTeacher("Andrei", "Vasile", "1234567890123", 10L, subjects);
        saveTeacher("Maria", "Pop", "2234567890123", 15L, new ArrayList<>());
        saveTeacher("Alexandra", "Popescu", "3234567890123", 22L, new ArrayList<>());
        saveTeacher("Lucian", "Popovici", "4234567890123", 22L, new ArrayList<>());
        saveTeacher("Valentin", "Radu", "5234567890123", 3L, new ArrayList<>());
        saveTeacher("Mirela", "Gheorghe", "6234567890123", 12L, new ArrayList<>());
        saveTeacher("Carmen", "Predescu", "7234567890123", 5L, new ArrayList<>());
        saveTeacher("Claudiu", "Vasilescu", "8234567890123", 22L, new ArrayList<>());
        saveTeacher("Andreea", "Coman", "9234567890123", 4L, new ArrayList<>());
        saveTeacher("Claudia", "Miclea", "1034567890123", 2L, new ArrayList<>());

        UserDto student1 = registerUser("Andreea", "Andreescu", "andreea@gmail.com", "andreea",UserType.USER.toString());
        System.out.println("User Andreea id: " + student1.getId().toString());
        UserDto student2 = registerUser("Alex", "Bogdanescu", "alex@gmail.com", "alex",UserType.USER.toString());
        UserDto student3 = registerUser("Marius", "Mugurescu", "marius@gmail.com", "marius",UserType.USER.toString());
        UserDto student4 = registerUser("Sabin", "Popa", "sabin@gmail.com", "sabin",UserType.USER.toString());
        UserDto student5 = registerUser("Mircea", "Dima", "mircea@gmail.com", "mircea",UserType.USER.toString());


        UserDto professor1 = registerUser("Mihai", "Eminescu", "mihaieminescu@gmail.com", "mihaieminescu",UserType.PROFESSOR.toString());
        enrollUserToSubject(professor1.getId(),subjectOne.getId());
        UserDto professor2 = registerUser("Ion", "Barbu", "ionbarbu@gmail.com", "ionbarbu",UserType.PROFESSOR.toString());
        enrollUserToSubject(professor2.getId(),subjectTwo.getId());
        UserDto professor3 = registerUser("Lucian", "Blaga", "lucianblaga@gmail.com", "lucianblaga",UserType.PROFESSOR.toString());
        enrollUserToSubject(professor3.getId(),subjectFore.getId());
        UserDto professor4 = registerUser("Ion", "Creanga", "ioncreanga@gmail.com", "ioncreanga",UserType.PROFESSOR.toString());
        enrollUserToSubject(professor4.getId(),subjectFive.getId());
        UserDto professor5 = registerUser("Nichita", "Stanescu", "nichitastanescu@gmail.com", "nichitastanescu",UserType.PROFESSOR.toString());
        enrollUserToSubject(professor5.getId(),subjectThree.getId());

        enrollUserToSubject(student1.getId(), subjectTwo.getId());
        enrollUserToSubject(student1.getId(), subjectOne.getId());
        enrollUserToSubject(student1.getId(), subjectThree.getId());
        enrollUserToSubject(student1.getId(), subjectFore.getId());
        enrollUserToSubject(student1.getId(), subjectFive.getId());

        enrollUserToSubject(student2.getId(), subjectTwo.getId());
        enrollUserToSubject(student2.getId(), subjectOne.getId());
        enrollUserToSubject(student2.getId(), subjectThree.getId());

        enrollUserToSubject(student3.getId(), subjectTwo.getId());
        enrollUserToSubject(student4.getId(), subjectTwo.getId());
        enrollUserToSubject(student5.getId(), subjectTwo.getId());

        ProjectEntity projectEntity1= addMockProjectOnSubject(subjectOne.getId());

        ProjectEntity projectEntity2=  addMockProjectOnSubject2(subjectTwo.getId());


        addMockProjectStudentForStudent(student1.getEmail(), projectEntity1.getId());


    }


    private SubjectEntity saveSubject(String name, Boolean optional, Integer creditPoints) {
        SubjectEntity subjectEntity = new SubjectEntity();
        subjectEntity.setName(name);
        subjectEntity.setOptional(optional);
        subjectEntity.setCreditPoints(creditPoints);
        return subjectService.saveSubject(subjectEntity);
    }

    private void saveTeacher(String firstName, String lastName, String cnp, Long salary, List<SubjectEntity> subjects) {
        TeacherEntity teacherEntity = new TeacherEntity();
        teacherEntity.setFirstName(firstName);
        teacherEntity.setLastName(lastName);
        teacherEntity.setCnp(cnp);
        teacherEntity.setSalary(salary);
        teacherEntity.setSubjects(subjects);
        teacherService.saveTeacher(teacherEntity);
    }

    private void enrollUserToSubject(Long studentId, Long subjectId) {
        User studentEntity = userService.enroll(studentId, subjectId, true);
    }

    private UserDto registerUser(String firstName, String lastName, String email, String password,String userType) {
        UserDto student = new UserDto();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setEmail(email);
        student.setPassword(password);
        student.setProfileImage("");
        student.setAuthorities(userType);
        return userService.registerUser(student);
    }

    private ProjectEntity addMockProjectOnSubject(Long subjectId) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setSubjectId(subjectId);
        projectDto.setTitle("Tema1");
        projectDto.setDescription("bla sdl alda  slddgdkgdk gdk gdg dgd asfsdg dfg dfg dfgdf gdfg djfgdfjg jdgjdjg dfjg dfj gdjg jdfg jdg jdj gd");
//        projectDto.setAttachment();
        projectDto.setDeadline(LocalDate.now());
        ProjectEntity entity= projectService.save(projectDto);
        subjectService.addProjectOnSubject(subjectId,entity.getId());

        return entity;

    }

    private ProjectEntity addMockProjectOnSubject2(Long subjectId) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setSubjectId(subjectId);
        projectDto.setTitle("Tema2");
        projectDto.setDescription(" aaaaaaa  aa a a a a a a a a a bla sdl alda  slddgdkgdk gdk gdg dgd asfsdg dfg dfg dfgdf gdfg djfgdfjg jdgjdjg dfjg dfj gdjg jdf    fdg df gd g dfg g jdg jdj gd");
//        projectDto.setAttachment();
        projectDto.setDeadline(LocalDate.now());

        ProjectEntity entity= projectService.save(projectDto);
        subjectService.addProjectOnSubject(subjectId,entity.getId());

        return entity;



    }

    private void addMockProjectStudentForStudent(String userEmail,Long projectId){
        ProjectStudentEntity projectStudentEntity=new ProjectStudentEntity();
        projectStudentEntity.setSolution("am rezolvat usor");
        User user=userService.getUsersByEmail(userEmail).get(0);
        ProjectEntity projectEntity=projectService.getProject(projectId);
        projectStudentEntity.setProject(projectEntity);
        projectStudentEntity.setStudent(user);
        projectStudentService.saveProjectStudent(projectStudentEntity);
    }


}
