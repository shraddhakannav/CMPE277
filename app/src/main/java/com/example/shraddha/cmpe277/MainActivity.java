package com.example.shraddha.cmpe277;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.shraddha.cmpe277.DataAccessors.ParseDataAccessor;
import com.example.shraddha.cmpe277.RESTApi.RestHelper;

public class MainActivity extends AppCompatActivity {

  private static String TAG = "Main Activity";
  ParseDataAccessor dataAccessor;
  RestHelper restHelper;
  //// Mine
  //    private String APPLICATION_ID = "MijqctrNfaJUf85RhA3yBgeRPPNJ4CLIPHE5mrLm";
  //    private String CLIENT_KEY = "Sn1ExIwvI1Gvoj8HzplnikS9eoJe3HKj0vVPnupx";

  //Old
  //private String APPLICATION_ID = "SauyeeY2Qa9qhwldPqG9MOgTokl6uepNliHK4jZE";
  //private String CLIENT_KEY = "3z5vqgwhTRsoo69LkFlLqww9p2nk6ZIa6uIbHc4k";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    GlobalValues.init(getApplicationContext());
    dataAccessor = SenseApplication.getParseDAInstance();
    dataAccessor.getSensorCategoriesFromParse();
    restHelper = SenseApplication.getRestHelperInstance();
    restHelper.getInstitutionFromErrdap();
    Button button_test = (Button) findViewById(R.id.userbtn);

    button_test.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, UserDisplayList.class);
        startActivity(intent); //<<< start Activity here
      }
    });


  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
