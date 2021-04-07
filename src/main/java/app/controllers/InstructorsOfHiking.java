package app.controllers;

import app.models.HikingSchedule;
import app.models.HikingTable;
import app.models.InstructorsTable;
import app.models.Participants;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;

public class InstructorsOfHiking implements Initializable {

    private static List<String> role;

    @FXML
    private VBox vboxPane;

    @FXML
    private Button back;

    @FXML
    private VBox hikingTablePane;

    @FXML
    private TableView<HikingTable> hikingTable;
    @FXML
    private TableColumn idHiking;
    @FXML
    private TableColumn nameHiking;
    @FXML
    private TableColumn startHiking;

    @FXML
    private Button showInstructorsButton;

    @FXML
    private VBox instructorsTablePane;

    @FXML
    private TableView<InstructorsTable> instructorsTable;
    @FXML
    private TableColumn idInstructor;
    @FXML
    private TableColumn surnameInstructor;
    @FXML
    private TableColumn categoryInstructor;

    @FXML
    private TableView<InstructorsTable> participantsTable;
    @FXML
    private TableColumn idParticipant;
    @FXML
    private TableColumn surnameParticipant;
    @FXML
    private TableColumn categoryParticipant;

    @FXML
    private HBox hboxPane;

    @FXML
    private javafx.scene.control.Button changeInstructorButton = new Button("Заменить\nинструктора");
    @FXML
    private Button addParticipantButton = new Button("Добавить участника\nили инструктора");
    @FXML
    private Button deleteParticipantButton = new Button("Удалить\nучастника");

    @FXML
    private Label message = new Label();

    private HikingTable curHikingTable;
    private InstructorsTable curInstructorsTable;

    private static ObservableList<HikingTable> dataHikingTable;
    private static ObservableList<InstructorsTable> dataInstructorsTable;
    private static ObservableList<InstructorsTable> dataParticipantsTable;

    public InstructorsOfHiking() {
    }

    public static List<String> getRole() {
        return role;
    }

    public static void setRole(List<String> role) {
        InstructorsOfHiking.role = role;
    }

    public static ObservableList<InstructorsTable> getDataInstructorsTable() {
        return dataInstructorsTable;
    }

    public static void setDataInstructorsTable(ObservableList<InstructorsTable> dataInstructorsTable) {
        InstructorsOfHiking.dataInstructorsTable = dataInstructorsTable;
    }

    public static ObservableList<InstructorsTable> getDataParticipantsTable() {
        return dataParticipantsTable;
    }

