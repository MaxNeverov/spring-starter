package com.mndev.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
//аспекты должны быть бинами
@Component
public class FirstAspect {

/*
@within - проверяет аннотацию на уровне классов
 */
//    @Pointcut("@within(org.springframework.stereotype.Controller)")
//    public void isControllerLayer(){}
//
//    /*
//    within - проверяет имя класса
//     */
//    @Pointcut("within(com.mndev.spring.service.*Service)")
//    public void isServiceLayer(){}
//
//    /*
//    this - все классы которые реализуют интерфейс repository(AOP рокси)
//    target - все классы которые реализуют интерфейс repository(target)
//     */
//    @Pointcut("this(org.springframework.data.repository)")
////    @Pointcut("target(org.springframework.data.repository)")
//    public void isRepositoryLayer(){}
//
//    /*
//    @annotation - проверка аннотации на уровне методов
//
//    Также возможно объединять несколько условий
//     */
//    @Pointcut("isControllerLayer() && @annotation(org.springframework.stereotype.Controller)")
//    public void hasController(){}
//
//
//    /*
//    args - проверяет параметры у метода
//    .. - все равно и на количество и на тип параметров
//    * - все равно на тип параметра
//
//     */
//    @Pointcut("args(org.springframework.ui.Model,..)")
//    public void hasParameter(){}
//
//    /*
//    @args - проверяет аннотации над указанным типом!!! параметров, не над самим параметром
//     */
////    @Pointcut("@args()")
////    public void hasUserInfoParamAnnotation(){}
//
//
//    /*
//    bean - проверка на имя бина
//     */
//    @Pointcut("bean(userService)")
//    public void isServiceLayerBean(){}
//
///*
//execution(модификатор-доступа? возвращаемый-тип название-класса?.название-класса-и-метода(param-pattern) throws-pattern)
// */
    @Pointcut("execution(public * com.mndev.spring.database.repository.*Repository.findById(*))")
    public void anyFindByIdServiceMethod(){}


    @Before("anyFindByIdServiceMethod()")
    public void addLogging(){
        log.info("invoked findByIdMethod");
    }
}
