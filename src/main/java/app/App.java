package app;

import app.models.Participants;
import app.models.Places;
import app.navigation.NavigationManager;
import app.utils.HibernateManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class App extends Application {

    public static void main(String[] args) throws java.text.ParseException{

        App.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        NavigationManager nm = new NavigationManager(stage);
        nm.goToAuto();
        stage.show();
    }

}
