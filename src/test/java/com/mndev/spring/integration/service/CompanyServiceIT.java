package com.mndev.spring.integration.service;

import com.mndev.spring.ApplicationRunner;
import com.mndev.spring.database.entity.Company;
import com.mndev.spring.dto.CompanyReadDto;
import com.mndev.spring.integration.annotation.IT;
import com.mndev.spring.listner.entity.EntityEvent;
import com.mndev.spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//INTEGRADE TEST
//Для Integrade test лучше подключаться к СПРИНГУ для связывания бинов
//@SpringBootTest
//@ActiveProfiles("test")


@IT//кастомная аннотация
@RequiredArgsConstructor
public class CompanyServiceIT {

    private static final Integer ID = 1;

    //связывание бина
    private final CompanyService companyService;

    @Test
    void findById(){

        //вызываем основной метод
        Optional<CompanyReadDto> actualResult = companyService.findById(ID);

        //проверяет существует ли актуальный результат, выдает исключение если нет
        assertTrue(actualResult.isPresent());

        //ожидаемый результат
        CompanyReadDto expectRes = new CompanyReadDto(ID, null);

        //Если существует актуальный результат, то сравниваем актуальный и ожидаемый результаты
        actualResult.ifPresent(actual -> assertEquals(expectRes,actual));

    }
}
