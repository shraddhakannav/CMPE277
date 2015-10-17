package com.example.shraddha.cmpe277;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Shraddha on 10/14/15.
 */
public class ParseApplication extends Application {

    private String APPLICATION_ID = "SauyeeY2Qa9qhwldPqG9MOgTokl6uepNliHK4jZE";
    private String CLIENT_KEY = "3z5vqgwhTRsoo69LkFlLqww9p2nk6ZIa6uIbHc4k";

    @Override
    public void onCreate() {
        super.onCreate();

        // Add your initialization code here
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();

        // If you would like all objects to be private by default, remove this line.
        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
    }
}
