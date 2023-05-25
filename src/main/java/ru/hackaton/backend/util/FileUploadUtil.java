package ru.hackaton.backend.util;

import org.apache.tomcat.util.http.fileupload.FileUtils;
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
import java.util.UUID;

@Component
public class FileUploadUtil {

    private final static List<String> mediaTypes = List.of("image", "video", "audio");

    private final Path fileStorageLocation;

    private final String fileUploadPath;

    @Autowired
    public FileUploadUtil(@Value("${file.uploadDir}") String fileUploadPath) {
        this.fileUploadPath = fileUploadPath;
        this.fileStorageLocation = Paths.get(fileUploadPath).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            for (FileEntityMarker fileEntityMarker : FileEntityMarker.values()) {
                for (String mediaType : mediaTypes) {
                    Path mediaTypeDirectory = fileStorageLocation.resolve(fileEntityMarker.getName()).resolve(mediaType);
                    Files.createDirectories(mediaTypeDirectory);
                }
            }
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    public UploadFileResponse saveFile(MultipartFile file, FileEntityMarker fileEntityMarker) {
        String originalFilename = validateFile(file, mediaTypes);
        String entityMarker = fileEntityMarker.getName();
        String contentType = getMediaType(file.getContentType());
        String uuidFilename = UUID.randomUUID() + getFileExtension(originalFilename);

        if (fileEntityMarker.equals(FileEntityMarker.USER))
            throw new FileStorageException("Invalid multipart file or file_entity_marker");

        try {
            Path targetLocation = this.fileStorageLocation
                    .resolve(entityMarker)
                    .resolve(contentType)
                    .resolve(uuidFilename);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/" + fileUploadPath)
                    .path(entityMarker)
                    .path("/" + contentType)
                    .path("/" + uuidFilename)
                    .toUriString();

            return new UploadFileResponse(originalFilename, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!", ex);
        }
    }

    public UploadFileResponse saveUserAvatar(long id, MultipartFile file) {
        String originalFilename = validateFile(file, List.of("image"));
        String entityMarker = FileEntityMarker.USER.getName();
        String contentType = "image";
        String uuidFilename = UUID.randomUUID() + getFileExtension(originalFilename);

        try {
            Path targetLocation = this.fileStorageLocation
                    .resolve(entityMarker)
                    .resolve(contentType)
                    .resolve(String.valueOf(id));

            if (!Files.exists(targetLocation)) {
                Files.createDirectories(targetLocation);
            } else {
                FileUtils.cleanDirectory(targetLocation.toFile());
            }
            targetLocation = targetLocation.resolve(uuidFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/" + fileUploadPath)
                    .path(entityMarker)
                    .path("/" + contentType)
                    .path("/" + id)
                    .path("/" + uuidFilename)
                    .toUriString();

            return new UploadFileResponse(uuidFilename, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + originalFilename + ". Please try again!", ex);
        }
    }

    private String validateFile(MultipartFile file, List<String> mediaTypes) {
        if (file == null
                || file.getOriginalFilename() == null
                || file.getContentType() == null
                || !mediaTypes.contains(getMediaType(file.getContentType()))) {
            throw new FileStorageException("Invalid multipart file or file_entity_marker");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
        return fileName;
    }

    public String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1 || dotIndex == filename.length() - 1) {
            throw new FileStorageException("Invalid multipart file or file_entity_marker");
        }
        return filename.substring(dotIndex).toLowerCase();
    }

    private String getMediaType(String contentType) {
        return contentType.substring(0, contentType.indexOf('/'));
    }

}
