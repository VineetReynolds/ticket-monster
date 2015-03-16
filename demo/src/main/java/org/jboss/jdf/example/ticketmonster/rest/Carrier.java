package org.jboss.jdf.example.ticketmonster.rest;

import org.jboss.jdf.example.ticketmonster.model.MailCarrier;

import javax.persistence.EntityManager;

/**
 * This is a representation bean class to represent a shipment carrier
 */
public class Carrier {

    private long id;
    private String name;
    private String address;

    public Carrier() {
    }

    public Carrier(final MailCarrier entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.address = entity.getAddress();
        }
    }

    public MailCarrier entityFromBean(MailCarrier entity, EntityManager em) {
        if (entity == null) {
            entity = new MailCarrier();
        }
        entity.setName(this.name);
        entity.setAddress(this.address);
        return entity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
