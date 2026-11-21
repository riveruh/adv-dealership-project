
package com.dealership;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ContractDataManager {

    private static final String FILE_NAME = "contracts.csv";

    public void saveContract(Contract contract) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(FILE_NAME, true))) {

            Vehicle vehicle = contract.getVehicle();

            StringBuilder line = new StringBuilder();

            if (contract instanceof SalesContract) {
                line.append("SALE");
            } else if (contract instanceof LeaseContract) {
                line.append("LEASE");
            } else {
                throw new IllegalArgumentException("Unknown contract type");
            }

            line.append("|").append(contract.getDate());
            line.append("|").append(contract.getCustomerName());
            line.append("|").append(contract.getCustomerEmail());

            line.append("|").append(vehicle.getVin());
            line.append("|").append(vehicle.getYear());
            line.append("|").append(vehicle.getMake());
            line.append("|").append(vehicle.getModel());
            line.append("|").append(vehicle.getVehicleType());
            line.append("|").append(vehicle.getColor());
            line.append("|").append(vehicle.getOdometer());
            line.append("|").append(formatMoney(vehicle.getPrice()));

            if (contract instanceof SalesContract sale) {

                line.append("|").append(formatMoney(sale.getSalesTax()));
                line.append("|").append(formatMoney(sale.getRecordingFee()));
                line.append("|").append(formatMoney(sale.getProcessingFee()));
                line.append("|").append(formatMoney(sale.getTotalPrice()));
                line.append("|").append(sale.isFinanced() ? "YES" : "NO");
                line.append("|").append(formatMoney(sale.getMonthlyPayment()));

            } else if (contract instanceof LeaseContract lease) {

                line.append("|").append(formatMoney(lease.getExpectedEndingValue()));
                line.append("|").append(formatMoney(lease.getLeaseFee()));
                line.append("|").append(formatMoney(lease.getTotalPrice()));
                line.append("|").append(formatMoney(lease.getMonthlyPayment()));
            }

            writer.write(line.toString());
            writer.newLine();

            System.out.println("Contract saved successfully!");

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to save contract to " + FILE_NAME, e);
        }
    }

    private String formatMoney(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }
}
