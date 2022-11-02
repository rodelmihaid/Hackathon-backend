package com.pocu.catalog.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
public class ProjectEntity extends BaseEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "deadline")
    private LocalDate deadline;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


    @Override
    public String toString() {
        return "ProjectEntity{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", attachment='" + attachment + '\'' +
                ", deadline=" + deadline +
                "} " + super.toString();
    }

//    public List<SubjectEntity> getSubjects() {
//        return subjects;
//    }
//
//    public void setSubject(SubjectEntity subject) {
//        if (this.subjects == null) {
//            this.subjects = new ArrayList<>();
//        }
//        this.subjects.add(subject);
//    }

    public void setSubject(SubjectEntity subjectEntity) {
    }
}
