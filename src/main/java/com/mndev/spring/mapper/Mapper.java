package com.mndev.spring.mapper;

public interface Mapper<F, T> {

    T mapFirstToSecond(F object);

    default T mapSecondToFirst(F fromObject, T toObject) {
        return toObject;
    }
}
