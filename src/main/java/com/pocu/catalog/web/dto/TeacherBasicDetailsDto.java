package com.pocu.catalog.web.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class TeacherBasicDetailsDto extends BaseDto {

    @NotEmpty(message = "firstName.cannot.be.empty")
    private String firstName;
    @NotEmpty(message = "lastName.cannot.be.empty")
    private String lastName;
    private List<SubjectDto> subjects;

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

    public List<SubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectDto> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "TeacherBasicDetailsDto{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", subjects=" + subjects +
                "} " + super.toString();
    }
}
