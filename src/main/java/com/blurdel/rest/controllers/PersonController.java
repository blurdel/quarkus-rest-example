package com.blurdel.rest.controllers;

import com.blurdel.rest.model.Person;
import com.blurdel.rest.services.IPersonService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Path("/person")
public class PersonController {

    @Inject
    private IPersonService service;

    private static final Logger LOG = LoggerFactory.getLogger(PersonController.class);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets the full list of Person objects")
    public Response list() {
        LOG.info("PersonController returning list");
        return Response.ok().entity(service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Gets a Person by id")
    public Response getOne(@PathParam("id") final Long id) {
        LOG.info("PersonController GET called for id: {}", id);
        Optional<Person> found = service.get(id);
        if (found.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOG.debug("PersonController GET Response: {}", found.get().toString());
        return Response.ok().entity(found).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Creates a new Person object")
    public Response addNew(final Person payload) {
        LOG.info("PersonController POST called with payload: {}", payload.toString());
        Optional<Person> added = service.add(payload);
        if (added.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        LOG.debug("PersonController POST Response: {}", added.get().toString());
        return Response.status(Response.Status.CREATED).entity(added).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Updates an existing Person by id")
    public Response update(@PathParam("id") final Long id, final Person payload) {
        LOG.info("PersonController PUT called for id {} with payload {}", id, payload.toString());
        Optional<Person> updated = service.update(payload);
        if (updated.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOG.debug("PersonController PUT Response: {}", updated.get().toString());
        return Response.status(Response.Status.OK).entity(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Deletes a Person by id")
    public Response delete(@PathParam("id") final Long id) {
        LOG.info("PersonController DELETE called for id {}", id);
        Optional<Person> deleted = service.delete(id);
        if (deleted.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        LOG.debug("PersonController DELETE Response: {}", deleted.get().toString());
        return Response.status(Response.Status.OK).entity(deleted).build();
    }

}
