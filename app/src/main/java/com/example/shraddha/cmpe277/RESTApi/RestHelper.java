package com.example.shraddha.cmpe277.RESTApi;

import android.util.Log;
import bolts.Task;
import com.example.shraddha.cmpe277.Gson.Institution;
import com.example.shraddha.cmpe277.ModelObjects.DataResult;
import com.google.api.client.json.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.internal.LinkedTreeMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestHelper {
  private static RestHelper restHelper;
  private static ErddapService erddapClient = MSCRetrofitClass.getErrdapClient();
  private static MSCService mscClient = MSCRetrofitClass.getMSCClient();
  public ArrayList allInstitutions = new ArrayList();
  public DataResult fetchedresult;

  public static void initInstance() {

    if (restHelper == null) {
      restHelper = new RestHelper();
    }
  }

  public static RestHelper getInstance() {
    return restHelper;
  }

  public void getOneWeekDataForVariables(String variables, String datasetID, String currentDate,
      String lastweekday) {
    HashMap<String, String> parameters = new HashMap<String, String>();

    parameters.put("time>", lastweekday);
    parameters.put("time<", currentDate);

    final Call<Object> call = erddapClient.getValuesForVariables(datasetID, variables, parameters);
    call.enqueue(new Callback<Object>() {
      @Override public void onResponse(Call call, Response response) {
        if (response.isSuccessful()) {
          try {

            LinkedTreeMap test = ((LinkedTreeMap) response.body());
            DataResult result = new DataResult();
            result.setRows((String[][]) ((LinkedTreeMap) test.get("table")).get("rows"));
            result.setColumnNames(
                (String[]) ((LinkedTreeMap) test.get("table")).get("columnNames"));
            result.setColumnDataTypes(
                (String[]) ((LinkedTreeMap) test.get("table")).get("columnTypes"));
            result.setColumnDataTypes(
                (String[]) ((LinkedTreeMap) test.get("table")).get("columnUnits"));
            fetchedresult = result;

            System.out.print("Printing all values ______________________________");

            for (int i = 0; i < result.getRows().length; i++) {
              for (int j = 0; j < result.getRows()[0].length; j++) {
                System.out.print("Value fetched is " + result.getColumnNames()[j] + " with value"
                    + result.getRows()[i][j] + " with unit" + result.getUnits()[j]
                    + " with data type" + result.getColumnDataTypes()[j]);
              }
            }

            Log.d("Response  OK", String.valueOf(response.code()));
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          // error response, no access to resource?
          Log.d("Response not OK", String.valueOf(response.code()));
        }
      }

      @Override public void onFailure(Call<Object> call, Throwable t) {
        // something went completely south (like no internet connection)
        Log.d("Error", t.getMessage());
      }
    });
  }

  public void getInstitutionFromErrdap() {
    ArrayList<String> listOfInstitutions = new ArrayList<>();

    final Call<Object> call = erddapClient.getSpecifiedParam();
    call.enqueue(new Callback<Object>() {
      @Override public void onResponse(Call call, Response response) {
        if (response.isSuccessful()) {
          try {

            LinkedTreeMap test = ((LinkedTreeMap) response.body());
            Institution institution = new Institution();

            ArrayList institutions = (ArrayList) ((LinkedTreeMap) test.get("table")).get("rows");

            for (Object eachInstitution : institutions) {
              eachInstitution = eachInstitution.toString().replace("[", "");
              eachInstitution = eachInstitution.toString().replace("]", "");
              allInstitutions.add(eachInstitution);
            }

            Log.d("Response  OK", String.valueOf(response.code()));
          } catch (Exception e) {
            e.printStackTrace();
          }
        } else {
          // error response, no access to resource?
          Log.d("Response not OK", String.valueOf(response.code()));
        }
      }

      @Override public void onFailure(Call<Object> call, Throwable t) {
        // something went completely south (like no internet connection)
        Log.d("Error", t.getMessage());
      }
    });
  }
}


