package com.pocu.catalog.web.dto;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class TeacherDto extends TeacherBasicDetailsDto {

    @NotEmpty(message = "cnp.cannot.be.empty")
    @Size(min = 13, max = 13, message = "cnp.size.invalid")
    @Pattern(regexp = "[0-9]+", message = "cnp.invalid")
    private String cnp;
   // @NotNull(message = "dateOfBirth.cannot.be.null")
    private LocalDate dateOfBirth;
    @NotNull(message = "salary.cannot.be.null")
    @Min(value = 1, message = "salary.cannot.be.negative")
    private Long salary;

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "TeacherDto{" +
                "cnp='" + cnp + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", salary=" + salary +
                "} " + super.toString();
    }
}
