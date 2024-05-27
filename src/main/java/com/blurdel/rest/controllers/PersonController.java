package com.blurdel.rest.controllers;

import io.quarkus.runtime.Application;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/person")
public class PersonController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Hello blurdel";
    }

}
