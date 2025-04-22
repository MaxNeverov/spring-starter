package com.mndev.spring.service;

import com.mndev.spring.database.entity.Company;
import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.dto.CompanyReadDto;
import com.mndev.spring.listner.entity.EntityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

//UNIT TEST
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {


    private static final Integer ID = 1;
    @Mock
    private UserService userService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @InjectMocks
    private CompanyService companyService;

    @Test
    void findById() {
        //возвращай Optional.of(new Company(ID)), когда вызывается вложенный метод
        Mockito.doReturn(Optional.of(new Company(ID, null, Collections.emptyMap()))).when(companyRepository).findById(ID);

        //вызываем основной метод
        Optional<CompanyReadDto> actualResult = companyService.findById(ID);

        //проверяет существует ли актуальный результат, выдает исключение если нет
        assertTrue(actualResult.isPresent());

        //ожидаемый результат
        CompanyReadDto expectRes = new CompanyReadDto(ID, null);

        //Если существует актуальный результат, то сравниваем актуальный и ожидаемый результаты
        actualResult.ifPresent(actual -> assertEquals(expectRes,actual));

        //проверка на вызов метода publishEvent у объекта eventPublisher
        Mockito.verify(eventPublisher).publishEvent(any(EntityEvent.class));

        //проверка, что кроме указанных выше вызовов методов, никаких других вызовов объектов не было
        Mockito.verifyNoMoreInteractions(eventPublisher, userService, companyRepository);

    }
}