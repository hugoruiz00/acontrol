/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author LENOVO
 */
public class SceneController {

    private static Parent main;

    public static void getInitialScene(Stage stage) throws IOException {
        main = FXMLLoader.load(App.class.getResource("view/home.fxml"));
        Scene scene = new Scene(main, 900, 500);
        stage.setTitle("AControl");
        stage.setScene(scene);
        stage.show();
    }

    public static void getHomeScene(ActionEvent event) throws IOException {
        changeScreen(event, "view/home.fxml");
    }

    public static void getPersonScene(ActionEvent event) throws IOException {
        changeScreen(event, "view/person.fxml");
    }
    
    public static void getPaymentScene(ActionEvent event) throws IOException {
        changeScreen(event, "view/payment.fxml");
    }

    private static void changeScreen(ActionEvent event, String path) throws IOException {
        main = FXMLLoader.load(App.class.getResource(path));
        Scene visitScene = new Scene(main,900, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(visitScene);
        window.show();
    }

    public static void close(ActionEvent actionEvent) {
        Node  source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
