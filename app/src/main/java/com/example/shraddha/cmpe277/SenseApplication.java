package com.example.shraddha.cmpe277;

import android.app.Application;
import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.RESTApi.RestHelper;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

/**
 * Created by Shraddha on 10/14/15.
 */
public class SenseApplication extends Application {

  //private String APPLICATION_ID = "SauyeeY2Qa9qhwldPqG9MOgTokl6uepNliHK4jZE";
  //private String CLIENT_KEY = "3z5vqgwhTRsoo69LkFlLqww9p2nk6ZIa6uIbHc4k";

  private static String APPLICATION_ID = "MijqctrNfaJUf85RhA3yBgeRPPNJ4CLIPHE5mrLm";
  private static String CLIENT_KEY = "Sn1ExIwvI1Gvoj8HzplnikS9eoJe3HKj0vVPnupx";

  @Override public void onCreate() {
    super.onCreate();

    Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
    ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();
    defaultACL.setPublicReadAccess(true);
    ParseACL.setDefaultACL(defaultACL, true);

    ParseDataAccessor.initInstance();
    RestHelper.initInstance();
  }

  public static ParseDataAccessor getParseDAInstance() {
    return ParseDataAccessor.getInstance();
  }

  public static RestHelper getRestHelperInstance(){
    return RestHelper.getInstance();
  }


}
