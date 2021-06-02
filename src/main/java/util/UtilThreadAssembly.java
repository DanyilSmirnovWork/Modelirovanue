package util;

import javafx.scene.text.Text;
import model.Part;

public interface UtilThreadAssembly extends UtilThread {
    void listAddPartAfterPreProcessing(Part part);
    long getQueueAfterPreProcessing();
    void setTextAfterPreProcessing(Text text);
}
