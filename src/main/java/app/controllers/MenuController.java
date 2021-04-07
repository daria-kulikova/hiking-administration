package app.controllers;

import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import app.utils.InflateUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import javax.swing.table.TableColumn;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private static List<String> role;

    public MenuController() {
    }

    public MenuController(List<String> role) {
        this.role = role;
    }

    @FXML
    private TilePane container;

    public static List<String> getRole() {
        return role;
    }

    public static void setRole(List<String> role) {
        MenuController.role = role;
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {

        Button scheduleButton = new Button("Расписание походов");
        scheduleButton.setPrefHeight(100);
        scheduleButton.setPrefWidth(200);
        scheduleButton.setOnAction(event -> NavigationManager.from(scheduleButton).goToHikingParametrs());
        container.getChildren().add(scheduleButton);

        if(role.contains("administrator")) {
            Button salariesButton = new Button("Расчет зарплаты");
            salariesButton.setPrefHeight(100);
            salariesButton.setPrefWidth(200);
            salariesButton.setOnAction(event -> NavigationManager.from(salariesButton).goToSalaries());
            container.getChildren().add(salariesButton);
        }

        if(role.contains("administrator") || role.contains("instructor")) {
            Button instructorsButton = new Button("Участники");
            instructorsButton.setPrefHeight(100);
            instructorsButton.setPrefWidth(200);
            instructorsButton.setOnAction(event -> NavigationManager.from(instructorsButton).goToInstructors());
            container.getChildren().add(instructorsButton);
        }

    }
}
