/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.App;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class NewWindowController {

    public static void getNewPersonWindow() throws IOException {
        getPopUpWindow("view/addPerson.fxml");
    }
    
    public static void getNewPaymentWindow() throws IOException {
        getPopUpWindow("view/addPayment.fxml");
    }

    private static void getPopUpWindow(String path) throws IOException {
        Stage stage = new Stage();
        Pane main = FXMLLoader.load(App.class.getResource(path));
        stage.setScene(new Scene(main));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("AControl");
        stage.getScene();
        stage.showAndWait();
    }
}