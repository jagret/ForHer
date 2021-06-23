package com.example.forher;

public class Problems {
    public String name,phone,date,place,image;

    public Problems()
    {

    }

    public Problems(String name, String phone, String date, String image, String place) {
        this.name = name;
        this.phone = phone;
        this.image = image;
        this.date=date;
        this.place=place;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPassword(String password) {
        this.place = place;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setAddress(String address) {
        this.date = date;
    }

}
