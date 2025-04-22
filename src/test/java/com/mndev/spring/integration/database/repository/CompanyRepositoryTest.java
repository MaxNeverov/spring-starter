package com.mndev.spring.integration.database.repository;

import com.mndev.spring.database.entity.Company;
import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.integration.IntegrationTestBase;
import com.mndev.spring.integration.annotation.IT;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor
//по умолчанию установлени откат изменений БД @Rollback(Если надо сохранить изменения - @Commit)
//@Transactional
//@Commit
class CompanyRepositoryTest extends IntegrationTestBase  {

    private static final Integer COMPANY_ID = 5;
    private final EntityManager entityManager;
    private final CompanyRepository companyRepository;

    @Test
    @Disabled
    void delete() {
        Optional<Company> company = companyRepository.findById(COMPANY_ID);
        assertTrue(company.isPresent());
        company.ifPresent(companyRepository::delete);
        entityManager.flush();
        assertTrue(companyRepository.findById(COMPANY_ID).isEmpty());

    }

//    @Test
//    void findById() {
//        Optional<Company> company = companyRepository.findById("Google");
////        Company company = entityManager.find(Company.class, 1);
////        assertNotNull(company);
//        assertTrue(company.isPresent());
//        assertThat(company.get().getLocales()).hasSize(2);
//    }

    @Test
    void save(){
        Company company = Company.builder()
                .name("Test")
                .locales(Map.of(
                        "ru", "Apple описание",
                        "en", "Apple description"
                ))
                .build();
        entityManager.persist(company);
        assertNotNull(company.getId());
    }
}