package com.mndev.spring.integration;

import com.mndev.spring.integration.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;

@IT
//путь до sql с данными таблиц
@Sql({
        "classpath:sql/data.sql"
})
//класс запуска контейнера перед всеми тестами
public abstract class IntegrationTestBase {

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.1");

    //старт контейнера
    @BeforeAll
    static void runContainer() {
        container.start();
    }

    //динамический url (логин и пароль в пропертях)
    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry. add("spring.datasource.url", container::getJdbcUrl);
    }
}
