/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.helpers.MovementStatus;
import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;

/**
 *
 * @author LENOVO
 */
public class RemovePersonController {
    @FXML
    private RadioButton permissionRadio, definitiveRadio;
    
    @FXML
    private Label message;
    
    @FXML
    private void initialize() {
        definitiveRadio.setOnAction(event -> {
            if (definitiveRadio.isSelected()) {
                  message.setVisible(true);
            }
        });
        
        permissionRadio.setOnAction(event -> {
            if (permissionRadio.isSelected()) {
                  message.setVisible(false);
            }
        });
    }
    
    @FXML
    private void updatePersonStatus(ActionEvent event) throws IOException {
        if (permissionRadio.isSelected() || definitiveRadio.isSelected()) {
            boolean isSaved = true;
            if (isSaved) {
                MovementStatus.setIsPersonRemoved(true);
                //alertText.setText("Cambio realizado con éxito");
                delayWindowClose(event);
            }
        }
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
