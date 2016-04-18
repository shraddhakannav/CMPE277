package com.example.shraddha.cmpe277.Utils;

import com.example.shraddha.cmpe277.ModelObjects.SensorData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Constants {
  public static String ALL_SENSORS = "All Sensors";
  public static String ALL_SOURCES = "All Sources";

  // Miscellaneous
  //* depth_status_flag
  //* latitude_status_flag
  //* longitude_status_flag
  //* salinity_status_flag
  //* time
  // time_status_flag

    public static HashMap<String, List<String>> categoriesToVariables =
            new HashMap<String, List<String>>();
    static DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    static DateFormat standardDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
    static DateFormat prettyDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
    private static HashMap<String, List<com.example.shraddha.cmpe277.ModelObjects.SensorData>> dataForVariable;

  public static void setCategoriesToVariables() {
    List<String> AtmosphereVariableList = new ArrayList<String>();
    AtmosphereVariableList.add("air_pressure");
    AtmosphereVariableList.add("air_temperature");
    AtmosphereVariableList.add("atmosphere_net_rate_of_absorption_of_energy");
    AtmosphereVariableList.add("mass_concentration_of_oxygen_in_sea_water");
    AtmosphereVariableList.add("mole_fraction_of_water_vapor_in_air");
    AtmosphereVariableList.add("visibility_in_air");
    categoriesToVariables.put("Atmosphere", AtmosphereVariableList);

    // Sea water
    List<String> SWvariableList = new ArrayList<String>();
    //SWvariableList.add("sea_water_density");
    //SWvariableList.add("sea_water_density_status_flag");
    //SWvariableList.add("sea_water_electrical_conductivity");
    //SWvariableList.add("sea_water_electrical_conductivity_status_flag");
    SWvariableList.add("sea_water_ph_reported_on_total_scale");
    SWvariableList.add("sea_water_practical_salinity");
    SWvariableList.add("sea_water_pressure");
    //  SWvariableList.add("sea_water_pressure_status_flag");
    ///SWvariableList.add("sea_water_salinity");
    // SWvariableList.add("sea_water_salinity_status_flag");
    //SWvariableList.add("sea_water_sigma_theta");
    SWvariableList.add("sea_water_speed");
    SWvariableList.add("sea_water_temperature");
    //SWvariableList.add("sea_water_temperature_status_flag");
    SWvariableList.add("direction_of_sea_water_velocity");
    //SWvariableList.add("eastward_sea_water_velocity");
    //SWvariableList.add("eastward_sea_water_velocity_status_flag");
    //SWvariableList.add("northward_sea_water_velocity");
    //SWvariableList.add("upward_sea_water_velocity");
    //SWvariableList.add("northward_sea_water_velocity_status_flag");
    SWvariableList.add("fractional_saturation_of_oxygen_in_sea_water");
    //SWvariableList.add("volume_beam_attenuation_coefficient_of_radiative_flux_in_sea_water");
    SWvariableList.add("percent_oxygen_ratio_between_air_and_sea_water");
    SWvariableList.add("mass_concentration_of_chlorophyll_in_sea_water");
    categoriesToVariables.put("Sea Water", SWvariableList);

    // Sea surface
    List<String> SSvariableList = new ArrayList<String>();
    //SSvariableList.add("sea_water_density");
    SSvariableList.add("sea_surface_dominant_wave_period");
    SSvariableList.add("sea_surface_height");
    SSvariableList.add("sea_surface_height_above_sea_level");
    SSvariableList.add("sea_surface_height_amplitude_due_to_geocentric_ocean_tide");
    SSvariableList.add("sea_surface_swell_wave_period");
    SSvariableList.add("sea_surface_swell_wave_significant_height");
    SSvariableList.add("sea_surface_swell_wave_to_direction");
    SSvariableList.add("sea_surface_wave_from_direction");
    SSvariableList.add("sea_surface_wave_mean_period");
    SSvariableList.add("sea_surface_wave_significant_height");
    SSvariableList.add("sea_surface_wave_to_direction");
    SSvariableList.add("sea_surface_wind_wave_period");
    SSvariableList.add("sea_surface_wind_wave_significant_height");
    SSvariableList.add("sea_surface_wind_wave_to_direction");
    SSvariableList.add("water_surface_height_above_reference_datum");
    SSvariableList.add("surface_downwelling_photosynthetic_photon_flux_in_air");
    SSvariableList.add("surface_downwelling_shortwave_flux_in_air");
    SSvariableList.add("sea_floor_depth_below_sea_surface");
    categoriesToVariables.put("Sea Surface", SSvariableList);

    // Wind
    List<String> WindvariableList = new ArrayList<String>();
    WindvariableList.add("wind_chill_temperature");
    WindvariableList.add("wind_from_direction");
    WindvariableList.add("wind_gust_from_direction");
    WindvariableList.add("wind_speed");
    WindvariableList.add("wind_speed_of_gust");
    //WindvariableList.add("eastward_wind");
    //WindvariableList.add("northward_wind");

    categoriesToVariables.put("Wind", WindvariableList);
    // Temperature
    List<String> TempvariableList = new ArrayList<String>();
    TempvariableList.add("dew_point_temperature");
    TempvariableList.add("fuel_temperature");
    TempvariableList.add("soil_temperature");
    //TempvariableList.add("temperuature_status_flag");

    categoriesToVariables.put("Temperature", TempvariableList);

    // Carbon dioxide Ratio
    List<String> CO2variableList = new ArrayList<String>();
    CO2variableList.add("surface_partial_pressure_of_carbon_dioxide_in_air");
    CO2variableList.add("surface_partial_pressure_of_carbon_dioxide_in_sea_water");
    CO2variableList.add("dissolved_carbon_dioxide_co2");
    CO2variableList.add("fugacity_of_carbon_dioxide_in_sea_water");
    CO2variableList.add(
        "surface_carbon_dioxide_partial_pressure_difference_between_air_and_sea_water");
    CO2variableList.add("mole_fraction_of_carbon_dioxide_in_air_in_dry_gas");
    CO2variableList.add("mole_fraction_of_carbon_dioxide_in_air_in_wet_gas");
    CO2variableList.add("mole_fraction_of_carbon_dioxide_in_sea_water_in_dry_gas");
    CO2variableList.add("mole_fraction_of_carbon_dioxide_in_sea_water_in_wet_gas");
    categoriesToVariables.put("Carbon dioxide Ratio", CO2variableList);

    List<String> MiscvariableList = new ArrayList<String>();
    MiscvariableList.add("battery_voltage");
    //MiscvariableList.add("depth");
    MiscvariableList.add("depth_to_water_level");
    MiscvariableList.add("fluorescence");
    //MiscvariableList.add("latitude");
    //MiscvariableList.add("longitude");
    MiscvariableList.add("lwe_thickness_of_precipitation_amount");
    MiscvariableList.add("height");
    //MiscvariableList.add("pco2");
    MiscvariableList.add("photosynthetically_active_radiation");
    MiscvariableList.add("precipitation_increment");
    //MiscvariableList.add("pressure_status_flag");
    MiscvariableList.add("relative_humidity");
    MiscvariableList.add("river_discharge");
    MiscvariableList.add("solar_radiation");
    //MiscvariableList.add("speed_of_sound_in_sea_water");
    MiscvariableList.add("toa_incoming_shortwave_flux");
    MiscvariableList.add("total_alkalinity");
    MiscvariableList.add("turbidity");
    // MiscvariableList.add("webcam");
    categoriesToVariables.put("Miscellaneous", MiscvariableList);
  }

  public static HashMap<String, List<SensorData>> getDataForVariable() {
    return dataForVariable;
  }

  public static void setDataForVariable(HashMap<String, List<SensorData>> dataForVariableTemp) {
    dataForVariable = dataForVariableTemp;
  }
  
  public static DateFormat getSimpleDateFormat() {
    return simpleDateFormat;
  }

  public static void setSimpleDateFormat(DateFormat simpleDateFormat) {
    Constants.simpleDateFormat = simpleDateFormat;
  }

  public static DateFormat getStandardDateFormat() {
    return standardDateFormat;
  }

  public static void setStandardDateFormat(DateFormat standardDateFormat) {
    Constants.standardDateFormat = standardDateFormat;
  }
  
  public static void setPrettyDateFormat(DateFormat prettyDateFormat) {
    Constants.prettyDateFormat = prettyDateFormat;
  }

  public static DateFormat getPrettyDateFormat() {
    return prettyDateFormat;
  }
  
  public static String formatDate(String rawDate) {
    Date date = new Date();
    //2016-04-04T23:36:00Z

    //String year = rawDate.substring(0,3);
    //String month = rawDate.substring(5,6);
    //String day = rawDate.substring(8,9);
    SimpleDateFormat sdfformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
    try {
      date = sdfformat.parse(rawDate);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return date.toString();
  }
}
