package com.mndev.spring.dto;

import com.mndev.spring.database.entity.Role;
import com.mndev.spring.validation.UserInfo;
import lombok.Value;
import org.postgresql.util.LruCache;
import org.postgresql.util.LruCache.CreateAction;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Value
@UserInfo
public class UserCreateEditDto {
    //Аннотации валидации
    @Email
    String username;

    @NotBlank(groups = CreateAction.class)
    String rawPassword;

    LocalDate birthDate;

    @Size(min = 3, max = 50)
    String firstname;

    String lastname;

    Role role;

    Integer companyId;

    //картинка название должно соответствовать VIEW
    MultipartFile image;
}
