package com.trallkong.psmsbackend.House;

import lombok.Data;

@Data
public class House {

    private String houseID;
    private String ownerName;
    private Double area;
    private HouseType type;

    public House(String houseID, String ownerName, Double area, HouseType type) {
        this.houseID = houseID;
        this.ownerName = ownerName;
        this.area = area;
        this.type = type;
    }

    public House(House house) {
        this.houseID = house.houseID;
        this.ownerName = house.ownerName;
        this.area = house.area;
        this.type = house.type;
    }
}
