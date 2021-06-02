package markupControllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Simulation;
import model.enums.Speeds;
import properties.SimulationProperties;
import util.SimulationUtils;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Класс контроллер для файла SimulationProject.fxml
 */
public class SimulationProjectController {
    @FXML
    private Spinner<Integer> sourceOfPartsDelaySpinner;
    @FXML
    private Spinner<Integer> preProcessingDelaySpinner;
    @FXML
    private Spinner<Integer> assemblyDelaySpinner;
    @FXML
    private Spinner<Integer> distributionDelaySpinner;
    @FXML
    private Spinner<Integer> simulationTimeSpinner;
    @FXML
    private Button butLastReport;
    @FXML
    private Slider spedSlider;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Text preProcessingQueueText;
    @FXML
    private Text assemblyQueueText1;
    @FXML
    private Text assemblyQueueText2;
    @FXML
    private Text distributionQueueText;
    @FXML
    private ImageView distributionQueueImg;
    @FXML
    private ImageView preProcessingQueueImg;
    @FXML
    private ImageView assemblyQueueImg1;
    @FXML
    private ImageView assemblyQueueImg2;
    @FXML
    private ImageView modelImg;
    @FXML
    private Button butSimulationStop;

    private final String path = System.getProperty("user.dir") + "/src/main/resources/Reports";
    public static String lastFileName;

    @FXML
    void SimulationStop(ActionEvent event) {
        Simulation.reported = false;
        Simulation.stop();
    }

    @FXML
    void openDirectoryReports(ActionEvent event) {
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
            desktop = Desktop.getDesktop();
        }
        try {
            Objects.requireNonNull(desktop).open(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openLastReport(ActionEvent event) {
            Desktop desktop = null;
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
            }
            try {
                Objects.requireNonNull(desktop).open(new File(lastFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    void simulationStart(ActionEvent event) {
        progressBar.setOpacity(10);
        progressBar.setOpacity(10);
        System.out.println(getSpeed((int) SimulationUtils.roundUp(spedSlider.getValue())));
        Simulation simulation = new Simulation(new SimulationProperties(sourceOfPartsDelaySpinner.getValue(), preProcessingDelaySpinner.getValue(),
                assemblyDelaySpinner.getValue(), distributionDelaySpinner.getValue(), simulationTimeSpinner.getValue(),
                getSpeed((int) spedSlider.getValue())));
        simulation.set();
        simulation.setText(preProcessingQueueText, assemblyQueueText1, assemblyQueueText2, distributionQueueText, progressBar);
        butSimulationStop.setDisable(false);
        Simulation.reported = true;
        simulation.start();
        butLastReport.setDisable(false);
    }

    @FXML
    void initialize() {
        //progressBar.
        //Картинка модели
        Image ModelImage = new Image("images/Diagram.png");
        modelImg.setImage(ModelImage);
        //Картинки очереди
        Image queueImage = new Image("images/Queue.jpg");
        preProcessingQueueImg.setImage(queueImage);
        assemblyQueueImg1.setImage(queueImage);
        assemblyQueueImg2.setImage(queueImage);
        distributionQueueImg.setImage(queueImage);
        //Значения для спинеров
        SpinnerValueFactory<Integer> valueFactorySimulationTime = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 24);
        SpinnerValueFactory<Integer> valueFactorySourceOfPartsDelay = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 10);
        SpinnerValueFactory<Integer> valueFactoryPreProcessingDelay = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 7);
        SpinnerValueFactory<Integer> valueFactoryAssemblyDelay  = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 6);
        SpinnerValueFactory<Integer> valueFactoryDistributionDelay = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 1000, 8);
        simulationTimeSpinner.setValueFactory(valueFactorySimulationTime);
        sourceOfPartsDelaySpinner.setValueFactory(valueFactorySourceOfPartsDelay);
        preProcessingDelaySpinner.setValueFactory(valueFactoryPreProcessingDelay);
        assemblyDelaySpinner.setValueFactory(valueFactoryAssemblyDelay);
        distributionDelaySpinner.setValueFactory(valueFactoryDistributionDelay);
        spedSlider.setMax(7);
        spedSlider.setMin(1);
        spedSlider.setValue(5);
        preProcessingQueueText.setText("0");
        assemblyQueueText1.setText("0");
        assemblyQueueText2.setText("0");
        distributionQueueText.setText("0");
    }

    public Speeds getSpeed(int value) {
        return switch (value) {
            case 1 -> Speeds.X1;
            case 2 -> Speeds.X2;
            case 3 -> Speeds.X3;
            case 4 -> Speeds.X4;
            case 5 -> Speeds.X5;
            case 6 -> Speeds.X6;
            case 7 -> Speeds.X7;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }
}
