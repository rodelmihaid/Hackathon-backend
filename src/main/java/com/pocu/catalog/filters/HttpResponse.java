package com.pocu.catalog.filters;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    /*    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss", timezone = "America/New_York")*/
    private Date timeStamp;
    private int httpStatusCode; // 200, 201, 400, 500
    private HttpStatus httpStatus;
    private String reason;
    private String message;
}

