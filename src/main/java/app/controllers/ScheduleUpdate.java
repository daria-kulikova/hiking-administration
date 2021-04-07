package app.controllers;

import app.models.HikingSchedule;
import app.models.ScheduleList;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import sun.security.ssl.HandshakeInStream;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ScheduleUpdate implements Initializable {

    private static ObservableList<ScheduleList> data;
    private static List<String> routeName = new ArrayList<>();

    Label error = new Label();

    @FXML
    private GridPane insertPane;
    @FXML
    private ComboBox<String> nameBoxInsert;
    @FXML
    private TextField costInsert;
    @FXML
    private DatePicker startInsert;
    @FXML
    private DatePicker finishInsert;
    @FXML
    private javafx.scene.control.Button insertButton;

    @FXML
    private GridPane updatePane;
    @FXML
    private ComboBox<Integer> idBoxUpdate;
    @FXML
    private ComboBox<String> nameBoxUpdate;
    @FXML
    private TextField costUpdate;
    @FXML
    private DatePicker startUpdate;
    @FXML
    private DatePicker finishUpdate;
    @FXML
    private Button updateButton;

    @FXML
    private GridPane deletePane;
    @FXML
    private ComboBox<Integer> idBoxDelete;
    @FXML
    private Button deleteButton;

    public static ObservableList<ScheduleList> getData() {
        return data;
    }

    public static void setData(ObservableList<ScheduleList> data) {
        ScheduleUpdate.data = data;
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        try {
            routeName = HibernateManager.getInstance().getRouteNames();
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 4, 2, 1);
        }
        List<Integer> idList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for(int i=0; i<data.size(); ++i){
            idList.add(data.get(i).getId());
        }
        for(int i=0; i<routeName.size(); ++i){
            nameList.add(routeName.get(i));
        }
        nameBoxInsert.getItems().addAll(FXCollections.observableArrayList(nameList));
        idBoxUpdate.getItems().addAll(FXCollections.observableArrayList(idList));
        nameBoxUpdate.getItems().addAll(FXCollections.observableArrayList(nameList));
        idBoxDelete.getItems().addAll(FXCollections.observableArrayList(idList));

        error.setTextFill(Color.web("#c80000"));

    }

    @FXML
    public void insertData() {
        deleteMes();

        if (startInsert.getValue() == null || finishInsert.getValue() == null || costInsert.equals("")
                || nameBoxInsert.getValue() == null) {

            error.setText("Необходимо заполнить все обязательные поля!");
            insertPane.add(error, 1, 4);
            return;

        }

        if (finishInsert.getValue().isBefore(startInsert.getValue())) {

            error.setText("Некорректные даты!");
            insertPane.add(error, 1, 4);
            return;

        }

        int cost;
        try {
            cost = Integer.parseInt(costInsert.getText());
            if(cost < 0){
              throw new NumberFormatException("");
            }
        } catch (NumberFormatException exp) {
            error.setText("Некорректная стоимость!");
            insertPane.add(error, 1, 4);
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();

        HikingSchedule item = new HikingSchedule();
        item.setStart(Date.valueOf(startInsert.getValue()));
        item.setFinish(Date.valueOf(finishInsert.getValue()));
        item.setNumberOfParticipants(0);
        item.setCost(cost);
        try {
            item.setIdRoute(instance.getRoute(nameBoxInsert.getValue()));
        } catch (SQLException e) {
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 4, 2, 1);
            return;
        }

        try {
            instance.beginTransaction();
            instance.save(item);
            instance.endTransaction();
        } catch (SQLException e) {
            instance.rollbackTransaction();
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 4, 2, 1);
            return;
        }


        try {
            data.add(instance.fromHikingScheduleToScheduleList(item));
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 4, 2, 1);
            return;
        }
        NavigationManager.from(insertButton).updateSchedule(data);

    }

    @FXML
    public void updateData(){
        deleteMes();

        if(idBoxUpdate.getValue() == null){
            error.setText("Выберите ID!");
            updatePane.add(error, 1, 5, 2, 1);
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();
        HikingSchedule item = new HikingSchedule();
        int idx = 0;

        try {
            item = (HikingSchedule) instance.find(HikingSchedule.class, idBoxUpdate.getValue());

            idx = data.indexOf(instance.fromHikingScheduleToScheduleList(item));

            if (nameBoxUpdate.getValue() != null) {
                item.setIdRoute(instance.getRoute(nameBoxUpdate.getValue()));
            }
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 5, 2, 1);
            return;
        }
        if(!costUpdate.getText().equals("")){
            try {
                item.setCost(Integer.parseInt(costUpdate.getText()));
                if (item.getCost() < 0) {
                  throw new NumberFormatException("");
                }
            }
            catch (NumberFormatException exp){
                error.setText("Некорректная стоимость!");
                updatePane.add(error, 1, 5, 2, 1);
                return;
            }
        }
        if(startUpdate.getValue() != null){
            item.setStart(Date.valueOf(startUpdate.getValue()));
        }
        if(finishUpdate.getValue() != null){
            item.setFinish(Date.valueOf(finishUpdate.getValue()));
        }

        if(item.getFinish().before(item.getStart())){
            error.setText("Некорректные даты!");
            updatePane.add(error, 1, 5, 2, 1);
            return;
        }

        try {
            instance.beginTransaction();
            instance.update(item);
            instance.endTransaction();
        }
        catch (SQLException e){
            instance.rollbackTransaction();
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 5, 2, 1);
            return;
        }


        try {
            data.set(idx, instance.fromHikingScheduleToScheduleList(item));
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 5, 2, 1);
            return;
        }
        NavigationManager.from(updateButton).updateSchedule(data);

    }

    @FXML
    public void deleteData(){
      deleteMes();

        Label error = new Label();
        error.setTextFill(Color.web("#c80000"));

        if(idBoxDelete.getValue() == null){
            error.setText("Выберите ID!");
            deletePane.add(error, 1, 3, 15, 1);
            return;
        }

        HibernateManager instance = HibernateManager.getInstance();
        HikingSchedule item = new HikingSchedule();
        try {
            item = (HikingSchedule) instance.find(HikingSchedule.class, idBoxDelete.getValue());
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 3, 15, 1);
            return;
        }

        try {
            instance.beginTransaction();
            instance.delete(item);
            instance.endTransaction();
        }
        catch (SQLException e){
            instance.rollbackTransaction();
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 3, 15, 1);
            return;
        }

        try {
            data.remove(instance.fromHikingScheduleToScheduleList(item));
        }
        catch (SQLException e){
            error.setText("Что-то пошло не так:(");
            error.setTextFill(Color.web("#c80000"));
            insertPane.add(error, 1, 3, 15, 1);
            return;
        }
        NavigationManager.from(deleteButton).updateSchedule(data);

    }

    private void deleteMes() {
        if(deletePane.getChildren().contains(error)){
            deletePane.getChildren().remove(error);
            return;
        }
        if(insertPane.getChildren().contains(error)){
            insertPane.getChildren().remove(error);
            return;
        }
        if(updatePane.getChildren().contains(error)){
            updatePane.getChildren().remove(error);
            return;
        }
    }
}
