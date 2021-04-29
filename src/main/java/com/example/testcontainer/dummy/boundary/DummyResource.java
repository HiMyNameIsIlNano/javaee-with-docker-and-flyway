package com.example.testcontainer.dummy.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/dummy")
public class DummyResource {

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}