package com.mndev.spring.integration.database.repository;

import com.mndev.spring.database.entity.Role;
import com.mndev.spring.database.entity.User;
import com.mndev.spring.database.repository.UserRepository;
import com.mndev.spring.dto.UserFilter;
import com.mndev.spring.integration.IntegrationTestBase;
import com.mndev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.Modifying;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
class UserRepositoryTest extends IntegrationTestBase {

    private final UserRepository userRepository;

    //Pageable -разделение результата таблицы по страницам
    @Test
    void checkPageable() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<User> slice = userRepository.findAllBy(pageable);
//        assertThat(users).hasSize(2);
        slice.forEach(user -> System.out.println(user.getCompany().getName()));

        while (slice.hasNext()){
            slice = userRepository.findAllBy(slice.nextPageable());
            slice.forEach(user -> System.out.println(user.getCompany().getName()));
        }
//        System.out.println(slice.getTotalPages());
    }

    @Test
    void checkUpdate() {
        int result = userRepository.updateRole(Role.USER, 1L, 5L);
        assertEquals(2, result);

        User byId = userRepository.getById(1L);
        assertSame(Role.USER, byId.getRole());
    }

    @Test
    void checkQueries() {
        List<User> users = userRepository.findAllBy("a", "ov");
        System.out.println(users);
    }

    @Test
    void checkAllByFilter() {
        UserFilter filter = new UserFilter(
                null, "ov", LocalDate.now()
        );
        List<User> users = userRepository.findAllByFilter(filter);
        assertThat(users).hasSize(4);
    }
}