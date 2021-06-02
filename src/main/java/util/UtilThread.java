package util;

import javafx.scene.text.Text;
import model.Part;

import java.util.List;

public interface UtilThread {
    /** Добавляет деталь в лист */
    void listAdd(Part part);
    /** Возвращает количество деталей в очереди */
    long getQueue();
    /** Очищает всё поля */
    void clear();
    /** Устанавливает значение очереди */
    void setText(Text text);
}
