package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class App extends Application {
    private static Stage pStage;

    public static Stage getStage() {
        return pStage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pStage = primaryStage;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(App.class.getResource("/views/login.fxml")));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
