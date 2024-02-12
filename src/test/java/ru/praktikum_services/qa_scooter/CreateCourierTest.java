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

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {
    private CourierClient courierClient;
    private CourierData courier;
    private int courierId;

    @Before
    @Step("Создания тестовых данных")
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    @Step("Удаление данных курьера")
    public void cleanUp() {
        if (courierId != 0) {
            courierClient.deleteCourier(String.valueOf(courierId));
        }
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Позитивная проверка создания курьера")
    public void createCourierTest() {
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().statusCode(SC_CREATED).body("ok", is(true));
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("Негатавная проверка создания курьера с существующим логином")
    public void createDuplicateCourierTest() {
        courierClient.createCourier(courier);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().statusCode(SC_CONFLICT).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
        courierId = courierClient.loginCourier(CourierCredentials.from(courier)).extract().jsonPath().getInt("id");
    }

    @Test
    @DisplayName("Создание курьера с пустым полем login")
    @Description("Негативная проверка создания курьера с пустым полем login")
    public void createCourierWithEmptyLoginTest() {
        courier.setLogin(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с пустым полем password")
    @Description("Негативная проверка создания курьера с пустым полем password")
    public void createCourietEmptyPasswordTest() {
        courier.setPassword(null);
        ValidatableResponse response = courierClient.createCourier(courier);
        response.assertThat().statusCode(SC_BAD_REQUEST).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}