package com.pocu.catalog.mail;

import lombok.Data;

@Data
public class MailRequest {

    private String name;
    private String subject_title;
    private String assignment_title;
    private String to;
    private String from;
    private String subject;

}
