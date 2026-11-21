
package com.dealership;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LeaseContract extends Contract {

    private static final BigDecimal ENDING_VALUE_RATE =
            new BigDecimal("0.50");

    private static final BigDecimal LEASE_FEE_RATE =
            new BigDecimal("0.07");

    public LeaseContract(String date, String customerName,
                         String customerEmail, Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
    }

    public BigDecimal getExpectedEndingValue() {
        return getVehicle().getPrice()
                .multiply(ENDING_VALUE_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getLeaseFee() {
        return getVehicle().getPrice()
                .multiply(LEASE_FEE_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getExpectedEndingValue()
                .add(getLeaseFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {

        double annualRate = 0.04;
        int months = 36;

        double monthlyRate = annualRate / 12;
        double principal = getTotalPrice().doubleValue();

        double monthlyPayment = principal * monthlyRate /
                (1 - Math.pow(1 + monthlyRate, -months));

        return BigDecimal.valueOf(monthlyPayment)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
