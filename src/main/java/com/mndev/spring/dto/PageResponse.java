package com.mndev.spring.dto;

import lombok.Value;
import org.springframework.data.domain.Page;

import java.util.List;

@Value
public class PageResponse<T> {
    List<T> content;
    Metadata metadata;

    public static <T> PageResponse<T> off(Page<T> page) {
        Metadata metadata1 = new Metadata(page.getNumber(), page.getSize(), page.getTotalElements());
        return new PageResponse<>(page.getContent(), metadata1);
    }

    @Value
    public static class Metadata{
       int page;
       int size;
       long totalElements;
    }
}
