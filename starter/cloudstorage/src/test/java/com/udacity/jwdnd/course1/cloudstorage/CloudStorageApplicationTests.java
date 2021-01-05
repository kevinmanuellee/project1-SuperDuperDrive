package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;
	private static String username;
	private static String password;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@BeforeEach
	public void beforeEach() {
		username = "kevin123";
		password = "password321";
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.createUser("kevin", "manuel", username, password);
		driver.get("http://localhost:" + this.port + "/login");
	}

	@AfterAll
	public static void afterAll() {
		if (driver != null) {
			driver.quit();
		}
	}

	@Test
	public void testAccessWithoutLogin(){
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testAccessWithLogin(){
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);


		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();

		Assertions.assertEquals("http://localhost:" + port + "/login?logout", driver.getCurrentUrl());
	}

	@Test
	public void testNote() throws InterruptedException {
		String title = "title123";
		String description = "description123";
		String editedTitle = "title321";
		String editedDescription = "description321";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open notes tab
		WebElement noteTab = homePage.getNotesTab();
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		//add new note
		wait.until(ExpectedConditions.elementToBeClickable( By.id("addNoteButton") )).click();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("note-title") )).sendKeys(title);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("note-description") )).sendKeys(description);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("saveButton") )).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/note", driver.getCurrentUrl());
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
		String displayedTitle = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteTitle"))).getText();
		String displayedDescription = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeNoteDescription"))).getText();
		Assertions.assertEquals(title, displayedTitle);
		Assertions.assertEquals(description, displayedDescription);

		//edit existing note
		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteEditButton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(editedTitle);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).clear();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-description"))).sendKeys(editedDescription);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("saveButton"))).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/note", driver.getCurrentUrl());
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//delete note
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("noteDeleteButton"))).click();

		//check result page to home page flow
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if note is deleted
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(noteTab)).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeNoteTitle().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeNoteDescription().getText());
	}

	@Test
	public void testCredential() throws InterruptedException {
		String urlC = "test.com";
		String usernameC = "userTest";
		String passwordC = "passTest";
		String editedUrlC = "google.com";
		String editedUsernameC = "userGoogle";
		String editedPasswordC = "passGoogle";

		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login(username,password);

		//Wait Driver
		WebDriverWait wait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/home");
		HomePage homePage = new HomePage(driver);
		Assertions.assertEquals("Home", driver.getTitle());

		//open credential tab
		WebElement credentialTab = homePage.getCredentialsTab();
		wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		//add new credential
		wait.until(ExpectedConditions.elementToBeClickable( By.id("addCredentialButton") )).click();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).sendKeys(urlC);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).sendKeys(usernameC);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).sendKeys(passwordC);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("saveCredentialChanges") )).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/credential", driver.getCurrentUrl());
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if the inputted note is consistent
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
		String displayedUrl = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUrl"))).getText();
		String displayedUsername = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialUsername"))).getText();
		String displayedPassword = wait.until(ExpectedConditions.elementToBeClickable(By.id("homeCredentialPassword"))).getText();
		Assertions.assertEquals(urlC, displayedUrl);
		Assertions.assertEquals(usernameC, displayedUsername);
		Assertions.assertNotEquals(usernameC, displayedPassword);

		//edit existing credential
		wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredentialButton"))).click();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).clear();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).clear();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).clear();
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-url") )).sendKeys(urlC);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-username") )).sendKeys(usernameC);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("credential-password") )).sendKeys(passwordC);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("saveCredentialChanges"))).click();

		//check result page to home page flow
		Assertions.assertEquals("http://localhost:" + port + "/credential", driver.getCurrentUrl());
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//delete credential
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("deleteCredentialButton"))).click();

		//check result page to home page flow
		Thread.sleep(1000);
		wait.until(ExpectedConditions.elementToBeClickable( By.id("successResult-to-home-link") )).click();
		Assertions.assertEquals("http://localhost:" + port + "/home", driver.getCurrentUrl());

		//check if credential is deleted
		driver.get("http://localhost:" + this.port + "/home");
		wait.until(ExpectedConditions.elementToBeClickable(credentialTab)).click();

		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialUrl().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialUsername().getText());
		Assertions.assertThrows(NoSuchElementException.class, () -> homePage.getHomeCredentialPassword().getText());

	}
}
