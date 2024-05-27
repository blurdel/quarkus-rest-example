package com.blurdel;

import com.blurdel.rest.services.GreetingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @Inject
    private GreetingService service;


    @GET
    @Path("/greet/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting(String name) {
        return service.getGreeting(name);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return service.getGreeting();
    }

}
