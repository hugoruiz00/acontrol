/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author LENOVO
 */
@Embeddable
class PersonPaymentId implements Serializable{
    @Column(name = "person_id")
    private Long personId;
    
    @Column(name = "payment_id")
    private Long paymentId;
    
    public PersonPaymentId() {}
    
    public PersonPaymentId(Long personId, Long paymentId) {
        this.personId = personId;
        this.paymentId = paymentId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        PersonPaymentId that = (PersonPaymentId) o;
        return Objects.equals(personId, that.personId) &&
               Objects.equals(paymentId, that.paymentId);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(personId, paymentId);
    }
}
