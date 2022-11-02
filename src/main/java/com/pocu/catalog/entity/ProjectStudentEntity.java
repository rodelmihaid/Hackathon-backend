package com.pocu.catalog.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.io.Serializable;


@Entity
@Table(name = "project_student")

public class ProjectStudentEntity extends BaseEntity {

    @Column(name = "description")
    private String description;

   @Column(name = "solution")
   private String solution;

    @Column(name = "attachment")
    private String attachment;


    @ManyToOne
    @JoinColumn(name="student_id")
    User student;

    @ManyToOne
    @JoinColumn(name="project_id")
    ProjectEntity project;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity project) {
        this.project = project;
    }

    public void setSubject(SubjectEntity subjectEntity) {
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    //    @Override
//    public String toString() {
//        return "ProjectStudentEntity{" +
//                ", description='" + description + '\'' +
//                ", attachment='" + solution + '\'';
//
//    }



}
