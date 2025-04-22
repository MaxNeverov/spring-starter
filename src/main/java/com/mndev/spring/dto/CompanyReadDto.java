package com.mndev.spring.dto;

import com.mndev.spring.database.entity.Company;
import lombok.Value;

import java.util.Objects;

@Value
public class CompanyReadDto {
    Integer id;
    String name;
}
