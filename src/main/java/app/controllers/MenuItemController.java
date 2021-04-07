package app.controllers;

import app.navigation.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuItemController implements Initializable {


    private int flag;

    @FXML
    private Button item;

    public MenuItemController(int flag) {
        this.flag = flag;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(flag == 1){
            item.setText("Расписание походов");
            item.setOnAction(event -> NavigationManager.from(item).goToHikingParametrs());
            item.setOnKeyPressed(event -> NavigationManager.from(item).goToHikingParametrs());
        }

        if(flag == 2){
            item.setText("Расчет зарплаты");
            item.setOnAction(event -> NavigationManager.from(item).goToSalaries());
            item.setOnKeyPressed(event -> NavigationManager.from(item).goToSalaries());
        }

        if(flag == 3){
            item.setText("Инструкторы");
            item.setOnAction(event -> NavigationManager.from(item).goToInstructors());
            item.setOnKeyPressed(event -> NavigationManager.from(item).goToInstructors());
        }
    }

}
