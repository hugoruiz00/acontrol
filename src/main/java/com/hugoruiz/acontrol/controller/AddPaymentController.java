/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PaymentDao;
import com.hugoruiz.acontrol.dao.PersonDao;
import com.hugoruiz.acontrol.helpers.MovementStatus;
import com.hugoruiz.acontrol.model.Payment;
import com.hugoruiz.acontrol.model.Person;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 *
 * @author LENOVO
 */
public class AddPaymentController {
    @FXML
    private TextField description, amount;

    @FXML
    private DatePicker date;

    @FXML
    private Label alertText;
    
    @FXML
    private void validateDigits(KeyEvent event) {
        if (!Character.isDigit(event.getCharacter().charAt(0))) {
            event.consume();
        }
    }

    @FXML
    private void saveNewPaymentAndAddToPersons(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Payment payment = createPaymentFromInput();
            boolean isSaved = new PaymentDao().createPayment(payment);
            if (isSaved) {
                addPaymentToPersons(payment);
                MovementStatus.setIsPaymentAdded(true);
                alertText.setText("Cooperación agregada");
                delayWindowClose(event);
            }
        }
    }
    
    private void addPaymentToPersons(Payment payment) {
        PersonDao personDao = new PersonDao();
        for (Person person : personDao.getPersons()) {
            person.addPayment(payment);
            personDao.updatePerson(person);
        }
    }

    private Payment createPaymentFromInput() {
        Payment payment = new Payment();
        payment.setDescription(description.getText().trim());
        payment.setAmount(Float.parseFloat(amount.getText().trim()));
        payment.setDate(date.getValue());

        return payment;
    }

    private boolean validateInputs() {
        if (description.getText().trim().equals("")) {
            alertText.setText("*La descripción es obligatoria");
            return false;
        }
        if (amount.getText().trim().equals("")) {
            alertText.setText("*La cantidad es obligatorio");
            return false;
        }
        if (date.getValue() == null) {
            alertText.setText("*La fecha es obligatoria");
            return false;
        }

        return true;
    }

    private void delayWindowClose(ActionEvent event) {
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event2 -> closeWindow(event));
        delay.play();
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        SceneController.close(event);
    }
}
