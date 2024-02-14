package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.data.CourierCredentials;
import ru.praktikum_services.qa_scooter.data.CourierData;
import ru.praktikum_services.qa_scooter.data.CourierGenerator;

import java.util.concurrent.ThreadLocalRandom;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DeleteCourierTest {
    private CourierClient courierClient;
    private CourierData courier;
    private int courierId;

    @Before
    @Step("Подготовка тестовых данных для выполнения теста")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.loginCourier(CourierCredentials.from(courier));
        courierId = response.extract().path("id");
    }

    @After
    @Step("Удаление данных курьера")
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.deleteCourier(String.valueOf(courierId));
        }
    }

    @Test
    @DisplayName("Удаление курьера из системы")
    @Description("Позитивная проверка удаления курьера из системы")
    public void deleteCourierTest() {
        ValidatableResponse response = courierClient.deleteCourier(String.valueOf(courierId));
        response.assertThat()
                .statusCode(SC_OK)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Удаление курьера без id")
    @Description("Негативная проверка удаления курьера без передачи id")
    public void deleteCourierNotIdTest() {
        ValidatableResponse response = courierClient.deleteCourier("");
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для удаления курьера"));
    }

    @Test
    @DisplayName("Удаление курьера с несуществующим id")
    @Description("Негативная проверка удаления курьера с несуществующим id")
    public void deleteCourierWithNonExistIdTest() {
        ValidatableResponse response = courierClient.deleteCourier(String.valueOf(ThreadLocalRandom.current().nextInt(1, 9)));
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Курьера с таким id нет."));
    }
}