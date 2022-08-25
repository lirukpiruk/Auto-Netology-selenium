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

    @Test
    void shouldSubmitFormWithNameWithYo() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пётр Пупкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfNameEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfPhoneEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкин Василий");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfFormEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotSendFormIfCheckBoxOff() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкин Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.className("button")).click();

        Boolean expected = true;
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfNameLatin() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Pupkin Petr");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfNameWithSpecCharacter() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("@#на&G@");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfProneWithSpecCharacter() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкин Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+791122233&7");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfNameWithNumber() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("123 6545");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldSetErrorMsgIfProneWithLetter() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Пупкин Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+791122233&7");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateIfJustName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        Boolean expected = true;
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid")).isDisplayed();

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateIfJustOneLetterInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("В");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        Boolean expected = true;
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid")).isDisplayed();

        assertEquals(expected, actual);
    }

    @Test
    void shouldNotValidateIOneHundredLettersInName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("аааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааааа");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79112223344");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();

        Boolean expected = true;
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid")).isDisplayed();

        assertEquals(expected, actual);
    }

}
