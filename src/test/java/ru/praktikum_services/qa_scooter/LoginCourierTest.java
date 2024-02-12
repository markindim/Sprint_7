package ru.praktikum_services.qa_scooter;

import ru.praktikum_services.qa_scooter.client.CourierClient;
import ru.praktikum_services.qa_scooter.data.CourierCredentials;
import ru.praktikum_services.qa_scooter.data.CourierData;
import ru.praktikum_services.qa_scooter.data.CourierGenerator;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class LoginCourierTest {
    private CourierClient courierClient;
    private CourierData courier;
    private CourierCredentials courierCredentials;
    private int courierId;


    @Before
    @Step("Создания тестовых данных")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierClient.createCourier(courier);
        courierCredentials = CourierCredentials.from(courier);
    }

    @After
    @Step("Удаление данных курьера")
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.deleteCourier(String.valueOf(courierId));
        }
    }

    @Test
    @DisplayName("Аутентификация курьера")
    @Description("Позитивная проверка аутентификации курьера в системе")
    public void courierLoginTest() {
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat()
                .statusCode(SC_OK)
                .body("id", greaterThan(0))
                .extract()
                .path("id");
        courierId = response.extract().path("id");
    }

    @Test
    @DisplayName("Аутентификация курьера с пустым полем login")
    @Description("Негативная проверка аутентификация курьера с пустым полем login")
    public void courierLoginWithEmptyLoginTest() {
        courierCredentials.setLogin(null);
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Аутентификация курьера с пустым полем password")
    @Description("Негативная проверка аутентификация курьера с пустым полем password")
    public void courierLoginWithEmptyPasswordTest() {
        courierCredentials.setPassword("");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Аутентификация курьера c несуществующими данными")
    @Description("Негативная проверка аутентификация курьера с несуществующими данными")
    public void courierLoginNonExistDataTest() {
        courierCredentials.setLogin("lskdgld");
        courierCredentials.setPassword("215zsgsdg");
        ValidatableResponse response = courierClient.loginCourier(courierCredentials);
        response.assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}