package com.mndev.spring.listner.entity;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EntitListener {

    @EventListener
    public void acceptEntity(EntityEvent entityEvent) {
        //Слушатель события EntityEvent
        //Код слушателя
        System.out.println("entity: " + entityEvent);
    }
}
