import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        WebDriver driver = Base.getDriver();

        try {
            driver.get("https://www.google.com");

            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.sendKeys("Dhaka");

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            List<WebElement> suggestions = driver.findElements(By.xpath("//ul[@class='G43f7e']/li"));

            List<String> suggestionsList = new ArrayList<>();
            for (WebElement suggestion : suggestions) {
                System.out.println(suggestion.getText());
            }

//            // Print the suggestions
//            System.out.println("Suggestions:");
//            for (String suggestion : suggestionsList) {
//                System.out.println(suggestion);
//            }
        } finally {
            driver.quit();
        }
    }
}
