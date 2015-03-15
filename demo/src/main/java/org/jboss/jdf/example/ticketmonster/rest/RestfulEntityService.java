package org.jboss.jdf.example.ticketmonster.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Created by vineet on 15/03/15.
 */
public interface RestfulEntityService<T> {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<T> getAll(@Context UriInfo uriInfo);

    List<T> getAll(MultivaluedMap<String, String> queryParameters);

    @GET
    @Path("/count")
    @Produces(MediaType.APPLICATION_JSON)
    Map<String, Long> getCount(@Context UriInfo uriInfo);

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    T getSingleInstance(@PathParam("id") Long id);
}
