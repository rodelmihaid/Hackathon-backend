package com.pocu.catalog.entity;

import com.pocu.catalog.constants.SecurityConstant;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User extends BaseEntity{
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;
    @Column(name = "university")
    private String university;
    @Column(name = "faculty")
    private String faculty;
    @Column(name = "registrationDate")
    private Date registrationDate;
    @Column(name = "authorities")
    private String authorities;
    @Lob
    @Column(name = "profileImage",columnDefinition = "varchar(10000000) DEFAULT '" + SecurityConstant.DEFAULT_PROFILE_IMAGE+"'" )
    private String profileImage;

    @Column(name = "tagEmail",columnDefinition="BOOLEAN DEFAULT true")
    private Boolean tagEmail;
    @Column(name = "commentEmail",columnDefinition="BOOLEAN DEFAULT true")
    private Boolean commentEmail;
    @Column(name = "postEmail",columnDefinition="BOOLEAN DEFAULT true")
    private Boolean postEmail;
//    byte[] profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "subject_enrollment",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<SubjectEntity> enrolledSubjects;



    public User(String email, String password, String firstName, String lastName, String university,
                Date registrationDate, String authorities, String faculty, String profileImage) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.university = university;
        this.registrationDate = registrationDate;
        this.authorities = authorities;
        this.faculty = faculty;
        this.profileImage = profileImage;
    }



    public User() {
    }

    public void setEnrolledSubject(SubjectEntity subject) {
        if (this.enrolledSubjects == null) {
            this.enrolledSubjects = new ArrayList<>();
        }
        this.enrolledSubjects.add(subject);
    }

    public void removeEnrolledSubject(SubjectEntity subject) {
        if (this.enrolledSubjects == null || this.enrolledSubjects.isEmpty()) {
            return;
        }
        this.enrolledSubjects.remove(subject);
    }
}
