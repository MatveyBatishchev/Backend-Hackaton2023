package ru.hackaton.backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponse {

    private int success;

    @JsonProperty("file")
    private FileDetails fileDetails;

}
