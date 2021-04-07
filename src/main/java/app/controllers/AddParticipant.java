package app.controllers;

import app.models.HikingSchedule;
import app.models.InstructorsTable;
import app.models.Participants;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javax.validation.*;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class AddParticipant implements Initializable {

    @FXML
    private TableView<InstructorsTable> participantsTable;
    @FXML
    private TableColumn idParticipant;
    @FXML
    private TableColumn surnameParticipant;
    @FXML
    private TableColumn categoryParticipant;

   /* @FXML
    private Button deleteButton;*/
    @FXML
    private Button updateButton;
    @FXML
    private Button chooseButton;

    @FXML
    private FlowPane tablePane;

    @FXML
    private TextField surname;
    @FXML
    private TextField name;
    @FXML
    private TextField patronymic;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField eMail;
    @FXML
    private ComboBox<String> sportsCategory;
    @FXML
    private ComboBox<String> position;
    @FXML
    private DatePicker birth;

    @FXML
    private Button saveUpdateButton;
    @FXML
    private Button createButton;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label message = new Label();

    private static int idHiking;

    private static ObservableList<InstructorsTable> data;

    Participants participant;

    public static int getIdHiking() {
        return idHiking;
    }

    public static void setIdHiking(int idHiking) {
        AddParticipant.idHiking = idHiking;
    }

    public static ObservableList<InstructorsTable> getData() {
        return data;
    }

    public static void setData(ObservableList<InstructorsTable> data) {
        AddParticipant.data = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        idParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, Integer>("idInstructor"));
        surnameParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("surnameInstructor"));
        categoryParticipant.setCellValueFactory(new PropertyValueFactory<InstructorsTable, String>("categoryInstructor"));

        participantsTable.setItems(data);
    }

    @FXML
    private void chooseParticipant() {

        deleteMessage();

        if(!isParticipantChosen()){
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();
        try {
            instance.addHikingParticipant(idHiking, participantsTable.getSelectionModel().getSelectedItem().getIdInstructor());
        }
        catch (SQLException e) {
            addErrorMes("");
            instance.rollbackTransaction();
            return;
        }

        NavigationManager.from(chooseButton).closeAddParticipant();
    }

    @FXML
    private void updateParticipant() {

        deleteMessage();

        if(!isParticipantChosen()){
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();
        try {
            InstructorsTable item = participantsTable.getSelectionModel().getSelectedItem();
            participant = (Participants) instance.find(Participants.class, item.getIdInstructor());
        }
        catch (SQLException e) {
            addErrorMes("");
            return;
        }

        surname.setText(participant.getSurname());
        name.setText(participant.getName());
        patronymic.setText(participant.getPatronymic());
        phoneNumber.setText(participant.getPhoneNumber());
        eMail.setText(participant.geteMail());
        if(participant.getBirth() != null){
            birth.setValue(participant.getBirth().toLocalDate());
        }
        if(participant.getSportsCategory() != ""){
            sportsCategory.setValue(participant.getSportsCategory());
        }
        if(participant.getPosition() == 0){
            position.setValue("Участник");
        }
        else {
            position.setValue("Инструктор");
        }

    }

    private boolean isParticipantChosen(){
        if(participantsTable.getSelectionModel().getSelectedItem() == null){
            message.setText("Участник не выбран!");
            message.setTextFill(Color.web("#c80000"));
            tablePane.getChildren().add(message);
            return false;
        }
        return true;
    }

    @FXML
    private void createParticipant() {

        deleteMessage();


        HibernateManager instance = HibernateManager.getInstance();

      if(surname.getText().equals("") || name.getText().equals("") || patronymic.getText().equals("") || phoneNumber.getText().equals("")
        || eMail.getText().equals("") || position.getValue() == null) {
        addErrorMes("Необходимо заполнить все обязательные поля!");
        return;
      }

        Participants item = changeParticipant(new Participants());

        try {
            instance.beginTransaction();
            instance.save(item);
            instance.endTransaction();
        }
        catch (ConstraintViolationException exp){
            instance.endTransaction();
            Validator validator  = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<Participants>> constraintViolations = validator.validate(item);
            addErrorMes(constraintViolations.iterator().next().getMessage());
            return;
        }
        catch (SQLException e){
            addErrorMes("");
            instance.rollbackTransaction();
            return;
        }

        try {
            data.add(instance.toInstructorsTable(item));
        }
        catch (SQLException e) {
            addErrorMes("");
            return;
        }
        NavigationManager.from(createButton).updateParticipantsTable(idHiking);

        message.setText("Участник успешно добавлен!");
        gridPane.add(message, 1, 3, 3, 1);

    }

    @FXML
    private void saveUpdate() {

        deleteMessage();

        if(participant == null){
            addErrorMes("Участник не выбран!");
            return;
        }

        if(surname.getText().equals("") || name.getText().equals("") || patronymic.getText().equals("") || phoneNumber.getText().equals("")
          || eMail.getText().equals("") || position.getValue() == null) {
          addErrorMes("Необходимо заполнить все обязательные поля!");
          return;
        }


        participant = changeParticipant(participant);


        HibernateManager instance = HibernateManager.getInstance();
        try {
            instance.beginTransaction();
            instance.update(participant);
            instance.endTransaction();
        }
        catch (SQLException e) {
            addErrorMes("");
            instance.rollbackTransaction();
            return;
        }

        NavigationManager.from(saveUpdateButton).updateParticipantsTable(idHiking);

    }

    private void deleteMessage() {

        message.setTextFill(Color.web("#000000"));

        if(tablePane.getChildren().contains(message)){
            tablePane.getChildren().remove(message);
            return;
        }

        if(gridPane.getChildren().contains(message)){
            gridPane.getChildren().remove(message);
            return;
        }

    }

    private Participants changeParticipant(Participants item){

        item.setSurname(surname.getText());
        item.setName(name.getText());
        item.setPatronymic(patronymic.getText());
        item.setPhoneNumber(phoneNumber.getText());
        item.seteMail(eMail.getText());
        item.setSportsCategory(sportsCategory.getValue());
          if (position.getValue().equals("Инструктор")) {
            item.setPosition(1);
          } else {
            item.setPosition(0);
          }
        if(birth.getValue() != null) {
            item.setBirth(Date.valueOf(birth.getValue()));
        }

        return item;
    }

    private void addErrorMes(String text) {
        if(text == ""){
            text = "Что-то пошло не так:(";
        }
        message.setText(text);
        message.setTextFill(Color.web("#c80000"));
        gridPane.add(message, 1, 4, 3, 1);
    }

}
