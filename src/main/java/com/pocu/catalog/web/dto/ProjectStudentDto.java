package com.pocu.catalog.web.dto;

public class ProjectStudentDto  extends BaseDto {
    private Long projectId;
    private String userEmail;
    private String solution;
    private String attachment;

    private String grade;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "ProjectStudentDto{" +
                "projectId='" + projectId + '\'' +
                ", studentId=" + userEmail +
                ", solution=" + solution +
                ", attachment=" + attachment +
//              //  ", subjects=" + subjects +
                "} " + super.toString();
    }

}
