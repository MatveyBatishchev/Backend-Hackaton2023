package ru.hackaton.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.hackaton.backend.util.FileUploadUtil;
import ru.hackaton.backend.util.UploadFileResponse;

@RestController
@RequiredArgsConstructor
public class FileControllerImpl implements FileController {

    private final FileUploadUtil fileUploadUtil;

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        return fileUploadUtil.saveFile(file);
    }
}
