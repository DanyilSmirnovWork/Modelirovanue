package threads;

import javafx.scene.control.ProgressBar;
import model.Simulation;
import model.enums.Speeds;
import util.SimulationUtils;

/**
 * Класс TimeCheckThread, который контролирует время симуляции
 */
public class TimeCheckThread extends Thread {
    /** Время симуляции, которое учитывает скорость симуляции */
    private long simulationTime;
    /** Время начала симуляции */
    private long timeStart;
    private ProgressBar progressBar;

    public void set(long simulationTime, Speeds speedSimulation) {
        this.simulationTime = SimulationUtils.getTimeMillis(simulationTime * 60, speedSimulation.getSpeed());
        this.timeStart = System.currentTimeMillis();
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (Simulation.simulationStatus) {
                    long time = System.currentTimeMillis();
                    double progress = (double) (time - timeStart) / simulationTime;
                    progressBar.setProgress(progress);
                    if (time >= timeStart + simulationTime)
                        Simulation.stop();
                } else {
                    progressBar.setProgress(0);
                    break;
                }
            } catch (Exception exception) {
                System.out.print("");
            }
        }
    }
}
