package com.mndev.spring.config;

import com.mndev.spring.database.pool.ConnectionPool;
import com.mndev.spring.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//аннотации java конфигурации
@Configuration
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "com.mndev.spring")
public class ApplicationConfiguration {

    //создание бина. В этом случае создание второго бина класса ConnectionPool, тк в самом классе
    //уже создан первый бин pool1
    @Bean("pool2")
    // inject property через параметры метода
    public ConnectionPool pool2(@Value("${db.username}") String username){
        return new ConnectionPool(username, 20);
    }

//    @Bean
//    public UserRepository userRepository(ConnectionPool pool2){
//        return new UserRepository(pool2);
//    }
}
