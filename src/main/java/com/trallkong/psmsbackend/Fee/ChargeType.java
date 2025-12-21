package com.trallkong.psmsbackend.Fee;

import lombok.Getter;

/* 计费方式：0-按建筑面积，1-按套内面积，2-按户，3-按用量，4-固定金额 */
@Getter
public enum ChargeType {
    BuildingArea(0, "按建筑面积"),
    InsideArea(1, "按套内面积"),
    HouseHold(2, "按户"),
    Usage(3, "按用量"),
    FixedAmount(4, "固定金额");

    private final int value;
    private final String name;

    ChargeType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static ChargeType getChargeType(int value) {
        for (ChargeType type : ChargeType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    public static String getName(int value) {
        for (ChargeType type : ChargeType.values()) {
            if (type.value == value) {
                return type.name;
            }
        }
        return null;
    }
}
