/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PersonDao;
import com.hugoruiz.acontrol.helpers.RegistrationStatus;
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
public class AddPersonController {
    
    @FXML
    private TextField num, name, lastName, address;

    @FXML
    private DatePicker birthDate;

    @FXML
    private Label alertText;
    
    @FXML
    private void validateDigits(KeyEvent event) {
        if (!Character.isDigit(event.getCharacter().charAt(0))) {
            event.consume();
        }
    }

    @FXML
    private void saveNewPerson(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Person person = createPersonFromInput();
            boolean isSaved = new PersonDao().createPerson(person);
            if (isSaved) {
                RegistrationStatus.setIsPersonAdded(true);
                alertText.setText("Persona agregada");
                delayWindowClose(event);
            }
        }
    }

    private Person createPersonFromInput() {
        Person person = new Person();
        person.setNum(Integer.parseInt(num.getText().trim()));
        person.setName(name.getText().trim());
        person.setLastName(lastName.getText().trim());
        person.setBirthdate(birthDate.getValue());
        person.setAddress(address.getText().trim());
        person.setStatus("Activo");

        return person;
    }

    private boolean validateInputs() {
        if (num.getText().trim().equals("")) {
            alertText.setText("*El número es obligatorio");
            return false;
        }
        if (birthDate.getValue() == null) {
            alertText.setText("*La fecha de nacimiento es obligatoria");
            return false;
        }
        if (name.getText().trim().equals("")) {
            alertText.setText("*El nombre es obligatorio");
            return false;
        }
        if (lastName.getText().trim().equals("")) {
            alertText.setText("*El apellido es obligatorio");
            return false;
        }
        if (address.getText().trim().equals("")) {
            alertText.setText("*El barrio es obligatorio");
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
