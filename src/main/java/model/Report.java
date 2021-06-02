package model;

import model.enums.BlockType;

import java.util.Date;

/**
 * Класс Report описывает сущность отчёта
 */
public record Report(BlockType blockType, long queueAverage, long timeAverage,
                     long timeMax, long out, long all) {

    @Override
    public BlockType blockType() {
        return blockType;
    }

    @Override
    public long queueAverage() {
        return queueAverage;
    }

    @Override
    public long timeAverage() {
        return timeAverage;
    }

    @Override
    public long timeMax() {
        return timeMax;
    }

    @Override
    public long out() {
        return out;
    }

    @Override
    public long all() {
        return all;
    }

    @Override
    public String toString() {
        return "Report{" +
                "blockType=" + blockType +
                ", queueAverage=" + queueAverage +
                ", timeAverage=" + timeAverage +
                ", timeMax=" + timeMax +
                ", out=" + out +
                ", all=" + all +
                '}';
    }
}
