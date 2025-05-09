package com.mndev.spring.mapper;

import com.mndev.spring.database.entity.Company;
import com.mndev.spring.dto.CompanyReadDto;
import org.springframework.stereotype.Component;

//Чтобы суметь заинжектить этот класс в другом классе
@Component
public class CompanyReadMapper implements Mapper<Company, CompanyReadDto> {
    @Override
    public CompanyReadDto mapFirstToSecond(Company object) {
        return new CompanyReadDto(
                object.getId(),
                object.getName());
    }
}
