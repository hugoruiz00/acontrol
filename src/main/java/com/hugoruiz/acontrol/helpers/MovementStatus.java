/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.helpers;

/**
 *
 * @author LENOVO
 */
public final class MovementStatus {

    private MovementStatus() {}

    private static boolean isPersonAdded, isPaymentAdded, isPersonRemoved;

    public static boolean isPersonAdded() {
        return isPersonAdded;
    }

    public static void setIsPersonAdded(boolean isPersonAdded) {
        MovementStatus.isPersonAdded = isPersonAdded;
    }

    public static boolean isPersonRemoved() {
        return isPersonRemoved;
    }

    public static void setIsPersonRemoved(boolean isPersonRemoved) {
        MovementStatus.isPersonRemoved = isPersonRemoved;
    }
    
    public static boolean isPaymentAdded() {
        return isPaymentAdded;
    }

    public static void setIsPaymentAdded(boolean isPaymentAdded) {
        MovementStatus.isPaymentAdded = isPaymentAdded;
    }
}
