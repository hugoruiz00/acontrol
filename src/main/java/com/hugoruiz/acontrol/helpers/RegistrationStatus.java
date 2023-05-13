/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.helpers;

/**
 *
 * @author LENOVO
 */
public final class RegistrationStatus {

    private RegistrationStatus() {}

    private static boolean isPersonAdded, isPaymentAdded;

    public static boolean isPersonAdded() {
        return isPersonAdded;
    }

    public static void setIsPersonAdded(boolean isPersonAdded) {
        RegistrationStatus.isPersonAdded = isPersonAdded;
    }
    
    public static boolean isPaymentAdded() {
        return isPaymentAdded;
    }

    public static void setIsPaymentAdded(boolean isPaymentAdded) {
        RegistrationStatus.isPaymentAdded = isPaymentAdded;
    }
}
