package api;

import model.Log;
import model.Part;
import model.Report;
import model.enums.BlockType;
import model.enums.Speeds;


import java.util.ArrayList;
import java.util.List;

/**
 * Класс создающий отчёт
 */
public record ReportGenerator(List<Part> queueAssembly1, long queue1,
                              List<Part> queueAssembly2, long queue2,
                              List<Part> queuePreProcessing, long queue3,
                              List<Part> queueDistribution, long queue4,
                              long partsEnter,
                              long timeStart, Speeds speedSimulation) {


    public void generate() {
        List<Report> reports = new ArrayList<>();
        reports.add(queueAnalyzer(queuePreProcessing, BlockType.PreProcessing, queue3));
        reports.add(queueAnalyzer(queueAssembly1, BlockType.Assembly, queue1));
        reports.add(queueAnalyzer(queueAssembly2, BlockType.Assembly, queue2));
        reports.add(queueAnalyzer(queueDistribution, BlockType.Distribution, queue4));
        for(Report report : reports) {
            System.out.println(report.toString());
        }
        print(reports);
    }

    private Report queueAnalyzer(List<Part> list, BlockType blockType, long queue) {
        long maxTime = 0;
        long timeAverage = 0;
        int i = 0;
        for(Part part : list) {
            for(Log log : part.getLogList()) {
                if(log.getBlockType().equals(blockType)) {
                    if(log.getExitTime() > 0){
                        long timeInBlock = log.getExitTime() - log.getEntryTime();
                        if (maxTime < timeInBlock) {
                            maxTime = timeInBlock;
                        }
                        timeAverage += timeInBlock;
                        i++;
                    }
                }
            }
        }
        timeAverage = timeAverage / i * speedSimulation.getSpeed() / 60000;
        maxTime = maxTime * speedSimulation.getSpeed() / 60000;

        return new Report(blockType, queue, timeAverage, maxTime, i, list.size());
    }

    private void print(List<Report> reports) {
        Typesetter typesetter = new Typesetter(reports, partsEnter);
    }
}
