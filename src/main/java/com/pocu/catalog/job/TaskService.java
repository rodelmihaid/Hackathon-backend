package com.pocu.catalog.job;

import com.pocu.catalog.entity.ProjectEntity;
import com.pocu.catalog.entity.SubjectEntity;
import com.pocu.catalog.entity.User;
import com.pocu.catalog.enums.UserType;
import com.pocu.catalog.mail.MailRequest;
import com.pocu.catalog.mail.MailService;
import com.pocu.catalog.service.ProjectService;
import com.pocu.catalog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TaskService  {
    private final ProjectService projectService;
    private final UserService userService;

    private final MailService mailService;

    @Autowired
    public TaskService (ProjectService projectService, UserService userService, MailService service) {
        this.projectService=projectService;
        this.userService=userService;
        this.mailService=service;
    }


    @Scheduled(cron = "0 04 21 ? * *")
    public void mailTask() {
        System.out.println("Periodic task: " + new Date());
        List<ProjectEntity> projectsWithDeadlineTomorrow=projectService.getProjectWithDeadlineTomorrow();
        List<User> users=userService.getUserByUserType(UserType.USER.toString());
        projectsWithDeadlineTomorrow.forEach(elem ->{
            users.forEach(user -> {
                if (user.getEnrolledSubjects()!=null){
                    user.getEnrolledSubjects().forEach(subject -> {
                        if (subject.getProject()!=null){
                           List<ProjectEntity> projectWithDeadlineTomorrow= subject.getProject().stream().filter(projectEntity -> projectEntity.getId()==elem.getId()).collect(Collectors.toList());
                           if(projectWithDeadlineTomorrow.size()==1){
                               MailRequest mailRequest=new MailRequest();
                               mailRequest.setFrom("deadlineavoider@gmail.com");
                               mailRequest.setTo(user.getEmail());
                               mailRequest.setName("Deadline Avoider");
                               mailRequest.setSubject_title(subject.getName());
                               mailRequest.setAssignment_title(projectWithDeadlineTomorrow.get(0).getTitle());
                               mailRequest.setSubject("Ai o tema noua de incarcat!!!");

                               Map<String, Object> model = new HashMap<>();
                               model.put("name", mailRequest.getName());
                               model.put("subject_title", mailRequest.getSubject_title());
                               model.put("assignment_title", mailRequest.getAssignment_title());
                               mailService.sendEmail(mailRequest, model);

                           }
                        }
                    });
                }
            });
        });


    }
}
