package com.mndev.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //настройка логин формы
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().disable()//отключение шифрования
                .authorizeRequests()

                .antMatchers("/login", "/users/registration","/v3/api-docs/**","/swagger/ui/**").permitAll()
                .antMatchers("/users/{\\d+}/delete").hasAuthority("ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/users").hasAnyAuthority("ADMIN","USER")
                .and()

                //ЗДЕСЬ НЕ РАБОТАЕТ АНОНИМ----------------------------------------------
//                .authorizeHttpRequests(urlConfig -> urlConfig
//                        .antMatchers("/login", "/users/registration","/v3/api-docs/**","/swagger/ui/**").permitAll()
//                        .antMatchers("/users/{\\d+}/delete").hasAuthority("ADMIN")
//                        .antMatchers("/admin/**").hasAuthority("ADMIN")
//                        .anyRequest().authenticated())
                //-------------------------------------------------------------------------

//                .httpBasic(Customizer.withDefaults());
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .formLogin(login -> login
                        .loginPage("/login")
                        //при успешном логине переводит по ссылке
                        .defaultSuccessUrl("/users"));
    }

    //метод шифрования пароля
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
