package ru.hackaton.backend.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileDetails {

    @JsonProperty("file_name")
    private String fileName;

    @JsonProperty("url")
    private String fileUri;

    @JsonProperty("content_type")
    private String contentType;

    @JsonProperty("file_size")
    private Long fileSize;

}
