package model.enums;

/**
 * Перечисление Speeds, которое содержит все возможные скорости произведения симуляции
 */
public enum Speeds {
    X1(288), X2(360), X3(480), X4(720), X5(1440), X6(2880), X7(8640);

    private final long speed;

    Speeds(long speed) {
        this.speed = speed;
    }

    public long getSpeed() {
        return this.speed;
    }

    @Override
    public String toString() {
        return "Speeds{" +
                "speed=" + speed +
                '}';
    }
}
