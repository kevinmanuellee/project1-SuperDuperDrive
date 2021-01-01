package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import starter.cloudstorage.src.test.java.com.udacity.jwdnd.course1.cloudstorage.SignupPage;
import starter.cloudstorage.src.test.java.com.udacity.jwdnd.course1.cloudstorage.LoginPage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String username;
	private static String password;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		username = "kevin123";
		password = "password321";
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	@Order(1)
	public void getSignupPage(){
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("signup", driver.getTitle());
		SignupPage signupPage = new SignupPage(driver);
		signupPage.createUser("kevin", "manuel", username, password);
	}

	@Test
	@Order(2)
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	@Order(3)
	public void getHomePage() {
		driver.get("http://localhost:" + this.port + "/Home");
		Assertions.assertEquals("Home", driver.getTitle());
	}

	@Test
	@Order(4)
	public void getResultPage() {
		driver.get("http://localhost:" + this.port + "/Result");
		Assertions.assertEquals("Result", driver.getTitle());
	}

}
