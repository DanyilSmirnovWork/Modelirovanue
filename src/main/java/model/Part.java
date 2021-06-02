package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс Part описывает сущность детали
 */
public class Part {
    /** Порядковый номер детали в симуляции */
    private final long id;
    /** Время создания в симуляции */
    private final long creationTime;
    /** Время выхода из симуляции */
    private long exitSimulationTime;
    /** Лист фиксирующий все перемещения детали */
    private List<Log> logList = new ArrayList<>();
    /** Родительская деталь 1*/
    private Part parentPart1;
    /** Родительская деталь 2*/
    private Part parentPart2;

    public Part(long id, long creationTime) {
        this.id = id;
        this.creationTime = creationTime;
    }

    /** Метод добавляющий новый лог
     * @param log новый лог
     */
    public void addLog(Log log) {
        logList.add(log);
    }

    /**
     * Метод устанавливает значение выходного времени для блока в котором сейчас находится деталь
     * @param exitTime время выхода из блока
     */
    public void setExitTimeForLastElementLogList(long exitTime) {
        Log updatedLog = logList.get(logList.size() - 1);
        updatedLog.setExitTime(exitTime);
        logList.set(logList.size() - 1, updatedLog);
    }

    public long getId() {
        return id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public Part getParentPart1() {
        return parentPart1;
    }

    public Part getParentPart2() {
        return parentPart2;
    }

    public List<Log> getLogList() {
        return logList;
    }

    public void setParentPart1(Part parentPart1) {
        this.parentPart1 = parentPart1;
    }

    public void setParentPart2(Part parentPart2) {
        this.parentPart2 = parentPart2;
    }
}
