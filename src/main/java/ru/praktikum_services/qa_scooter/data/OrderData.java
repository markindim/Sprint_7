package ru.praktikum_services.qa_scooter.data;

import java.util.List;

public class OrderData {
    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private String deliveryDate;
    private String comment;
    private int rentTime;
    private List<String> color;

    public OrderData(List<String> color) {
        this.firstName = "Смирнов";
        this.lastName = "Николай";
        this.address = "г.Москва, ул. Краснодонская, д.28";
        this.metroStation = 5;
        this.phone = "+79775641234";
        this.deliveryDate = "2024-02-21";
        this.comment = "Я не надолго";
        this.rentTime = 1;
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMetroStation() {
        return metroStation;
    }

    public void setMetroStation(int metroStation) {
        this.metroStation = metroStation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRentTime() {
        return rentTime;
    }

    public void setRentTime(int rentTime) {
        this.rentTime = rentTime;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }
}
