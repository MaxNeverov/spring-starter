package com.mndev.spring;

import com.mndev.spring.config.ApplicationConfiguration;
import com.mndev.spring.database.entity.Company;
import com.mndev.spring.database.pool.ConnectionPool;
import com.mndev.spring.database.repository.CompanyRepository;
import com.mndev.spring.database.repository.UserRepository;
import com.mndev.spring.ioc.Container;
import com.mndev.spring.service.CompanyService;
import com.mndev.spring.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.management.MBeanServerFactory;
import java.lang.reflect.Proxy;
import java.util.Optional;

//аннотация запуска приложения на спринге
@SpringBootApplication
public class ApplicationRunner {
    public static void main(String[] args) {

/// //////////////////////////////////////////////////////////////////////////////////
        //создание объектов вручную

//        ConnectionPool connectionPool = new ConnectionPool();
//        UserRepository userRepository = new UserRepository(connectionPool);
//        CompanyRepository companyRepository = new CompanyRepository(connectionPool);
//        UserService userService = new UserService(userRepository, companyRepository);

        /// /////////////////////////////////////////////////////////////////////////
        //IoC - фреймворк сам создает объекты через контейнер
        //при IoC можно сразу создавать нужный объект не беспокоясь об его зависимостях

//        Container container = new Container();
//        ConnectionPool connectionPool = container.get(ConnectionPool.class);
//        UserRepository userRepository = container.get(UserRepository.class);
//        CompanyRepository companyRepository = container.get(CompanyRepository.class);

        ///////////////////////////////////////////////////////////////////////
//        Либо можно сразу создать нужный объект, не смотря на его зависимости (связи будуи созданы автоматически)

//        UserService userService = container.get(UserService.class);

/// /////////////////////////////////////////////////////////////////////////////////
        //Создание IoC с помощью СПРИНГа

        //путь к XML конфигу, если не используется java конфиг
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("111.xml")

        //путь к Java конфигу, ксли реализацция метаданных через аннотации
//        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
//
//            ConnectionPool pool1 = context.getBean("pool1", ConnectionPool.class);
//            System.out.println(pool1);
//
//            CompanyService pool3 = context.getBean("companyService", CompanyService.class);
//            System.out.println(pool3.findById(1));
//        }

/// ////////////////////////////////////////////////////////////////////////////////////////////////////
        //SPRING запуск приложения
        ConfigurableApplicationContext context = SpringApplication.run(ApplicationRunner.class, args);
//        System.out.println("Hello World!");
//        System.out.println(context.getBean("pool1"));

        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
//        Optional<Company> company = companyRepository.findById("Google");
//        company.ifPresent(System.out::println);





    }
}
