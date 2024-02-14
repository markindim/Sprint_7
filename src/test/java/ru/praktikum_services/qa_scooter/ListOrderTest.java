package ru.praktikum_services.qa_scooter;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.praktikum_services.qa_scooter.order.OrderClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class ListOrderTest {
    private OrderClient orderClient;

    @Test
    @DisplayName("Запрос на оплучение списка заказов")
    @Description("Позитивная проверка получение списка заказов")
    public void orderList() {
        orderClient = new OrderClient();
        ValidatableResponse responce = orderClient.orderList();
        responce.assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}