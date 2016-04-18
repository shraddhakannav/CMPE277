package com.example.shraddha.cmpe277.Utils;

import android.graphics.Color;
import com.example.shraddha.cmpe277.ModelObjects.SensorData;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
  public static HashMap<String, Float> institutionToColorMapping = new HashMap<>();

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
  static DateFormat standardDateFormat =
      new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.US);
  static DateFormat prettyDateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
  private static HashMap<String, List<com.example.shraddha.cmpe277.ModelObjects.SensorData>>
      dataForVariable;

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

  public static DateFormat getPrettyDateFormat() {
    return prettyDateFormat;
  }

  public static void setPrettyDateFormat(DateFormat prettyDateFormat) {
    Constants.prettyDateFormat = prettyDateFormat;
  }

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

  //public float getHue(String institution) {
  //  BitmapDescriptorFactory.
  //  BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
  //}

  public static void prepareInsitutitionHashMap() {

    institutionToColorMapping.put("Axiom Data Science", BitmapDescriptorFactory.HUE_BLUE);
    institutionToColorMapping.put("California State University East Bay",
        BitmapDescriptorFactory.HUE_AZURE);
    institutionToColorMapping.put("Caltrans", BitmapDescriptorFactory.HUE_CYAN);
    institutionToColorMapping.put("Carbon Dioxide Information Analysis Center",
        BitmapDescriptorFactory.HUE_GREEN);
    institutionToColorMapping.put("USGS Pacific Coastal and Marine Science Center",
        BitmapDescriptorFactory.HUE_MAGENTA);
    institutionToColorMapping.put("USGS National Water Information System (NWIS)",
        BitmapDescriptorFactory.HUE_ORANGE);
    institutionToColorMapping.put("USGS Coastal and Marine Geology Program",
        BitmapDescriptorFactory.HUE_RED);
    institutionToColorMapping.put("University of California Davis, Bodega Marine Laboratory",
        BitmapDescriptorFactory.HUE_ROSE);
    institutionToColorMapping.put("Scripps Institution of Oceanography",
        BitmapDescriptorFactory.HUE_VIOLET);
    institutionToColorMapping.put("San Francisco State University Romberg Tiburon Center",
        BitmapDescriptorFactory.HUE_YELLOW);
    institutionToColorMapping.put("San Francisco State University CMA", getMarkerIcon("#FFAEB9"));
    institutionToColorMapping.put("Partnership for Interdisciplinary Studies of Coastal Oceans",
        getMarkerIcon("#5F9EA0"));
    institutionToColorMapping.put(
        "Oregon State University\n" + "College of Earth, Ocean and Atmospheric Sciences\n"
            + "104 CEOAS Admin Bldg\n" + "Corvallis, OR 97331-5503", getMarkerIcon("#7CFC00"));
    institutionToColorMapping.put(
        "Ocean Sciences Department, University of California,1156 High Street, Santa Cruz,CA 95064",
        getMarkerIcon("#EEB422"));
    institutionToColorMapping.put("NOAA Weather", getMarkerIcon("#F4A460"));
    institutionToColorMapping.put("NOAA Water", getMarkerIcon("#F4A460"));
    institutionToColorMapping.put("NOAA Earth System Research Laboratory",
        getMarkerIcon("#FFA07A"));
    institutionToColorMapping.put("Naval Postgraduate School (NPS)", getMarkerIcon("#FFA07A"));
    institutionToColorMapping.put("National Estuarine Research Reserve System (NERRS)",
        getMarkerIcon("#FF6A6A"));
    institutionToColorMapping.put("National Data Buoy Center (NDBC)", getMarkerIcon("#E066FF"));
    institutionToColorMapping.put("Moss Landing Marine Laboratories (MLML)",
        getMarkerIcon("#DC143C"));
    institutionToColorMapping.put("Monterey Bay Aquarium Research Institute",
        getMarkerIcon("#3D59AB"));
    institutionToColorMapping.put("MiscWebCams", getMarkerIcon("#20B2AA"));
    institutionToColorMapping.put("MesoWest", getMarkerIcon("#00C957"));
    institutionToColorMapping.put("Land/Ocean Biogeochemical Observatory (LOBO) in Elkhorn Slough",
        getMarkerIcon("#C0FF3E"));
    institutionToColorMapping.put("Hydrometeorological Automated Data System (HADS)",
        getMarkerIcon("#00CED1"));
    institutionToColorMapping.put("Humboldt State University", getMarkerIcon("#EEE685"));
    institutionToColorMapping.put("gov.ca.dfg", getMarkerIcon("#CDB38B"));
    institutionToColorMapping.put("edu.ucsd.cdip", getMarkerIcon("#CD3278"));
    institutionToColorMapping.put("edu.ucsc", getMarkerIcon("#EE7AE9"));
    institutionToColorMapping.put("Central & Northern California Ocean Observing System (CeNCOOS)",
        getMarkerIcon("#9370DB"));
    institutionToColorMapping.put(
        "Center for Operational Oceanographic Products and Services (CO-OPS)",
        getMarkerIcon("#FF7F50"));
  }

  public static float getMarkerIcon(String color) {
    float[] hsv = new float[3];
    Color.colorToHSV(Color.parseColor(color), hsv);
    return hsv[0];
  }

  public static float getIconColorForInstitution(String institution) {

    Float color = institutionToColorMapping.get(institution);
    if (color != null) {
      return color;
    } else {
      return BitmapDescriptorFactory.HUE_BLUE;
    }
  }
}
