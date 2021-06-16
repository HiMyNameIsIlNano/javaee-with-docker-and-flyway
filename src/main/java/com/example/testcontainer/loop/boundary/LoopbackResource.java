package com.example.testcontainer.loop.boundary;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.jboss.logging.Logger;

@Path("loopback")
public class LoopbackResource {

    private static final Logger LOGGER = Logger.getLogger(LoopbackResource.class.getName());

    @GET
    @Produces("text/plain")
    @Path("/{text}")
    public Response loopback(@PathParam("text") @NotNull @NotBlank final String text) {
        LOGGER.info("Received text: " + text);

        return Response.ok(text).build();
    }

}
