package ru.praktikum_services.qa_scooter.order;

import ru.praktikum_services.qa_scooter.data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestOrder {
    private static final String ORDERS_CREATE_PATH = "/api/v1/orders";
    private static final String ORDERS_LIST_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrders(OrderData orderData) {
        return given()
                .spec(requestSpecification())
                .body(orderData)
                .when()
                .post(ORDERS_CREATE_PATH)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse orderList() {
        return given()
                .spec(requestSpecification())
                .when()
                .get(ORDERS_LIST_PATH)
                .then();
    }
}