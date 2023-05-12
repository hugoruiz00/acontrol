/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PaymentDao;
import com.hugoruiz.acontrol.helpers.RegistrationStatus;
import com.hugoruiz.acontrol.model.Payment;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author LENOVO
 */
public class PaymentController {
    @FXML
    private Label title;
    
    @FXML
    private TextField searchBar;
    
    @FXML
    private TableView<Payment> paymentsTable;
    
    @FXML
    private TableColumn<?, ?> descriptionCol, amountCol, dateCol;
    
    PaymentDao paymentDao = new PaymentDao();
    
    ObservableList<Payment> paymentsObservableList = FXCollections.observableArrayList();
    
    @FXML
    void showHomeScreen(ActionEvent event) throws IOException {
        SceneController.getHomeScene(event);
    }
    
    @FXML
    void showPersonScreen(ActionEvent event) throws IOException {
        SceneController.getPersonScene(event);
    }
    
    @FXML
    private void newWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewPersonWindow();
        if(RegistrationStatus.isPersonAdded()) {
            refreshScreen(event);
            RegistrationStatus.setIsPersonAdded(false);
        }
    }
    
    @FXML
    private void refreshScreen(ActionEvent event) throws IOException {
        SceneController.getPaymentScene(event);
    }
    
    @FXML
    private void initialize() {
        setTexts();
        setObservableList();
        configureTables();
        fillTable();
    }

    private void setTexts() {
        title.setText("Cooperaciones");
    }

    private void setObservableList() {
        paymentsObservableList.clear();
        //paymentsObservableList.addAll(personDao.getPersons());
    }

    private void configureTables() {
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void fillTable() {
        paymentsTable.setItems(getSortedList());
    }

    private SortedList<Payment> getSortedList() {
        SortedList<Payment> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(paymentsTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Payment> getFilteredList() {
        FilteredList<Payment> filteredList = new FilteredList<>(paymentsObservableList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(payment -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (payment.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (payment.getDate().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else return String.valueOf(payment.getAmount()).contains(lowerCaseFilter);
                }));
        return filteredList;
    }
}
