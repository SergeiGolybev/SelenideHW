package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
            $(withText("Забро")).click();
            $("[data-test-id=notification]").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на "
                    + date), Duration.ofSeconds(15));
        }
    }
