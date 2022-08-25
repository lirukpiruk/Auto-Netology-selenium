package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class DebitCardOrderFormTest {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void Setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldSubmitFormWithValidDataNameFirst() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Пупкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSubmitFormWithValidDataSurnameFirst() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкин Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSubmitFormWithValidDataCapsLock() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("ПУПКИН ВАСИЛИЙ");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSubmitFormWithValidDataWithDash() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкина-Губкина Анна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSubmitFormWithValidDataWithDoubleName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкина Анна Мария");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    /*@Test
    void shouldSubmitFormWithNameWithYo() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пётр Пупкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }*/



}
