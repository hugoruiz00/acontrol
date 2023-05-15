/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PersonPaymentDao;
import com.hugoruiz.acontrol.model.PersonPayment;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxTableCell;

/**
 *
 * @author LENOVO
 */
public class PaidCheckBoxTableCell extends CheckBoxTableCell<PersonPayment, Boolean> {    
    private PersonPaymentDao personPaymentDao;
    private Label totalPayment;
    
    public PaidCheckBoxTableCell(PersonPaymentDao personPaymentDao, Label totalPayment){
        this.personPaymentDao = personPaymentDao;
        this.totalPayment = totalPayment;
    }
    
    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(item);
            setGraphic(checkBox);
            
            checkBox.setOnAction(event -> {
                PersonPayment personPayment = getTableView().getItems().get(getIndex());
                personPayment.setIsPaid(checkBox.isSelected());
                
                boolean isSaved = new PersonPaymentDao().updatePersonPayment(personPayment);
                if(isSaved){
                    float total = 0;
                    for (PersonPayment pp : personPaymentDao.getUnpaidPersonPaymentsByPerson(personPayment.getPerson())) {
                        total += pp.getPayment().getAmount();
                    }
                    totalPayment.setText("" + total);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No se ha podido actualizar el estado del pago");
                    alert.showAndWait();
                    checkBox.setSelected(!checkBox.isSelected());
                }
            });
        }
    }
}
