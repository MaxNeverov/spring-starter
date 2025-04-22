package com.mndev.spring.mapper;

import com.mndev.spring.database.entity.User;
import com.mndev.spring.dto.CompanyReadDto;
import com.mndev.spring.dto.UserReadDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
//С помощью аннотации инжектим CompanyReadMapper
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private final CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFirstToSecond(User object) {

        CompanyReadDto company = Optional.ofNullable(object.getCompany()).map(companyReadMapper::mapFirstToSecond).orElse(null);

        return new UserReadDto(
                object.getId(),
                object.getUsername(),
                object.getBirthDate(),
                object.getFirstname(),
                object.getLastname(),
                object.getImage(),
                object.getRole(),
                company
                );
    }
}
