package threads;

import javafx.scene.text.Text;
import model.*;
import model.enums.BlockType;
import model.enums.Speeds;
import util.SimulationUtils;
import util.UtilThread;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс DistributionThread, который занимается регулировкой деталей
 */
public class DistributionThread extends Thread implements UtilThread {
    /** Задержка */
    private long time;
    /** Id первой детали в очереди */
    private int idPartList = 0;
    /** Лист деталей вошедших в блок */
    private final List<Part> partList = new ArrayList<>();
    /** Для установки значений очереди */
    Text text;
    private long queue1;
    private int i1;

    /**
     * Метод для расчёта задержки
     * @param time время генерации деталей
     * @param speedSimulation скорость симуляции
     */
    public void set(long time, Speeds speedSimulation) {
        this.time = SimulationUtils.getTimeMillis(time, speedSimulation.getSpeed());
        clear();
    }

    public long getQueue1() {
        return queue1 / i1;
    }

    public List getQueueList() {
        return partList;
    }


    @Override
    public void run() {
        while (true) {
            if(Simulation.simulationStatus) {
                try {
                    partList.get(idPartList);
                    sleep(time);
                    partList.get(idPartList).setExitTimeForLastElementLogList(System.currentTimeMillis());
                    idPartList++;
                    queue1 += getQueue();
                    i1 ++;
                    System.out.println("1 элемент покинул блок регулирования, очередь на данный момент: "
                            + getQueue() + "\nВсего покинуло этот блок: " + idPartList);
                    text.setText(String.valueOf(getQueue()));
                } catch (Exception e) {
                    System.out.print("");
                }
            } else
                break;
        }
    }

    @Override
    public void listAdd(Part part) {
        queue1 += getQueue();
        i1 ++;
        part.addLog(new Log(System.currentTimeMillis(), BlockType.Distribution));
        partList.add(part);
        System.out.println("Поступил 1 элемент в блок регулирования, очередь на данный момент: " + getQueue());
    }

    @Override
    public long getQueue() {
        return this.partList.size() - this.idPartList;
    }

    @Override
    public void clear() {
        idPartList = 0;
        partList.clear();
        try{
            text.setText("0");
        } catch (Exception e) {
            System.out.print("");
        }
    }

    @Override
    public void setText(Text text) {
        this.text = text;
    }

}
