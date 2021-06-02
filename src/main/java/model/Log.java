package model;

import model.enums.BlockType;

/**
 * Класс Log, содержащий информация о нахождении детали в определенном блоке
 */
public class Log {
    /** Блок, в котором находится деталь */
    private final BlockType blockType;
    /** Время входа в блока */
    private final long entryTime;
    /** Время выхода из блока */
    private long exitTime;

    public Log(long entryTime, BlockType blockType) {
        this.entryTime = entryTime;
        this.blockType = blockType;
    }

    public void setExitTime(long exitTime) {
        this.exitTime = exitTime;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    public BlockType getBlockType() {
        return blockType;
    }
}
