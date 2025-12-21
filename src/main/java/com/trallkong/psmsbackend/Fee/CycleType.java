package com.trallkong.psmsbackend.Fee;

import lombok.Getter;

@Getter
public enum CycleType {
    Monthly(0, "月"),
    Quarterly(1, "季"),
    HalfYearly(2, "半年"),
    Yearly(3, "年"),
    OneTime(4, "一次性");

    private final int value;
    private final String name;

    CycleType(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static CycleType getCycleType(int value) {
        for (CycleType cycleType : CycleType.values()) {
            if (cycleType.value == value) {
                return cycleType;
            }
        }
        return null;
    }

    public static String getName(int value) {
        for (CycleType cycleType : CycleType.values()) {
            if (cycleType.value == value) {
                return cycleType.name;
            }
        }
        return null;
    }
}
