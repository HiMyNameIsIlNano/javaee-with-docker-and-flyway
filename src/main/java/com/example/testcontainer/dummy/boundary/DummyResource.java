package com.example.testcontainer.dummy.boundary;

import com.example.testcontainer.dummy.entity.Dummy;
import com.example.testcontainer.dummy.entity.Dummy.DummyBuilder;
import com.example.testcontainer.dummy.entity.DummyService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/dummy")
public class DummyResource {

    private DummyService dummyService;

    public DummyResource() {
    }

    @Inject
    public DummyResource(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @GET
    @Path("/save")
    @Produces("text/plain")
    public Response save() {
        final Dummy dummy = DummyBuilder.forCreation()
                .withText("dummy")
                .build();

        dummyService.save(dummy);
        return Response.ok(dummy).build();
    }
}