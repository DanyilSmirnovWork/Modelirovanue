package threads;

import javafx.scene.text.Text;
import model.*;
import model.enums.BlockType;
import model.enums.Speeds;
import util.SimulationUtils;
import util.UtilThreadAssembly;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс AssemblyThread, который занимается сборкой деталей
 */
public class AssemblyThread extends Thread implements UtilThreadAssembly {
    /** Задержка */
    private long time;
    /** Id первой детали в очереди */
    private int idPartList = 0;
    /** Id первой детали в очереди */
    private int idPartListAfterPreProcessing = 0;
    /** Лист деталей вошедших в блок */
    private final List<Part> partList = new ArrayList<>();
    /** Лист деталей вошедших в блок после предварительной обработки */
    private final List<Part> partListAfterPreProcessing = new ArrayList<>();
    private long queue1 = 0;
    private int i1 = 0;
    private long queue2 = 0;
    private int i2 = 0;
    /** Для установки значений очереди */
    Text text1;
    /** Для установки значений очереди */
    Text text2;

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

    public long getQueue2() {
        return queue2 / i2;
    }

    @Override
    public void run() {
        while(true) {
            if(Simulation.simulationStatus) {
                try {
                    Part partParent1 = partList.get(idPartList);
                    Part partParent2 = partListAfterPreProcessing.get(idPartListAfterPreProcessing);
                    sleep(time);
                    partParent1.setExitTimeForLastElementLogList(System.currentTimeMillis());
                    partParent2.setExitTimeForLastElementLogList(System.currentTimeMillis());
                    Part newPart = new Part(Simulation.idGenerator.generateNewId(), System.currentTimeMillis());
                    newPart.setParentPart1(partParent1);
                    newPart.setParentPart2(partParent2);
                    idPartList++;
                    idPartListAfterPreProcessing++;
                    if(SimulationUtils.getRandom(0.04)) {
                        Simulation.preProcessing.listAdd(newPart);
                    } else
                        Simulation.distribution.listAdd(newPart);
                    queue1 += getQueue();
                    i1 ++;
                    queue2 += getQueueAfterPreProcessing();
                    i2 ++;
                    System.out.println("1 создан в результате сборки, очередь на данный момент: " + getQueue() + " / " + getQueueAfterPreProcessing()
                        + " Всего покинуло этот блок: " + idPartList);
                    text1.setText(String.valueOf(getQueue()));
                    text2.setText(String.valueOf(getQueueAfterPreProcessing()));
                } catch (Exception e) {
                    System.out.print("");
                }
            } else
                break;
        }
    }

    @Override
    public void listAdd(Part part) {
        part.addLog(new Log(System.currentTimeMillis(), BlockType.Assembly));
        partList.add(part);
        queue1 += getQueue();
        i1 ++;
        System.out.println("Поступил 1 элемент в блок сборки, очередь на данный момент: " + getQueue());
    }

    @Override
    public void listAddPartAfterPreProcessing(Part part) {
        part.addLog(new Log(System.currentTimeMillis(), BlockType.Assembly));
        partListAfterPreProcessing.add(part);
        queue2 += getQueueAfterPreProcessing();
        i2 ++;
        System.out.println("Поступил 1 элемент в блок сборки (из блока предварительной обработки), очередь на данный момент: " + getQueueAfterPreProcessing());
    }

    @Override
    public long getQueue() {
        return this.partList.size() - this.idPartList;
    }

    public List getQueueList() {
        return partList;
    }

    public List getQueueAfterPreProcessingList() {
        return partListAfterPreProcessing;
    }

    @Override
    public void clear() {
        idPartList = 0;
        idPartListAfterPreProcessing = 0;
        partList.clear();
        partListAfterPreProcessing.clear();
        try {
            text1.setText("0");
            text2.setText("0");
        } catch (Exception e) {
            System.out.print("");
        }
    }

    @Override
    public long getQueueAfterPreProcessing() {
        return this.partListAfterPreProcessing.size() - this.idPartListAfterPreProcessing;
    }

    @Override
    public void setText(Text text) {
        this.text1 = text;
    }

    @Override
    public void setTextAfterPreProcessing(Text text) {
        this.text2 = text;
    }
}
