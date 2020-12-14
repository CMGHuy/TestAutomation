package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@Component
public class RunOpenText {

	private static final Logger LOG = LoggerFactory.getLogger(ReadBrowsingHistory.class);

	@Value("${file.web.driver.chrome.path}")
	private String fileChromeDriver;

	public void openOpenTextWindow(String openTextURL) throws InterruptedException {

		Path pathToChromeDriver = Paths.get(fileChromeDriver);

		if(!Files.exists(pathToChromeDriver)){
			LOG.info("File chrome driver is not available");
		}

		System.setProperty("webdriver.chrome.driver", String.valueOf(pathToChromeDriver));

		ChromeDriver driver = new ChromeDriver();
		HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
		URL url = executor.getAddressOfRemoteServer();
		SessionId session_id = driver.getSessionId();
		System.out.println(session_id.toString() + url + driver.getTitle());

		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 100);

		String processingURL = openTextURL;

		try {
			driver.get(processingURL);
			Thread.sleep(3000);
			/*driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
			WebElement firstResult = wait.until(presenceOfElementLocated(By.cssSelector("h3>div")));
			System.out.println(firstResult.getAttribute("textContent"));*/
		} finally {
			driver.quit();
		}
	}
}
