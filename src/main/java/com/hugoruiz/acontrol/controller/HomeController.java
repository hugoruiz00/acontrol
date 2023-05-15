package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PersonDao;
import com.hugoruiz.acontrol.dao.PersonPaymentDao;
import com.hugoruiz.acontrol.model.Person;
import com.hugoruiz.acontrol.model.PersonPayment;
import java.io.IOException;
import java.time.LocalDate;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.input.MouseEvent;

public class HomeController {
    @FXML
    private Label title, personSelected, totalPayment;
    
    @FXML
    private TextField searchBar;
    
    @FXML
    private TableView<Person> personsTable;
    
    @FXML
    private TableView<PersonPayment> paymentsTable;
    
    @FXML
    private TableColumn<?, ?> numCol, nameCol, lastNameCol, addressCol;
    
    @FXML
    private TableColumn<PersonPayment, String> descriptionCol;
    
    @FXML
    private TableColumn<PersonPayment, LocalDate> dateCol;
    
    @FXML
    private TableColumn<PersonPayment, Float> amountCol;
            
    @FXML
    private TableColumn<PersonPayment, Boolean> isPaidCol;
        
    PersonDao personDao = new PersonDao();
    PersonPaymentDao personPaymentDao = new PersonPaymentDao();
    
    ObservableList<Person> personsObservableList = FXCollections.observableArrayList();
    ObservableList<PersonPayment> paymentsObservableList = FXCollections.observableArrayList();
    
    @FXML
    void showPersonScreen(ActionEvent event) throws IOException {
        SceneController.getPersonScene(event);
    }
    
    @FXML
    void showPaymentScreen(ActionEvent event) throws IOException {
        SceneController.getPaymentScene(event);
    }
    
    @FXML
    void payTotal(ActionEvent event) throws IOException{
        Person person = personsTable.getSelectionModel().getSelectedItem();
        if (person != null) {
            for (PersonPayment personPayment : personPaymentDao.getUnpaidPersonPaymentsByPerson(person)) {
                personPayment.setIsPaid(true);
                personPaymentDao.updatePersonPayment(personPayment);   
            }
            SceneController.getHomeScene(event);
        }
    }
    
    @FXML
    void fillPaymentsTable(MouseEvent event) {
        Person person = personsTable.getSelectionModel().getSelectedItem();
        if (person != null) {
            paymentsObservableList.clear();
            paymentsObservableList.addAll(person.getPayments());
            paymentsTable.setItems(paymentsObservableList);
            
            personSelected.setText(person.getName()+" "+person.getLastName());
            float total = 0;
            for (PersonPayment personPayment : personPaymentDao.getUnpaidPersonPaymentsByPerson(person)) {
                total += personPayment.getPayment().getAmount();
            }
            totalPayment.setText("" + total);
        }
    }
    
    @FXML
    private void initialize() {
        setTexts();
        setObservableList();
        configureTables();
        fillTable();
        //exitBtn.setOnAction(SceneController::close);
    }

    private void setTexts() {
        title.setText("Principal");
    }

    private void setObservableList() {
        personsObservableList.clear();
        personsObservableList.addAll(personDao.getPersons());
    }

    private void configureTables() {
        numCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        descriptionCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPayment().getDescription()));
        dateCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPayment().getDate()));
        amountCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPayment().getAmount()));
        isPaidCol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().isIsPaid()));
        isPaidCol.setCellFactory(column -> new PaidCheckBoxTableCell(personPaymentDao, totalPayment));
    }

    private void fillTable() {
        personsTable.setItems(getSortedList());
    }

    private SortedList<Person> getSortedList() {
        SortedList<Person> sortedList = new SortedList<>(getFilteredList());
        sortedList.comparatorProperty().bind(personsTable.comparatorProperty());
        return sortedList;
    }

    private FilteredList<Person> getFilteredList() {
        FilteredList<Person> filteredList = new FilteredList<>(personsObservableList, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) ->
                filteredList.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else return String.valueOf(person.getNum()).contains(lowerCaseFilter);
                }));
        return filteredList;
    }
}
