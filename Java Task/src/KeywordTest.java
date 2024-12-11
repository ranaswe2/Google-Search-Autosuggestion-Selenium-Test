import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class KeywordTest {
    public static void main(String[] args) throws IOException {
        WebDriver driver = Base.getDriver();

        String excelPath = "external/Excel.xlsx";
        FileInputStream fileInputStream = new FileInputStream(excelPath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        String today = LocalDate.now().getDayOfWeek().toString().toLowerCase(); // Get today's day

        Sheet sheet = workbook.getSheet(today.substring(0, 1).toUpperCase() + today.substring(1));

        for (Row row : sheet) {
            Cell keywordCell = row.getCell(2); // 2nd column
            if (keywordCell == null) continue;
            String keyword = keywordCell.getStringCellValue();

            driver.get("https://www.google.com");
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys(keyword);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@class='G43f7e']/li"));
            String longest = "";
            String shortest = keyword;

            for (WebElement suggestion : suggestions) {
                String text = suggestion.getText();
                if (text.length() > longest.length()) longest = text;
                if (text.length() < shortest.length()) shortest = text;
            }

            Cell longestCell = row.createCell(3);
            Cell shortestCell = row.createCell(4);
            longestCell.setCellValue(longest);
            shortestCell.setCellValue(shortest);
        }

        fileInputStream.close();
        FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();

        driver.quit();

        ReadExcelOutput readExcelOutput = new ReadExcelOutput();
        readExcelOutput.printFileData("external/Excel.xlsx");
    }
}

