/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.dao;

import com.hugoruiz.acontrol.model.Payment;
import com.hugoruiz.acontrol.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

/**
 *
 * @author LENOVO
 */
public class PaymentDao {
    public Boolean createPayment(Payment payment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(payment);
            transaction.commit();
            return transaction.getStatus() == TransactionStatus.COMMITTED;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        }
        return false;
    }
    
    public Payment getPayment(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(Payment.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Payment> getPayments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Payment", Payment.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }
}
