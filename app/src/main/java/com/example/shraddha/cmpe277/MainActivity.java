package com.example.shraddha.cmpe277;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.Parse;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Main Activity";

    private String APPLICATION_ID = "<<YOUR_APPLICATION_ID>>";
    private String CLIENT_KEY = "<<YOUR_CLIENT_KEY>>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GlobalValues.init(getApplicationContext());

        try {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "Starting intializing parse is problem");
        }

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
