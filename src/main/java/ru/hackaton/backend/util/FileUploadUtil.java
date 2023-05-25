package ru.hackaton.backend.util;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.hackaton.backend.errors.FileStorageException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
public class FileUploadUtil {

    private final static List<String> mediaTypes = List.of("image", "video", "audio");

    private final Path fileStorageLocation;

    @Autowired
    public FileUploadUtil(@Value("${file.uploadDir}") String fileUploadPath) {
        this.fileStorageLocation = Paths.get(fileUploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            for (FileEntityMarker fileEntityMarker : FileEntityMarker.values()) {
                for (String mediaType : mediaTypes) {
                    Files.createDirectories(this.fileStorageLocation.resolve(fileEntityMarker.getName()).resolve(mediaType));
                }
            }
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    @SneakyThrows
    public UploadFileResponse saveFile(MultipartFile file, FileEntityMarker fileEntityMarker) {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            String contentType = file.getContentType().substring(0, file.getContentType().indexOf('/'));
            String entityMarker = fileEntityMarker.getName();

            Path targetLocation = this.fileStorageLocation
                    .resolve(entityMarker)
                    .resolve(contentType)
                    .resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/media/")
                    .path("/" + entityMarker)
                    .path("/" + contentType)
                    .path("/" + fileName)
                    .toUriString();

            return UploadFileResponse.builder()
                        .fileName(fileName)
                        .fileUri(fileDownloadUri)
                        .contentType(file.getContentType())
                        .fileSize(file.getSize())
                    .build();
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

}
