package com.pocu.catalog.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "subject")
public class SubjectEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "credit_points")
    private Integer creditPoints;

    @Column(name = "is_optional")
    private Boolean optional;

//    @ManyToMany(mappedBy = "enrolledSubjects")
//    private List<User> enrolledStudents;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private List<ProjectEntity> projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(Integer creditPoints) {
        this.creditPoints = creditPoints;
    }

    public Boolean getOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

//    public List<User> getEnrolledStudents() {
//        return enrolledStudents;
//    }
//
//    public void setEnrolledStudents(List<User> enrolledStudents) {
//        this.enrolledStudents = enrolledStudents;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubjectEntity that = (SubjectEntity) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(creditPoints, that.creditPoints) &&
                Objects.equals(optional, that.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creditPoints, optional);
    }



    public List<ProjectEntity> getProject() {
        return projects;
    }

    public void addProject(ProjectEntity project) {
        if (this.projects == null) {
            this.projects = new ArrayList<>();
        }
        this.projects.add(project);
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }
}
