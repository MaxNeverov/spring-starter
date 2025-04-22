package com.mndev.spring.integration.service;

import com.mndev.spring.database.entity.Role;
import com.mndev.spring.database.entity.User;
import com.mndev.spring.dto.UserCreateEditDto;
import com.mndev.spring.dto.UserReadDto;
import com.mndev.spring.integration.IntegrationTestBase;
import com.mndev.spring.integration.annotation.IT;
import com.mndev.spring.service.UserService;
import lombok.RequiredArgsConstructor;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
public class UserServiceIT extends IntegrationTestBase {

    private final UserService userService;

    @Test
    void findAll(){
        List<UserReadDto> users = userService.findAll();
        assertThat(users).hasSize(5);
    }

    @Test
    void findById(){
        Optional<UserReadDto> user = userService.findById(1L);
        assertTrue(user.isPresent());
        user.ifPresent(it -> assertEquals("Ivan", it.getFirstname()));
    }

    @Test
    void create(){
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                "test@test.ru",
                "test",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                1,
                //картинка
                new MockMultipartFile("test", new byte[0])
        );

        UserReadDto actualRes = userService.create(userCreateEditDto);
        assertEquals(userCreateEditDto.getUsername(), actualRes.getUsername());
        assertEquals(userCreateEditDto.getBirthDate(), actualRes.getBirthDate());
        assertEquals(userCreateEditDto.getFirstname(), actualRes.getFirstname());
        assertEquals(userCreateEditDto.getLastname(), actualRes.getLastname());
        //Енамы сравниваются через assertSame
        assertSame(userCreateEditDto.getRole(), actualRes.getRole());
        assertEquals(userCreateEditDto.getCompanyId(), actualRes.getCompany().getId());

    }

    @Test
    void update() {
        UserCreateEditDto userCreateEditDto = new UserCreateEditDto(
                "test@test.ru",
                "test",
                LocalDate.now(),
                "test",
                "test",
                Role.ADMIN,
                1,
                new MockMultipartFile("test", new byte[0])
        );

        Optional<UserReadDto> actualRes = userService.update(1L, userCreateEditDto);
        assertTrue(actualRes.isPresent());
        actualRes.ifPresent(it -> {
            assertEquals(userCreateEditDto.getUsername(), it.getUsername());
            assertEquals(userCreateEditDto.getBirthDate(), it.getBirthDate());
            assertEquals(userCreateEditDto.getFirstname(), it.getFirstname());
            assertEquals(userCreateEditDto.getLastname(), it.getLastname());
            //Енамы сравниваются через assertSame
            assertSame(userCreateEditDto.getRole(), it.getRole());
            assertEquals(userCreateEditDto.getCompanyId(), it.getCompany().getId());
        });

    }

    @Test
    void delete(){
//        assertFalse(userService.delete(-125L));
        assertTrue(userService.delete(1L));
    }
}
