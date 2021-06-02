package threads;

import model.enums.Speeds;
import util.SimulationUtils;
import model.Part;
import model.Simulation;

/**
 * Поток SourceOfPartsThread, который генерирует детали и распределяет их между потоками AssemblyThread и PreProcessingThread
 */
public class SourceOfPartsThread extends Thread {
    /** Время задержки */
    private long delay;
    /** Счёт созданных деталей */
    private long counter = 0;
    long timeStart;
    Speeds speedSimulation;
    /**
     * Метод для расчёта задержки
     * @param time время генерации деталей
     * @param speedSimulation скорость симуляции
     */
    public void set(long time, Speeds speedSimulation) {
        this.speedSimulation = speedSimulation;
        this.delay = SimulationUtils.getTimeMillis(time, speedSimulation.getSpeed());
        clear();
    }

    public long served() {
        return counter;
    }

    public void clear() {
        this.counter = 0;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public Speeds getSpeedSimulation() {
        return speedSimulation;
    }
    @Override
    public void run() {
        timeStart = System.currentTimeMillis();
        while(true) {
            if (Simulation.simulationStatus) {
                try {
                    do {
                        this.counter++;
                        Part part = new Part(Simulation.idGenerator.generateNewId(), System.currentTimeMillis());
                        Simulation.reported = true;
                        if (SimulationUtils.getRandom(0.50))
                            Simulation.preProcessing.listAdd(part);
                        else
                            Simulation.assembly.listAdd(part);
                    } while (counter % 3 != 0);
                    System.out.println("Создано 3 новых элемента, всего созданных элементов: " + this.counter);
                    sleep(delay);
                } catch (Exception e) {
                    System.out.print("");
                }
            } else
                break;
        }
    }
}
