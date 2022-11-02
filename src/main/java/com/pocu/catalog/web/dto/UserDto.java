package com.pocu.catalog.web.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto extends BaseDto{
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String university;
    private Date registrationDate;
    private String authorities;
    private String faculty;
    private String profileImage;
    private Boolean tagEmail;
    private Boolean commentEmail;
    private Boolean postEmail;
    private List<SubjectDto> enrolledSubjects;

    public List<SubjectDto> getEnrolledSubjects() {
        return enrolledSubjects;
    }

    public void setEnrolledSubjects(List<SubjectDto> enrolledSubjects) {
        this.enrolledSubjects = enrolledSubjects;
    }

}
