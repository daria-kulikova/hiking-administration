package app.controllers;

import app.models.Salaries;
import app.models.ScheduleList;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SalariesTable implements Initializable {

    @FXML
    private Button back;

    @FXML
    private VBox parametersPane;

    @FXML
    private DatePicker start;
    @FXML
    private DatePicker finish;
    @FXML
    private Button getSalariesTable;

    @FXML
    private TableView salaries;

    @FXML
    private TableColumn id;
    @FXML
    private TableColumn surname;
    @FXML
    private TableColumn name;
    @FXML
    private TableColumn patronymic;
    @FXML
    private TableColumn i;
    @FXML
    private TableColumn ii;
    @FXML
    private TableColumn iii;
    @FXML
    private TableColumn iv;
    @FXML
    private TableColumn total;

    @FXML
    private VBox salariesPane;

    private Label errorMes = new Label();

    @Override
    public void initialize(URL location, ResourceBundle resource) {
        errorMes.setTextFill(Color.web("#c80000"));

        id.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("id"));
        surname.setCellValueFactory(new PropertyValueFactory<Salaries, String>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<Salaries, String>("name"));
        patronymic.setCellValueFactory(new PropertyValueFactory<Salaries, String>("patronymic"));
        i.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("i"));
        ii.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("ii"));
        iii.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("iii"));
        iv.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("iv"));
        total.setCellValueFactory(new PropertyValueFactory<Salaries, Integer>("total"));
    }

    @FXML
    private void getTable() {
        deleteMes();

        if(start.getValue() == null || finish.getValue() == null){
            errorMes.setText("Укажите даты!");
            parametersPane.getChildren().add(errorMes);
            return;
        }

        if(finish.getValue().isBefore(start.getValue())) {
          errorMes.setText("Дата окончания не может быть раньше начала!");
          parametersPane.getChildren().add(errorMes);
          return;
        }

        HibernateManager instance = HibernateManager.getInstance();

        try {
            ObservableList<Salaries> data = FXCollections.observableList(instance.getSalaries(
                    Date.valueOf(start.getValue()), Date.valueOf(finish.getValue())
            ));
            salaries.setItems(data);
        }
        catch (SQLException e){
            errorMes.setText("Что-то пошло не так:(");
            salariesPane.getChildren().add(errorMes);
            return;
        }

    }

    @FXML
    private void goBack() {
        NavigationManager.from(back).goToMenu();
    }

    private void deleteMes() {
        if(salariesPane.getChildren().contains(errorMes)){
            salariesPane.getChildren().remove(errorMes);
            return;
        }
        if(parametersPane.getChildren().contains(errorMes)){
            parametersPane.getChildren().remove(errorMes);
            return;
        }
    }

}
