package model.enums;

/** Перечисление BlockType, которое содержит все типы блоков в симуляции */
public enum BlockType {
    Assembly("Сборка"), Distribution("Регулировка"), PreProcessing("Предварительная обработка");
    BlockType(String blockName) {
    }
}
