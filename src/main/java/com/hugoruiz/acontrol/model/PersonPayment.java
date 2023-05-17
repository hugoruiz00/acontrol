/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

/**
 *
 * @author LENOVO
 */
@Entity
@Table(name = "person_payment")
public class PersonPayment {
    @EmbeddedId
    private PersonPaymentId id;
       
    @ManyToOne
    @MapsId("personId")
    private Person person;
    
    @ManyToOne
    @MapsId("paymentId")
    private Payment payment;
    
    @Column(name="is_paid")
    private boolean isPaid;
    
    public PersonPayment(){}
    
    public PersonPayment(Person person, Payment payment) {
        this.person = person;
        this.payment = payment;
        this.id = new PersonPaymentId(person.getId(), payment.getId());
    }
    
    public PersonPayment(Person person, Payment payment, boolean isPaid) {
        this.person = person;
        this.payment = payment;
        this.isPaid = isPaid;
        this.id = new PersonPaymentId(person.getId(), payment.getId());
    }

    public PersonPaymentId getId() {
        return id;
    }

    public void setId(PersonPaymentId id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
}
