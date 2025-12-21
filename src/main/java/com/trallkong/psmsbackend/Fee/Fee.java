package com.trallkong.psmsbackend.Fee;

import com.trallkong.psmsbackend.Date.Date;
import com.trallkong.psmsbackend.House.House;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Fee extends House {

    private ChargeItem chargeItem;
    private Double amount;
    private Double actualAmount;
    private Date date;

    public Fee(House house, ChargeItem chargeItem, Double amount, Double actualAmount, Date date) {
        super(house);
        this.chargeItem = chargeItem;
        this.amount = amount;
        this.actualAmount = actualAmount;
        this.date = date;
    }
}
