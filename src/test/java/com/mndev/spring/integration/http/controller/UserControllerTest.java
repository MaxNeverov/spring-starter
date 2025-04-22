package com.mndev.spring.integration.http.controller;

import com.mndev.spring.database.entity.Role;
import com.mndev.spring.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

//имитация http запросов
@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerTest extends IntegrationTestBase {

    //имитация http запросов
    private final MockMvc mockMvc;

//    @BeforeEach
//    void init(){
//
////        List<GrantedAuthority> roles = Arrays.asList(Role.ADMIN, Role.USER);
//        User testUser = new User("test@gmail.com", "test", roles);
////        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(testUser, testUser.getPassword(), roles);
////
////        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
////        securityContext.setAuthentication(authenticationToken);
////        SecurityContextHolder.setContext(securityContext);
//    }

    @Test
    @WithMockUser(username = "test@gmail.com", password = "test", authorities = {"ADMIN", "USER"})
    void findAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                //проверка на статус
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                //на имя вида
                .andExpect(MockMvcResultMatchers.view().name("user/users"))
                //проверка существует ли аттрибут
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
                //проверка количества аттрибутоа (5 юзеров)
//                .andExpect(MockMvcResultMatchers.model().attribute("users", hasSize(5)));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                .param("username", "test@gmail.com")
                .param("firstname", "test")
                .param("lastname", "testtest")
                .param("role", "ADMIN")
                .param("companyId", "1")
                //Для даты нужен конвертор для маппинга(можно написать в пропертях MVC)
                        .param("birthDate", "2020-01-01")
        )
                .andExpectAll(
                        MockMvcResultMatchers.status().is3xxRedirection(),
                        MockMvcResultMatchers.redirectedUrlPattern("/users/{\\d+}")

                );


    }
}