package com.pocu.catalog.web.dto;

import java.time.LocalDate;

public class ProjectDto  extends BaseDto {
    private String title;
    private String description;
    private String attachment;

    private LocalDate deadline;
    private Long subjectId;

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

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "title='" + title + '\'' +
                ", description=" + description +
                ", attachment=" + attachment +
                ", deadline=" + deadline +
//              //  ", subjects=" + subjects +
                "} " + super.toString();

    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }


}
