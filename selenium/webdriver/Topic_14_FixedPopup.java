package webdriver;

import java.awt.AWTException;
import java.awt.Robot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_FixedPopup {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	Actions action;
	JavascriptExecutor jsExecutor;

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Mac")) { // Mac
			System.setProperty("webdriver.chrome.driver", projectPath + "/browserDriver/chromedriver");
		} else { // Windows
			System.setProperty("webdriver.chrome.driver", projectPath + "\\browserDrivers\\chromedriver.exe");
		}

		driver = new ChromeDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor) driver;

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	public void TC_01_Ngoaingu24() {
		driver.get("https://ngoaingu24h.vn/");
		By loginPopup = By.xpath("(//div[@id='modal-login-v1'])[1]");
		
		//Khi vao page thi check popup nay chua hien thị
		Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());
		
		//Click vao button login
		driver.findElement(By.cssSelector("button.login_")).click();
		
		//Popup login hien thi
		Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());
		
		//Nhap thong tin 
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] input#account-input")).sendKeys("automationtesting");
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] input#password-input")).sendKeys("automationtesting");
		driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] button.btn-login-v1")).click();
		sleepInSecond(3);
		
		//Verify error message hien thi
		Assert.assertEquals(driver.findElement(By.cssSelector("div[id='modal-login-v1'][style] div.error-login-panel")).getText(), "Tài khoản không tồn tại!");
			
		
	}


	public void TC_02_Zingpoll() {
	driver.get("https://zingpoll.com/");
	
	///Khi mo ra thi khong co popup
	System.out.println("Start"+ getDateTimeNow());
	Assert.assertEquals(driver.findElements(By.cssSelector("div[role='dialog']")).size(), 0);
	System.out.println("End"+ getDateTimeNow());
	//Click vao dang nhap de hien thi popup
	driver.findElement(By.xpath("span[class*='Userstyle__ItemText']")).click();
	
	//verify pop up hien thi
	Assert.assertTrue(driver.findElement(By.cssSelector("div[role='dialog']")).isDisplayed());
	
	//Click vao dang nhap bang email link
	driver.findElement(By.cssSelector("p.login-with-email")).click();
	
	//Click vao dang nhap button 
	driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
	
	//Kiem tra hai error message hien thi
	Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Email không được để trống']")).isDisplayed());
	Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Mật khẩu không được để trống']")).isDisplayed());
	
	
	//Close popup
	driver.findElement(By.xpath("img.close-img")).click();
	
	Assert.assertEquals(driver.findElements(By.cssSelector("div[role='dialog']")).size(), 0);
	
	
		
}
	@Test
	public void TC_03_Facebook() {
	driver.get("https://www.facebook.com/");
	driver.findElement(By.xpath("open-registration-form-button")).click();
	Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).isDisplayed());
	//Close popup
	driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/parent::div/img")).click();
	sleepInSecond(3);
	
	//verify popup da duoc close
	Assert.assertEquals(driver.findElements(By.xpath("//div[text()='Sign Up']/parent::div/parent::div")).size(),0);
	
		
	}
	

	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	public String getDateTimeNow() {	
	
	Date date = new Date();
	return date.toString();
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getContentFile(String filePath) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(filePath);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}

	public void dragAndDropHTML5(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.cssSelector(sourceLocator));
		WebElement target = driver.findElement(By.cssSelector(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

	}
}