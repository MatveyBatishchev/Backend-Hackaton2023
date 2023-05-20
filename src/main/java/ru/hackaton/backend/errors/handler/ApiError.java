package ru.hackaton.backend.errors.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private HttpStatus status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String exception;

    private String reason;


    public ApiError(HttpStatus status, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.exception = ex.getClass().getSimpleName();
        this.reason = ex.getMessage();
    }

}
