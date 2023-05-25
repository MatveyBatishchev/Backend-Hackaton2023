package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.util.UploadFileResponse;

@Tag(name = "Files")
@RequestMapping("/files")
public interface FileController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file);

}