package com.mndev.spring.mapper;

import com.mndev.spring.database.entity.Company;
import com.mndev.spring.database.entity.User;
import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.dto.UserCreateEditDto;
import com.mndev.spring.dto.UserReadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
@RequiredArgsConstructor
public class UserCreateEditMapper implements Mapper<UserCreateEditDto, User> {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User mapSecondToFirst(UserCreateEditDto fromObject, User toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public User mapFirstToSecond(UserCreateEditDto object) {
        User user = new User();
        copy(object, user);

        return user;

    }

    private void copy(UserCreateEditDto object, User user) {
        user.setUsername(object.getUsername());
        user.setFirstname(object.getFirstname());
        user.setLastname(object.getLastname());
        user.setBirthDate(object.getBirthDate());
        user.setRole(object.getRole());
        user.setCompany(getCompany(object.getCompanyId()));

        Optional.ofNullable(object.getRawPassword())
                        .filter(StringUtils::hasText)
                                .map(passwordEncoder::encode)
                                        .ifPresent(user::setPassword);

        //Проверка на наличие аватара
        Optional.ofNullable(object.getImage())
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> user.setImage(image.getOriginalFilename()));
    }

    public Company getCompany(Integer companyId){
        return Optional.ofNullable(companyId)
                .flatMap(companyRepository::findById).orElse(null);
    }
}
