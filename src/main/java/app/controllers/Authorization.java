package app.controllers;

import app.models.*;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.management.Query;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class Authorization {

    @FXML
    private FlowPane pane;

    @FXML
    private TextField usrname;

    @FXML
    private PasswordField pass;

    @FXML
    private Button goToMenuButton;

    Label mess = new Label();

    @FXML
    private void goToMenu() {

        if(pane.getChildren().contains(mess)){
            pane.getChildren().remove(mess);
        }


        HibernateManager.setUsrname(usrname.getText());
        HibernateManager.setPass(pass.getText());

        HibernateManager instance = HibernateManager.getInstance();

        if(HibernateManager.isError()){
            mess.setText("Неправильное имя пользователя и/или пароль");
            mess.setTextFill(Color.web("#c80000"));
            pane.getChildren().add(mess);
            return;
        }

        try {
            HibernateManager.setRole(instance.getUserRole());
        }
        catch (SQLException e) {
            mess.setText("Что-то пошло не так:(");
            mess.setTextFill(Color.web("#c80000"));
            pane.getChildren().add(mess);
            return;
        }

        if(instance.getRole().contains("administrator") || instance.getRole().contains("instructor")) {
            NavigationManager.from(goToMenuButton).goToMenu();
        }
        else{
            NavigationManager.from(goToMenuButton).goToHikingParametrs();
        }

    }
}