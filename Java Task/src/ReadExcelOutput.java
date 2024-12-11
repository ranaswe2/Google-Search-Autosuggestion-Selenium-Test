
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

public class ReadExcelOutput {
    public void printFileData(String excelPath) {

        try (FileInputStream fileInputStream = new FileInputStream(excelPath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            // Iterate through all sheets in the workbook
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                System.out.println("Sheet Name: " + sheet.getSheetName());

                // Iterate through all rows in the sheet
                for (Row row : sheet) {
                    // Iterate through all cells in the row
                    for (Cell cell : row) {
                        switch (cell.getCellType()) {
                            case STRING:
                                System.out.print(cell.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                System.out.print(cell.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                System.out.print(cell.getBooleanCellValue() + "\t");
                                break;
                            default:
                                System.out.print("UNKNOWN\t");
                                break;
                        }
                    }
                    System.out.println(); // Move to the next row
                }
                System.out.println("========================================");
            }

        } catch (IOException e) {
            System.out.println("Error reading Excel file: " + e.getMessage());
        }
    }
}
