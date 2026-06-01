package com.example.demo.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Functions {
    public static BigDecimal validateTotalAmount(BigDecimal totalAmount, Float discount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) > 0 && discount > 0) {
            totalAmount = totalAmount.divide(BigDecimal.valueOf(discount), RoundingMode.HALF_UP);
            return totalAmount;
        }
        if (discount == null) {
            return totalAmount;
        }
        if (totalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }
        return totalAmount;
    }

    public static boolean isANumber(){
        return false;
    }
}
