package app.controllers;

import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import app.utils.InflateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HikingParametrs implements Initializable {

    @FXML
    private ComboBox<String> routeBox;

    @FXML
    private DatePicker start;

    @FXML
    private DatePicker finish;

    @FXML
    private TextField cost;

    @FXML
    private Button goToScheduleButton;

    @FXML
    private Button back;

    @FXML
    private GridPane gridPane;

    private Label errorMes = new Label("Что-то пошло не так:(");

    public HikingParametrs() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        errorMes.setTextFill(Color.web("#c80000"));

        HibernateManager instance = HibernateManager.getInstance();
        try {
            ObservableList items = FXCollections.observableArrayList(instance.getTypeOfHiking());
            routeBox.getItems().addAll(items);
        }
        catch (SQLException e) {
            gridPane.add(errorMes, 11, 1, 2, 1);
            return;
        }

    }

    @FXML
    public void goToSchedule(){
        deleteMes();

        Date st, fin;
        int c;
        if(start.getValue() == null)
            st = Date.valueOf("1990-01-01");
        else
            st = Date.valueOf(start.getValue());
        if(finish.getValue() == null)
            fin = Date.valueOf("3000-01-01");
        else
            fin =Date.valueOf(finish.getValue());
        if(fin.before(st)){
          errorMes.setText("Дата окончания не может быть раньше начала!");
          errorMes.setTextFill(Color.web("#c80000"));
          gridPane.add(errorMes, 1, 12);
          return;
        }
        if(cost.getText().length() == 0)
            c = 1000000000;
        else {
            try {
                c = Integer.parseInt(cost.getText());
                if(c < 0){
                  throw new NumberFormatException("");
                }
            }
            catch (NumberFormatException exp) {
                errorMes.setText("Некорректная стоимость!");
                errorMes.setTextFill(Color.web("#c80000"));
                gridPane.add(errorMes, 1, 12);
                return;
            }
        }

        NavigationManager.from(goToScheduleButton).goToSchedule(routeBox.getValue(), st, fin, c);
    }

    @FXML
    public void goToMenu() {
        deleteMes();

        NavigationManager.from(back).goToMenu();
    }

    private void deleteMes(){
        if(gridPane.getChildren().contains(errorMes)){
            gridPane.getChildren().remove(errorMes);
        }
    }

}
