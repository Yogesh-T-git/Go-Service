

package com.user.goservice.VehicleSales;

public class VehicleSales {
    public String model, price, manufactureDate, registrationNo, mile, condition, url, description;

    public VehicleSales(String model, String price, String manufactureDate, String registrationNo,
                        String mile, String condition, String url , String description) {
        this.model = model;
        this.price = price;
        this.manufactureDate = manufactureDate;
        this.registrationNo = registrationNo;
        this.mile = mile;
        this.condition = condition;
        this.url = url;
        this.description = description;
    }
}
