package com.mndev.spring.http.controller;

import com.mndev.spring.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    //Вывод страницы формы
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

//    //получение данных
//    @PostMapping("/login")
//    /*
//    Если страница статическая, то можно не вызывать класс ModelAndView
//     использовать String, который возвращает название View
//     */
//    public String login(@ModelAttribute("login") LoginDto loginDto) {
////        return "user/login";
//        //перенаправление на другой сайт
//        return "redirect:https://google.com";
//    }
}
