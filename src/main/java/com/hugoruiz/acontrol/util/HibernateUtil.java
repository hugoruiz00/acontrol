/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.util;

import com.hugoruiz.acontrol.model.Payment;
import com.hugoruiz.acontrol.model.Person;
import com.hugoruiz.acontrol.model.PersonPayment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author LENOVO
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                MetadataSources metadataSources = new MetadataSources(
                    new StandardServiceRegistryBuilder().configure().build()
                );

                metadataSources.addAnnotatedClass(Person.class);
                metadataSources.addAnnotatedClass(Payment.class);
                metadataSources.addAnnotatedClass(PersonPayment.class);

                sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }

}
