package org.jboss.jdf.example.ticketmonster.rest;

import org.jboss.jdf.example.ticketmonster.model.MailCarrier;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.util.ArrayList;
import java.util.List;

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
        MailCarrier entity = carrierBean.entityFromBean(null, em);
        em.persist(entity);
        return Response.created(UriBuilder.fromResource(CarrierService.class).path(String.valueOf(entity.getId())).build()).build();
    }

    @GET
    @Path("/{id:[0-9][0-9]*}")
    @Produces("application/json")
    public Response findById(@PathParam("id") Long id)
    {
        TypedQuery<MailCarrier> findByIdQuery = em.createQuery("SELECT DISTINCT e FROM MailCarrier e WHERE e.id = :entityId ORDER BY e.id", MailCarrier.class);
        findByIdQuery.setParameter("entityId", id);
        MailCarrier entity;
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            entity = null;
        }
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Carrier bean = new Carrier(entity);
        return Response.ok(bean).build();
    }

    @GET
    @Produces("application/json")
    public List<Carrier> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
    {
        TypedQuery<MailCarrier> findAllQuery = em.createQuery("SELECT DISTINCT e FROM MailCarrier e ORDER BY e.id", MailCarrier.class);
        if (startPosition != null)
        {
            findAllQuery.setFirstResult(startPosition);
        }
        if (maxResult != null)
        {
            findAllQuery.setMaxResults(maxResult);
        }
        final List<MailCarrier> searchResults = findAllQuery.getResultList();
        final List<Carrier> results = new ArrayList<Carrier>();
        for(MailCarrier searchResult: searchResults) {
            Carrier dto = new Carrier(searchResult);
            results.add(dto);
        }
        return results;
    }

    @PUT
    @Path("/{id:[0-9][0-9]*}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Long id, Carrier carrierBean)
    {
        TypedQuery<MailCarrier> findByIdQuery = em.createQuery("SELECT DISTINCT e FROM MailCarrier e WHERE e.id = :entityId ORDER BY e.id", MailCarrier.class);
        findByIdQuery.setParameter("entityId", id);
        MailCarrier entity;
        try {
            entity = findByIdQuery.getSingleResult();
        } catch (NoResultException nre) {
            entity = null;
        }
        entity = carrierBean.entityFromBean(entity, em);
        em.merge(entity);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id:[0-9][0-9]*}")
    public Response deleteById(@PathParam("id") Long id)
    {
        MailCarrier entity = em.find(MailCarrier.class, id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        em.remove(entity);
        return Response.noContent().build();
    }
}
