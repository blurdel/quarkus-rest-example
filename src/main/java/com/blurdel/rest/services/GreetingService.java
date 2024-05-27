package com.blurdel.rest.services;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    public String getGreeting() {
        return "Hello!";
    }

    public String getGreeting(String name) {
        return "Hello " + name;
    }
}
