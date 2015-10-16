package com.example.shraddha.cmpe277;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;


public class MainActivity extends AppCompatActivity {


    private static String TAG = "Main Activity";

    private String APPLICATION_ID = "SauyeeY2Qa9qhwldPqG9MOgTokl6uepNliHK4jZE";
    private String CLIENT_KEY = "3z5vqgwhTRsoo69LkFlLqww9p2nk6ZIa6uIbHc4k";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDeviceId();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);


        Button button_test = (Button) findViewById(R.id.userbtn);

        button_test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UserDisplayList.class);
                startActivity(intent); //<<< start Activity here

            }
        });

    }


    public void registerMe(View view) {

        showCustomDialog();
    }

    protected void showCustomDialog() {
        // TODO Auto-generated method stub
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_register_me_dialog);

        final EditText editText = (EditText) dialog.findViewById(R.id.phone);
        Button button = (Button) dialog.findViewById(R.id.registerphonebtn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                editText.getText();

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public String getDeviceId() {

        String android_id = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        GlobalValues.setDeviceId(android_id);


        return android_id;
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
