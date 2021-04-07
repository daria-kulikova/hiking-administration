package app.navigation;

import app.controllers.AddParticipant;
import app.controllers.InstructorsOfHiking;
import app.controllers.MenuController;
import app.controllers.Schedule;
import app.models.InstructorsTable;
import app.models.ScheduleList;
import app.utils.HibernateManager;
import app.utils.InflateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NavigationManager {

    private Stage stage;

    private Stage stage1 = new Stage();

    public NavigationManager(Stage stage) {
        this.stage = stage;
    }

    public NavigationManager(Stage stage, Stage stage1) {
        this.stage = stage;
        this.stage1 = stage1;
    }

    public static NavigationManager from(Node node) {
        return (NavigationManager) node.getScene().getUserData();
    }

    private Scene createScene(Parent root) {
        final Scene scene = new Scene(root);
        scene.setUserData(this);
        return scene;
    }

    public void goToAuto() {
        final Parent root = InflateUtils.loadFXML("/authorization.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void goToMenu() {
        MenuController.setRole(HibernateManager.getRole());
        final Parent root = InflateUtils.loadFXML("/menu.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void goToHikingParametrs() {
        final Parent root = InflateUtils.loadFXML("/hiking_parametrs.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void goToSchedule(String route, java.sql.Date start, Date finish, int cost) {
        HibernateManager instance = HibernateManager.getInstance();

        try {
            ObservableList<ScheduleList> data = FXCollections.observableList(instance.getSchedule(route, start, finish, cost));
            updateSchedule(data);
        }
        catch (SQLException e){
            updateSchedule(FXCollections.observableArrayList(new ArrayList<>()));
        }
    }

    public void updateSchedule(ObservableList<ScheduleList> data) {
        Schedule.setData(data);

        final Parent root = InflateUtils.loadFXML("/hiking_schedule.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void goToSalaries() {
        final Parent root = InflateUtils.loadFXML("/salaries_table.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void goToInstructors() {
        InstructorsOfHiking.setRole(HibernateManager.getRole());
        final Parent root = InflateUtils.loadFXML("/instructors_of_hiking.fxml");
        final Scene scene = createScene(root);
        stage.setScene(scene);
    }

    public void openAddParticipant(int idHiking) {
      System.out.println(200);
      System.out.println(idHiking);
        updateParticipantsTable(idHiking);

      System.out.println(201);
        stage1.show();
      System.out.println(202);
    }

    public void updateParticipantsTable(int idHiking) {

        HibernateManager instance = HibernateManager.getInstance();
        List<InstructorsTable> instructorsTableList;
        try {
            instructorsTableList = instance.getParticipants(idHiking);
        }
        catch (Throwable e){
            instructorsTableList = new ArrayList<>();
        }

        AddParticipant.setData(FXCollections.observableArrayList(instructorsTableList));

      System.out.println(105);
        final Parent root = InflateUtils.loadFXML("/add_participant.fxml");
      System.out.println(106);
        final Scene scene = createScene(root);
      System.out.println(107);
        stage1.setScene(scene);
      System.out.println(108);
    }


    public void closeAddParticipant() {
        stage1.close();
    }

}
