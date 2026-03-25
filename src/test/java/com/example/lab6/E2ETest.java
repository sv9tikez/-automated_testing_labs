package com.example.lab6;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class E2ETest {

    private static final String URL = "https://www.saucedemo.com";
    private static final String USER = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void login(String user, String pass) {
        driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.id("login-button")).click();
    }

    private WebElement waitFor(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Test
    @DisplayName("Сценарій 1: Повний цикл покупки — від входу до підтвердження замовлення")
    void testFullPurchaseFlow() {
        login(USER, PASSWORD);

        assertEquals("Products", waitFor(By.cssSelector(".title")).getText());

        WebElement firstAddBtn = waitFor(By.cssSelector(".btn_inventory"));
        firstAddBtn.click();

        assertEquals("1", driver.findElement(By.cssSelector(".shopping_cart_badge")).getText());

        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        assertTrue(driver.getCurrentUrl().contains("/cart.html"));

        waitFor(By.id("checkout")).click();

        waitFor(By.id("first-name")).sendKeys("Іван");
        driver.findElement(By.id("last-name")).sendKeys("Петренко");
        driver.findElement(By.id("postal-code")).sendKeys("79000");
        driver.findElement(By.id("continue")).click();

        waitFor(By.id("finish")).click();

        String header = waitFor(By.cssSelector(".complete-header")).getText();
        assertEquals("Thank you for your order!", header);
    }

    @Test
    @DisplayName("Сценарій 2: Додавання двох товарів та перевірка вмісту кошика")
    void testAddTwoItemsToCart() {
        login(USER, PASSWORD);
        waitFor(By.cssSelector(".inventory_item"));

        addItemByName("Sauce Labs Backpack");
        addItemByName("Sauce Labs Bike Light");

        assertEquals("2", driver.findElement(By.cssSelector(".shopping_cart_badge")).getText());

        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        List<String> cartNames = driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .map(WebElement::getText)
                .toList();

        assertTrue(cartNames.contains("Sauce Labs Backpack"));
        assertTrue(cartNames.contains("Sauce Labs Bike Light"));
    }

    private void addItemByName(String name) {
        driver.findElements(By.cssSelector(".inventory_item")).stream()
                .filter(item -> item.findElement(By.cssSelector(".inventory_item_name"))
                        .getText().equals(name))
                .findFirst()
                .orElseThrow()
                .findElement(By.cssSelector(".btn_inventory"))
                .click();
    }

    @Test
    @DisplayName("Сценарій 3: Невдалий вхід — некоректний логін/пароль")
    void testLoginWithInvalidCredentials() {
        login("wrong_user", "wrong_pass");

        String error = waitFor(By.cssSelector("[data-test='error']")).getText();
        assertTrue(error.contains("Username and password do not match"),
                "Очікується повідомлення про невірні дані. Фактично: " + error);

        assertTrue(driver.getCurrentUrl().matches("https://www.saucedemo.com/?"));
    }
}
