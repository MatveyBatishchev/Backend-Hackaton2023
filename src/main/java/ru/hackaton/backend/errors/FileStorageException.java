package ru.hackaton.backend.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception throws when error occurs during saving file
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public final class FileStorageException extends RuntimeException {

    public FileStorageException() {
        super();
    }

    public FileStorageException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(final String message) {
        super(message);
    }

    public FileStorageException(final Throwable cause) {
        super(cause);
    }

}