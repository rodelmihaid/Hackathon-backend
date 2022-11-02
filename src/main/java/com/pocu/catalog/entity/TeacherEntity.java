package com.pocu.catalog.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher")
public class TeacherEntity extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "cnp")
    private String cnp;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "salary")
    private Long salary;

    @OneToMany
    @JoinColumn(name = "teacher_id")
    private List<SubjectEntity> subjects;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public List<SubjectEntity> getSubjects() {
        return subjects;
    }

    public void setSubject(SubjectEntity subject) {
        if (this.subjects == null) {
            this.subjects = new ArrayList<>();
        }
        this.subjects.add(subject);
    }

    public void setSubjects(List<SubjectEntity> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "TeacherEntity{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cnp='" + cnp + '\'' +
                ", birthDate=" + birthDate +
                ", salary=" + salary +
                ", subjects=" + subjects +
                "} " + super.toString();
    }
}
