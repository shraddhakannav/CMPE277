package com.example.shraddha.cmpe277.RESTApi;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ErddapService {

  /*
  Just to get institutions :
  http://erddap.cencoos.org/erddap/tabledap/allDatasets.htmlTable?institution
*/

    @GET("tabledap/allDatasets.json?institution")
    Call<Object> getSpecifiedParam();

    //http://erddap.cencoos.org/erddap/tabledap/edu_ucsc_scwharf1.htmlTable?time,latitude,longitude,station,wind_speed,air_pressure,wind_from_direction,sea_water_temperature,sea_water_electrical_conductivity,wind_speed_of_gust,air_temperature,sea_water_practical_salinity,depth&time>=2013-04-18T00:00:00Z&time<=2013-04-25T14:04:00Z

    @GET("tabledap/{datasetID}.json")
    Call<Object> getValuesForVariables(
            @Path("datasetID") String datasetID,
            @QueryMap Map<String, String> parameters);
}
