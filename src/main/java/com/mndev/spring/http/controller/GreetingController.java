package com.mndev.spring.http.controller;

import com.mndev.spring.dto.UserReadDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
//Для добавления общего пути
//@RequestMapping("/api/v1")

//Добавляет Session Attribute
@SessionAttributes({"user"})
public class GreetingController {

    //при вводе url /hello будет исполнен метод GET у метода hello
//    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    /*
    вместо громоздкой аннотации можно использовать
    @GetMapping
    @PutMapping
    @DeleteMapping
     */
    @GetMapping("/hello")
    //можно прописать в скобках параметр model, чтобы не создавать его через new (dependency injection)
    public String hello(Model model,
                        HttpServletRequest request,
                        UserReadDto userReadDto) {

        //request Attribute
        model.addAttribute("user", userReadDto);
        return "greeting/hello";

    }

    @RequestMapping(value = "/hello/{id}", method = RequestMethod.GET)
    public ModelAndView hello2(ModelAndView modelAndView, HttpServletRequest request,
                              @RequestParam("age") Integer age,
                              @RequestHeader("accept") String accept,
                              @CookieValue("JSESSIONID") String jsessionId,
                              @PathVariable("id") Integer id) {
        modelAndView.setViewName("greeting/hello");
        return modelAndView;

    }

    @GetMapping("/bye")
    /*
    Установка атрибута сессии, если сначала открыть Hello, то появится Request Attribute Ivan
    который станет Session Attribute и его можно будет использовать в bye.html
     */
    public String bye(@SessionAttribute("user") UserReadDto user, Model model) {
        return "greeting/bye";

    }
}
