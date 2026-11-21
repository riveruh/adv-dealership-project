
package com.dealership;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SalesContract extends Contract {

    private static final BigDecimal SALES_TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal RECORDING_FEE = new BigDecimal("100.00");

    private boolean financed;

    public SalesContract(String date, String customerName,
                         String customerEmail, Vehicle vehicle,
                         boolean financed) {
        super(date, customerName, customerEmail, vehicle);
        this.financed = financed;
    }

    public boolean isFinanced() {
        return financed;
    }

    public void setFinanced(boolean financed) {
        this.financed = financed;
    }

    public BigDecimal getSalesTax() {
        return getVehicle().getPrice()
                .multiply(SALES_TAX_RATE)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getRecordingFee() {
        return RECORDING_FEE;
    }

    public BigDecimal getProcessingFee() {
        if (getVehicle().getPrice().compareTo(
                new BigDecimal("10000")) < 0) {
            return new BigDecimal("295.00");
        }

        return new BigDecimal("495.00");
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getVehicle().getPrice()
                .add(getSalesTax())
                .add(getRecordingFee())
                .add(getProcessingFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        if (!financed) {
            return BigDecimal.ZERO.setScale(2);
        }

        BigDecimal vehiclePrice = getVehicle().getPrice();

        double annualRate;
        int months;

        if (vehiclePrice.compareTo(
                new BigDecimal("10000")) >= 0) {
            annualRate = 0.0425;
            months = 48;
        } else {
            annualRate = 0.0525;
            months = 24;
        }

        double monthlyRate = annualRate / 12;
        double principal = getTotalPrice().doubleValue();

        double monthlyPayment = principal * monthlyRate /
                (1 - Math.pow(1 + monthlyRate, -months));

        return BigDecimal.valueOf(monthlyPayment)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
