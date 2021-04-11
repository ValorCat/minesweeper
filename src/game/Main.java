package game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Game currentGame;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        currentGame = new Game(root, 18, 70);
        Scene scene = new Scene(root, 575, 575);
        scene.getStylesheets().add("stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle("Minesweeper");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
