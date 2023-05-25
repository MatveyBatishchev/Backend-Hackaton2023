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
                    Path mediaTypeDirectory = fileStorageLocation.resolve(fileEntityMarker.getName()).resolve(mediaType);
                    Files.createDirectories(mediaTypeDirectory);
                }
            }
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored", ex);
        }
    }

    public UploadFileResponse saveFile(MultipartFile file, FileEntityMarker fileEntityMarker) {
        String fileName = validateFile(file, mediaTypes);
        String contentType = getMediaType(file.getContentType());
        String entityMarker = fileEntityMarker.getName();

        if (fileEntityMarker.equals(FileEntityMarker.USER))
            throw new FileStorageException("Invalid multipart file or file_entity_marker");

        try {
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

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public UploadFileResponse saveUserAvatar(long id, MultipartFile file) {
        String fileName = validateFile(file, List.of("image"));
        String userFileMarker = FileEntityMarker.USER.getName();

        try {
            Path targetLocation = this.fileStorageLocation
                    .resolve(userFileMarker)
                    .resolve("image")
                    .resolve(String.valueOf(id));

            if (!Files.exists(targetLocation)) {
                Files.createDirectories(targetLocation);
            } else {
                FileUtils.cleanDirectory(targetLocation.toFile());
            }
            targetLocation = targetLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/media/")
                    .path("/" + userFileMarker)
                    .path("/image")
                    .path("/" + id)
                    .path("/" + fileName)
                    .toUriString();

            return new UploadFileResponse(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
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

    private String getMediaType(String contentType) {
        return contentType.substring(0, contentType.indexOf('/'));
    }

}
