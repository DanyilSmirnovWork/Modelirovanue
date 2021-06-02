package api;

import markupControllers.SimulationProjectController;
import model.Report;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Класс, который записывает отчёт в MS Word
 */
public class Typesetter {
    private final String path = System.getProperty("user.dir") + "/src/main/resources/Reports/";
    List<Report> reports;
    long partsEnter;
    long partsOutMax;
    long partsOut;

    public Typesetter(List<Report> reports, long partsEnter) {
        this.reports = reports;
        this.partsEnter = partsEnter;
        this.partsOutMax = partsEnter * 3;
        setPartsOut();
        try {
            print();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setPartsOut() {
        this.partsOut = reports.get(reports.size() - 1).out();
    }

    private void print() throws IOException {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet("Отчёт");
        String pathName = path + "Report" + (Objects.requireNonNull(new File(path).listFiles()).length + 1) + ".xls";
        FileOutputStream fos = new FileOutputStream(pathName);

        Font font = wb.createFont();
        font.setFontHeightInPoints((short) 18);
        font.setFontName("Times New Roman");
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFont(font);

        Row sheetRow1 = sheet.createRow(1);
        Cell cell1 = sheetRow1.createCell(0);
        cell1.setCellValue("Дата отчёта");
        Cell cell2 = sheetRow1.createCell(1);
        cell2.setCellValue(String.valueOf(new Date()));

        Row sheetRow2 = sheet.createRow(2);
        Cell cell3 = sheetRow2.createCell(0);
        cell3.setCellValue("Деталей вошло");
        Cell cell4 = sheetRow2.createCell(1);
        cell4.setCellValue(String.valueOf(partsEnter));

        Row sheetRow3 = sheet.createRow(3);
        Cell cell5 = sheetRow3.createCell(0);
        cell5.setCellValue("Деталей вышло");
        Cell cell6 = sheetRow3.createCell(1);
        cell6.setCellValue(String.valueOf(partsOut));

        Row sheetRow4 = sheet.createRow(4);
        Cell cell7 = sheetRow4.createCell(0);
        cell7.setCellValue("Должно выйти");
        Cell cell8 = sheetRow4.createCell(1);
        cell8.setCellValue(String.valueOf(partsEnter / 2));

        cell1.setCellStyle(cellStyle);
        cell2.setCellStyle(cellStyle);
        cell3.setCellStyle(cellStyle);
        cell4.setCellStyle(cellStyle);
        cell5.setCellStyle(cellStyle);
        cell6.setCellStyle(cellStyle);
        cell7.setCellStyle(cellStyle);
        cell8.setCellStyle(cellStyle);

        int l = 1;

        for(int i = 0; i < reports.size(); i++) {
            Report report = reports.get(i);
            Row row1 = sheet.createRow(i + 5 + l);
            Cell cellId1 = row1.createCell(0);
            cellId1.setCellValue("Блок ");
            Cell cellId2 = row1.createCell(1);
            cellId2.setCellValue(String.valueOf(report.blockType()));

            Row row2 = sheet.createRow(i + 6 + l);
            Cell cellId3 = row2.createCell(0);
            cellId3.setCellValue("Среднее количество деталей в очереди ");
            Cell cellId4 = row2.createCell(1);
            cellId4.setCellValue(String.valueOf(report.queueAverage()));

            Row row3 = sheet.createRow(i + 7 + l);
            Cell cellId5 = row3.createCell(0);
            cellId5.setCellValue("Среднее время в очереди ");
            Cell cellId6 = row3.createCell(1);
            cellId6.setCellValue(String.valueOf(report.timeAverage()));

            Row row4 = sheet.createRow(i + 8 + l);
            Cell cellId7 = row4.createCell(0);
            cellId7.setCellValue("Максимальное время в очереди ");
            Cell cellId8 = row4.createCell(1);
            cellId8.setCellValue(String.valueOf(report.timeMax()));

            Row row5 = sheet.createRow(i + 9 + l);
            Cell cellId9 = row5.createCell(0);
            cellId9.setCellValue("Количество деталей вошедших блок ");
            Cell cellId10 = row5.createCell(1);
            cellId10.setCellValue(String.valueOf(report.all()));

            Row row6 = sheet.createRow(i + 10 + l);
            Cell cellId11 = row6.createCell(0);
            cellId11.setCellValue("Среднее количество деталей в очереди ");
            Cell cellId12 = row6.createCell(1);
            cellId12.setCellValue(String.valueOf(report.out()));

            l +=6;

            cellId1.setCellStyle(cellStyle);
            cellId2.setCellStyle(cellStyle);
            cellId3.setCellStyle(cellStyle);
            cellId4.setCellStyle(cellStyle);
            cellId5.setCellStyle(cellStyle);
            cellId6.setCellStyle(cellStyle);
            cellId7.setCellStyle(cellStyle);
            cellId8.setCellStyle(cellStyle);
            cellId9.setCellStyle(cellStyle);
            cellId10.setCellStyle(cellStyle);
            cellId11.setCellStyle(cellStyle);
            cellId12.setCellStyle(cellStyle);
        }
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        wb.write(fos);
        fos.close();
        SimulationProjectController.lastFileName = pathName;
    }
}
