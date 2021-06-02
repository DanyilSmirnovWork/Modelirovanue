package model;

import api.ReportGenerator;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import properties.SimulationProperties;
import threads.*;
import util.PartIdGenerator;

/**
 * Класс Simulation описывающий симуляцию
 * @param properties - свойства симуляции
 */
public record Simulation(SimulationProperties properties) {
    public static SourceOfPartsThread sourceOfParts;
    public static PreProcessingThread preProcessing;
    public static AssemblyThread assembly;
    public static DistributionThread distribution;
    public static PartIdGenerator idGenerator;
    public static TimeCheckThread timeCheckThread;
    public static boolean simulationStatus;
    public static boolean reported = true;

    public Simulation(SimulationProperties properties) {
        this.properties = properties;
        Simulation.idGenerator = new PartIdGenerator();
        Simulation.timeCheckThread = new TimeCheckThread();
        Simulation.sourceOfParts = new SourceOfPartsThread();
        Simulation.preProcessing = new PreProcessingThread();
        Simulation.assembly = new AssemblyThread();
        Simulation.distribution = new DistributionThread();
        set();
    }

    public void setText(Text text1, Text text2, Text text3, Text text4, ProgressBar progressBar) {
        Simulation.preProcessing.setText(text1);
        Simulation.assembly.setTextAfterPreProcessing(text2);
        Simulation.assembly.setText(text3);
        Simulation.distribution.setText(text4);
        Simulation.timeCheckThread.setProgressBar(progressBar);
    }

    public void set() {
        Simulation.timeCheckThread.set(properties.timeSimulation(), properties.speedSimulation());
        Simulation.sourceOfParts.set(properties.sourceOfPartsTime(), properties.speedSimulation());
        Simulation.preProcessing.set(properties.preProcessingTime(), properties.speedSimulation());
        Simulation.assembly.set(properties.assemblyTime(), properties.speedSimulation());
        Simulation.distribution.set(properties.distributionTime(), properties.speedSimulation());
    }

    public void start() {
        restart();
        Simulation.simulationStatus = true;
        Simulation.timeCheckThread.start();
        Simulation.sourceOfParts.start();
        Simulation.preProcessing.start();
        Simulation.assembly.start();
        Simulation.distribution.start();
    }

    public static void stop() {
        Simulation.simulationStatus = false;
        if(reported)
            Simulation.generateReport();
    }

    private void restart() {
        Simulation.sourceOfParts.clear();
        Simulation.preProcessing.clear();
        Simulation.assembly.clear();
        Simulation.distribution.clear();
        Simulation.idGenerator.restart();
    }

    public static void generateReport() {
        ReportGenerator reportGenerator;
        reportGenerator = new ReportGenerator(Simulation.assembly.getQueueList(), Simulation.assembly.getQueue1(),
                Simulation.assembly.getQueueAfterPreProcessingList(), Simulation.assembly.getQueue2(),
                Simulation.preProcessing.getQueueList(), Simulation.preProcessing.getQueue1(),
                Simulation.distribution.getQueueList(), Simulation.distribution.getQueue1(),
                Simulation.sourceOfParts.served(),
                sourceOfParts.getTimeStart(), Simulation.sourceOfParts.getSpeedSimulation());
        reportGenerator.generate();
    }
}
