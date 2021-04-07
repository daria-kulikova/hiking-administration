package app.controllers;

import app.models.ScheduleList;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import app.utils.InflateUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;


public class Schedule implements Initializable {

    @FXML
    private Button back;

    @FXML
    private FlowPane schedulePane;

    @FXML
    private TableView schedule;

    @FXML
    private TableColumn id;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn type;
    @FXML
    private TableColumn start;
    @FXML
    private TableColumn finish;
    @FXML
    private TableColumn cost;

    private static ObservableList<ScheduleList> data;

    public Schedule() {
    }

    public static ObservableList<ScheduleList> getData() {
        return data;
    }

    public static void setData(ObservableList<ScheduleList> data) {

        Schedule.data = data;

    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        id.setCellValueFactory(new PropertyValueFactory<ScheduleList, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<ScheduleList, String >("name"));
        type.setCellValueFactory(new PropertyValueFactory<ScheduleList, String>("type"));
        start.setCellValueFactory(new PropertyValueFactory<ScheduleList, Date>("start"));
        finish.setCellValueFactory(new PropertyValueFactory<ScheduleList, Date>("finish"));
        cost.setCellValueFactory(new PropertyValueFactory<ScheduleList, Integer>("cost"));
        schedule.setItems(data);

        List<String> role = HibernateManager.getRole();
        if(role.contains("sysadmin0") || role.contains("administrator")){

            ScheduleUpdate.setData(data);
            final ScheduleUpdate controller = new ScheduleUpdate();
            final Parent scheduleUpdate = InflateUtils.loadFxmlWithCustomController("/schedule_update.fxml", controller);
            schedulePane.getChildren().addAll(scheduleUpdate);


        }
    }

    @FXML
    public void goToHikingParametrs() {

        NavigationManager.from(back).goToHikingParametrs();
    }

}
