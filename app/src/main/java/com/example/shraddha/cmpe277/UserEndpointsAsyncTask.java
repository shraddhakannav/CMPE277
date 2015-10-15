package com.example.shraddha.cmpe277;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.example.shraddha.cmpe277.backend.userApi.UserApi;
import com.example.shraddha.cmpe277.backend.userApi.model.User;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

/**
 * Created by Shraddha on 10/14/15.
 */
public class UserEndpointsAsyncTask extends AsyncTask<Pair<Context, User>, Void, User> {
    private static UserApi myApiService = null;
    private static String TAG = "UserEndpointsAsyncTask";
    private Context context;

    @Override
    protected User doInBackground(Pair<Context, User>... params) {
        if (myApiService == null) {

            UserApi.Builder builder = new UserApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null).setRootUrl("https://cmpe277-109506.appspot.com/_ah/api/");

            myApiService = builder.build();
        }

        context = params[0].first;
        User user = params[0].second;

        try {

//            User inserteduser = (User) myApiService.insertUser(user);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(User sensor) {
        String sensorvalue = "Not found";
        if (sensor != null)
            sensorvalue = sensor.getName();
        Toast.makeText(context, sensor.toString(), Toast.LENGTH_LONG).show();
    }
}
