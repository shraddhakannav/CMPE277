package com.example.shraddha.cmpe277.Trust;

import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.SenseApplication;

import java.util.Calendar;


public class TrustCalculation {
    private double mean;
    private double variance;
    private double dataTrust;
    private double currentValue;
    private double communicationTrust;
    private double energyTrust;
    private ParseDataAccessor dataAccessor = SenseApplication.getParseDAInstance();

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public double getDataTrust() {
        return dataTrust;
    }

    public void setDataTrust(double dataTrust) {
        this.dataTrust = dataTrust;
    }

    public double getCommunicationTrust() {
        return communicationTrust;
    }

    public void setCommunicationTrust(double communicationTrust) {
        this.communicationTrust = communicationTrust;
    }

    public double getEnergyTrust() {
        return energyTrust;
    }

    public void setEnergyTrust(double energyTrust) {
        this.energyTrust = energyTrust;
    }

    public void calculateDataTrust(String variable, String datasetId) {
        String season = getCurrentSeason();
        // TODO: Query for the latest value for a variable and data set
        //TODO: Query parse for mean and variance for a season, variable and datasetId

        // Calculate Trust using that formula
        double power = (Math.pow(currentValue - mean, 2)) / (2 * Math.pow(variance, 2));
        double exponentValue = Math.exp(-power);
        double coefficient = 1 / (variance * Math.sqrt(2 * Math.PI));
        double fOfX = coefficient * exponentValue;

        // Integration

        //SimpsonIntegrator simpsonIntegrator = new SimpsonIntegrator(currentValue, mean);
        //simpsonIntegrator.integrate(1, fOfX, currentValue, mean);
    }

    private String getCurrentSeason() {
        Calendar calendar = Calendar.getInstance();
        int currentMonth = Calendar.MONTH + 1;
        String season = "";
        switch (currentMonth) {
            case 11:// Dec
            case 0:// JAn
            case 1:// Feb
                season = "Winter";
                break;
            case 2:// March
            case 3:// Apr
            case 4:// May
                season = "Spring";
                break;
            case 5:// June
            case 6:// July
            case 7:// August
                season = "Summer";
                break;
            case 8: // Sept
            case 9: // Oct
            case 10: // Nov
                season = "Autumn";
                break;
            default:
                System.out.print("Not one of the available months");
        }

        return season;
    }
}

