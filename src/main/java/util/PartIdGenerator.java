package util;

public class PartIdGenerator {
    long id;

    public PartIdGenerator() {
        restart();
    }

    public long generateNewId() {
        this.id++;
        return this.id;
    }

    public void restart() {
        this.id = 0;
    }

    public long getId() {
        return this.id;
    }
}
