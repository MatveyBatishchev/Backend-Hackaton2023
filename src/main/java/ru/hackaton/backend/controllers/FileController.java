package ru.hackaton.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.util.FileEntityMarker;
import ru.hackaton.backend.util.UploadFileResponse;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@Tag(name = "Files")
@RequestMapping("/files")
public interface FileController {

    @Operation(summary = "Загрузка файлов на сервер", description = "Маркер USER выдаст ошибку, его нельзя здесь использовать")
    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                  @RequestParam("file_entity_marker") FileEntityMarker fileEntityMarker);

}