package org.jboss.jdf.example.ticketmonster.rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_IMPLEMENTED;

/**
 * A RESTful service for accessing shipment carriers.
 */
@Stateless
@Path("/carriers")
public class CarrierService {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    @POST
    @Consumes("application/json")
    public Response create(Carrier carrierBean)
    {
        // TODO store carrier in the database
        return Response.status(NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Long id)
    {
        // TODO get required carrier from the database
        return Response.status(NOT_IMPLEMENTED).build();
    }

    @GET
    @Produces("application/json")
    public List<Carrier> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
    {
        final List<Carrier> results = new ArrayList<Carrier>();
        // TODO get all carriers from the database
        return results;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Long id, Carrier carrierBean)
    {
        // TODO update carrier in the database
        return Response.status(NOT_IMPLEMENTED).build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id)
    {
        // TODO delete carrier in the database
        return Response.status(NOT_IMPLEMENTED).build();
    }
}