    public static void setDataParticipantsTable(ObservableList<InstructorsTable> dataParticipantsTable) {
        InstructorsOfHiking.dataParticipantsTable = dataParticipantsTable;
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        idHiking.setCellValueFactory(new PropertyValueFactory<HikingTable, Integer>("idHiking"));
        nameHiking.setCellValueFactory(new PropertyValueFactory<HikingTable, String>("nameHiking"));
        startHiking.setCellValueFactory(new PropertyValueFactory<HikingTable, Date>("startHiking"));

        idInstructor.setCellValueFactory(new PropertyValueFactory<InstructorsTable, Integer>("idInstructor"));
        surnameInstructor.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("surnameInstructor"));
        categoryInstructor.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("categoryInstructor"));

        idParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, Integer>("idInstructor"));
        surnameParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("surnameInstructor"));
        categoryParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("categoryInstructor"));

        HibernateManager instance = HibernateManager.getInstance();

        try {
            ObservableList<HikingTable> list = FXCollections.observableArrayList(instance.getHikingTable());
            hikingTable.setItems(list);
        }
        catch (SQLException e) {
            addMes();
        }

        if(role.contains("administrator")) {
            changeInstructorButton.setOnAction(event -> changeInstructor());
            hboxPane.getChildren().add(changeInstructorButton);

            addParticipantButton.setOnAction(event -> addParticipant());
            hboxPane.getChildren().add(addParticipantButton);

            deleteParticipantButton.setOnAction(event -> deleteParticipant());
            hboxPane.getChildren().add(deleteParticipantButton);
        }

    }

    @FXML
    private void goToMenu() {
        NavigationManager.from(back).goToMenu();
    }

    @FXML
    private void showInstructors() {

        deleteMessage();

        if(hikingTable.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите поход!");
            message.setTextFill(Color.web("#c80000"));
            hikingTablePane.getChildren().add(message);
            return;
        }

        curHikingTable = hikingTable.getSelectionModel().getSelectedItem();

        try {
            HibernateManager instance = HibernateManager.getInstance();
            instance.getInstructorsTable(curHikingTable.getIdHiking());
        }
        catch (SQLException e){
            addMes();
        }

        if(dataInstructorsTable.size() == 0){
            message.setText("Инструктор еще не назначен.");
            instructorsTablePane.getChildren().add(message);
        }

        instructorsTable.setItems(FXCollections.observableArrayList(dataInstructorsTable));
        participantsTable.setItems(FXCollections.observableArrayList(dataParticipantsTable));

    }

    @FXML
    private void changeInstructor() {

        deleteMessage();

        if(instructorsTable.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите инструктора!");
            message.setTextFill(Color.web("#c80000"));
            hikingTablePane.getChildren().add(message);
            return;
        }

        int idHiking = curHikingTable.getIdHiking();
        int idInstr = instructorsTable.getSelectionModel().getSelectedItem().getIdInstructor();


        boolean check = true;
        HibernateManager instance = HibernateManager.getInstance();
        try {
            check = instance.changeInstructor(idHiking, idInstr);
        }
        catch (SQLException e){
            addMes();
            return;
        }

        if(check == false){
            message.setText("Нет инструктора на замену!");
            vboxPane.getChildren().add(message);
            return;
        }

        try {
            instance.getInstructorsTable(idHiking);
        }
        catch (SQLException e){
            addMes();
        }
        instructorsTable.setItems(FXCollections.observableArrayList(dataInstructorsTable));

    }

    @FXML
    private void addParticipant() {

        deleteMessage();

        if(hikingTable.getSelectionModel().getSelectedItem() != null){
            curHikingTable = hikingTable.getSelectionModel().getSelectedItem();
        }

        if(curHikingTable == null){
            message.setText("Выберите поход!");
            message.setTextFill(Color.web("#c80000"));
            vboxPane.getChildren().add(message);
            return;
        }

        AddParticipant.setIdHiking(curHikingTable.getIdHiking());

        NavigationManager.from(addParticipantButton).openAddParticipant(curHikingTable.getIdHiking());

    }

    private void deleteParticipant() {
        deleteMessage();

        if(participantsTable.getSelectionModel().getSelectedItem() == null){
            message.setText("Выберите участника!");
            message.setTextFill(Color.web("#c80000"));
            vboxPane.getChildren().add(message);
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();

        try {
            instance.deleteParticipantsOfHiking(curHikingTable.getIdHiking(),
                    participantsTable.getSelectionModel().getSelectedItem().getIdInstructor());
        }
        catch (SQLException e){
            instance.rollbackTransaction();
            addMes();
            return;
        }

        try {
            instance.getInstructorsTable(curHikingTable.getIdHiking());
        }
        catch (SQLException e){
            addMes();
        }
        instructorsTable.setItems(dataInstructorsTable);
        participantsTable.setItems(dataParticipantsTable);
    }

    private void deleteMessage() {

        message.setTextFill(Color.web("#000000"));

        if(hikingTablePane.getChildren().contains(message)) {
            hikingTablePane.getChildren().remove(message);
            return;
        }

        if(instructorsTablePane.getChildren().contains(message)) {
            instructorsTablePane.getChildren().remove(message);
            return;
        }

        if(vboxPane.getChildren().contains(message)) {
            vboxPane.getChildren().remove(message);
            return;
        }

    }

    private void addMes() {

        message.setText("Что-то пошло не так:(");
        message.setTextFill(Color.web("#c80000"));

    }

}
