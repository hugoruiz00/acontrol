package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PersonDao;
import com.hugoruiz.acontrol.helpers.RegistrationStatus;
import com.hugoruiz.acontrol.model.Person;
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

public class PersonController {
    @FXML
    private Label title, stats;
    
    @FXML
    private TextField searchBar;
    
    @FXML
    private TableView<Person> personsTable;
    
    @FXML
    private TableColumn<?, ?> numCol, nameCol, lastNameCol, addressCol;
    
    PersonDao personDao = new PersonDao();
    
    ObservableList<Person> personsObservableList = FXCollections.observableArrayList();
    
    @FXML
    void showHomeScreen(ActionEvent event) throws IOException {
        SceneController.getHomeScene(event);
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
        SceneController.getPersonScene(event);
    }
    
    @FXML
    private void initialize() {
        setTexts();
        setObservableList();
        configureTables();
        fillTable();
    }

    private void setTexts() {
        title.setText("Personas");
        setDbInfo();
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

    private void setDbInfo() {
        //stats.setText(String.format("Total vets in database: %s", vetDao.getVetsNumber()));
        stats.setText("Test");
    }
}