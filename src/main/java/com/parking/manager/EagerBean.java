package com.parking.manager;

import org.springframework.stereotype.Component;

@Component
public class EagerBean {
    public EagerBean() {
        System.out.println("EagerBean started :D");
    }
}
