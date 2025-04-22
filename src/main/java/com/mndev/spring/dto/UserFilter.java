package com.mndev.spring.dto;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Value
public class UserFilter {

    String firstname;
    String lastname;
    LocalDate birthDate;
}
