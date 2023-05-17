package com.hugoruiz.acontrol.controller;

import com.hugoruiz.acontrol.dao.PersonDao;
import com.hugoruiz.acontrol.helpers.MovementStatus;
import com.hugoruiz.acontrol.model.Person;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PersonController {
    @FXML
    private Label title;
    
    @FXML
    private TextField searchBar;
    
    @FXML
    private Button changeStatusBtn;
    
    @FXML
    private TableView<Person> personsTable;
    
    @FXML
    private TableColumn<?, ?> numCol, nameCol, lastNameCol, addressCol, statusCol;
    
    PersonDao personDao = new PersonDao();
    
    ObservableList<Person> personsObservableList = FXCollections.observableArrayList();
    
    @FXML
    void showHomeScreen(ActionEvent event) throws IOException {
        SceneController.getHomeScene(event);
    }
    
    @FXML
    void showPaymentScreen(ActionEvent event) throws IOException {
        SceneController.getPaymentScene(event);
    }
    
    @FXML
    void personSelected(MouseEvent event) {
        Person person = personsTable.getSelectionModel().getSelectedItem();
        if (person!=null && person.getStatus().equals("Activo")) {
            changeStatusBtn.setText("Dar permiso");
        }else if(person!=null && person.getStatus().equals("En permiso")){
            changeStatusBtn.setText("Reactivar");
        } 
    }
    
    @FXML
    private void newPersonWindow(ActionEvent event) throws IOException {
        NewWindowController.getNewPersonWindow();
        if(MovementStatus.isPersonAdded()) {
            refreshScreen(event);
            MovementStatus.setIsPersonAdded(false);
        }
    }
    
    @FXML
    private void updatePersonStatus(ActionEvent event) throws IOException {
        Person person = personsTable.getSelectionModel().getSelectedItem();
        if (person!=null && person.getStatus().equals("Activo")) {
            person.setStatus("En permiso");
            personDao.updatePerson(person);
            refreshScreen(event);
        }else if(person!=null && person.getStatus().equals("En permiso")){
            person.setStatus("Activo");
            personDao.updatePerson(person);
            refreshScreen(event);
        }
    }
    
    @FXML
    private void deletePerson(ActionEvent event) throws IOException {
        Person person = personsTable.getSelectionModel().getSelectedItem();
        if(person!=null){
            personDao.deletePerson(person);
            refreshScreen(event);
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
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
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