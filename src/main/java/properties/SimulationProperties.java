package properties;

import model.enums.Speeds;

/**
 * Класс SimulationProperties определяет свойства симуляции
 * @param sourceOfPartsTime время создания деталей
 * @param preProcessingTime время предварительной обработки
 * @param assemblyTime время сборки
 * @param distributionTime время распределения
 * @param timeSimulation время симуляции
 * @param speedSimulation скорость симуляции
 */
public record SimulationProperties(long sourceOfPartsTime, long preProcessingTime, long assemblyTime, long distributionTime, long timeSimulation,  Speeds speedSimulation) {
    @Override
    public long sourceOfPartsTime() {
        return sourceOfPartsTime;
    }

    @Override
    public long preProcessingTime() {
        return preProcessingTime;
    }

    @Override
    public long assemblyTime() {
        return assemblyTime;
    }

    @Override
    public long distributionTime() {
        return distributionTime;
    }

    @Override
    public long timeSimulation() {
        return timeSimulation;
    }

    @Override
    public Speeds speedSimulation() {
        return speedSimulation;
    }
}