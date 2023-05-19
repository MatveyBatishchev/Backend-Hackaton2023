package ru.hackaton.backend.util;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageWrapper<T> {

    private long totalCount;

    private List<T> content;

    @JsonValue
    public List<T> getContent() {
        return content;
    }

}
