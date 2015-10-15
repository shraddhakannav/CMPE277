package com.example.shraddha.cmpe277;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.shraddha.cmpe277.backend.userApi.UserApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Shraddha on 10/11/15.
 */
public class ListEndpointsAsyncTask extends AsyncTask<Context, Void, List<SensorData>> {
    private static String TAG = "EndpointsAsyncTask";
    private UserApi myApiService = null;
    private Context context;

    @Override
    protected List<SensorData> doInBackground(Context... params) {
        if (myApiService == null) {

            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null).setRootUrl("https://cmpe277-109506.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0];

        try {
            Long id = 123L;
            UserApi.List service = myApiService.list();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<SensorData> list) {
        if (list != null)
            for (SensorData t : list) {
                Toast.makeText(context, t.getValue() + "", Toast.LENGTH_LONG).show();
            }
        else
            Toast.makeText(context, "No Users", Toast.LENGTH_LONG).show();

    }
}
