package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.ownText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegistrationTest {

    @BeforeEach
    public void openPage() {
        open("http://localhost:9999/");
    }

    @Test
    public void shouldSendForm() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").shouldBe(Condition.text("Успешно! Встреча успешно забронирована на "
                + date), Duration.ofSeconds(15));
    }

    @Test
    public void sendFormCityNotInList() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Грязи");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid [class='input__sub']").shouldHave(ownText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void sendFormWrongDate() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid [class='input__sub']").shouldHave(ownText("Заказ на выбранную дату невозможен"));

    }

    @Test
    public void sendFormWrongName() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Ivanov Ivan");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid [class='input__sub']").shouldBe(ownText("Имя и Фамилия указаны "));
    }

    @Test
    public void sendFormWrongPhone() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("79050444444");
        $("[data-test-id=agreement] span").click();
        $(withText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid [class='input__sub']").shouldHave(ownText("Телефон указан неверно."));
    }

    @Test
    public void sendFormCheckBoxOff() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $(withText("Забронировать")).click();
        $(" [data-test-id='agreement'].input_invalid [class='checkbox__text']").shouldHave(ownText("Я соглашаюсь"));
    }

    @Test
    public void shouldSendFormWithoutCity() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='city'].input_invalid [class='input__sub']").shouldHave(ownText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldSendFormWithoutDate() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='date'] .input_invalid [class='input__sub']").shouldHave(ownText("Неверно введена дата"));

    }

    @Test
    public void shouldSendFormWithoutName() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=phone] input").val("+79050444444");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='name'].input_invalid [class='input__sub']").shouldBe(ownText("Поле обязательно для заполнения"));

    }

    @Test
    public void shouldSendFormWithoutPhone() {

        Configuration.holdBrowserOpen = true;
        Configuration.browserSize = "1440x900";
        $("[data-test-id=city] input").val("Москва");
        String date = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").val(date);
        $("[data-test-id=name] input").val("Иванов Иван");
        $("[data-test-id=agreement] span").click();
        $(byText("Забронировать")).click();
        $("[data-test-id='phone'].input_invalid [class='input__sub']").shouldBe(ownText("Поле обязательно для заполнения"));

    }


}

