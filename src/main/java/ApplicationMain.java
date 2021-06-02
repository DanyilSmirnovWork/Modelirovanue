import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Класс запускающий приложение
 */
public class ApplicationMain extends Application {

    /** Окно */
    public static Stage stage;

    /**
     * Метод для управления свойствами (функционалом) нашего окна
     * @param stage окно
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("markupFiles/SimulationProject.fxml")));
        stage.setTitle("SimulationProject");
        stage.setScene(new Scene(root, 1100, 850));
        stage.show();
        stage.setResizable(false);
        ApplicationMain.stage = stage;
    }

    /**
     * Главный метод запускающий оконное приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args){
        launch(args);
    }
}
