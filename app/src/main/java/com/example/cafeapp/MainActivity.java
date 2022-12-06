package com.example.cafeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText distance;
    private TextView location;
    private Button btnLocate;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        distance = findViewById(R.id.inputDistance);
        location = findViewById(R.id.location);
        btnLocate = findViewById(R.id.btn_locate);
        btnLogout = findViewById(R.id.btn_logout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        btnLocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPrefName = getSharedPreferences("name", MODE_PRIVATE);
                SharedPreferences.Editor editorName = sharedPrefName.edit();

                SharedPreferences sharedPrefCords = getSharedPreferences("cords", MODE_PRIVATE);
                SharedPreferences.Editor editorCords = sharedPrefCords.edit();
                try {
                    JSONObject jaJsonObject = new JSONObject(JsonDataFromAsset());
                    JSONArray jsonArray = jaJsonObject.getJSONArray("features");
                    Log.i("---View JSONArray before", jsonArray.toString());

                    int random = new Random().nextInt( + jsonArray.length()-300);
                    Log.i("---RANDOM VALUE IS THIS", String.valueOf(random));
                    for(int i=0; i<jsonArray.length();i++){
                        Log.i("---RANDOM VALUE IS THIS", String.valueOf(random));
                        if(i == random){
                            JSONObject cafeData=jsonArray.getJSONObject(i);
                            Log.i("---View JSONArray object", cafeData.toString());

                            JSONObject cafeDataInside=cafeData.getJSONObject("properties");
                            Log.i("---INSIDE FOR NAME -- ", cafeDataInside.toString());

                            JSONObject cafeDataInsideForCords=cafeData.getJSONObject("geometry");
                            Log.i("---INSIDE FOR CORDS -- ", cafeDataInsideForCords.toString());

                            String cafeDataInsideInside=cafeDataInside.getString("name");
                            Log.i("---NAME -- ", cafeDataInsideInside.toString());
                            if(cafeDataInsideInside == null) {
                                editorName.putString("name", "cafe");
                            }
                            else{
                                editorName.putString("name", cafeDataInsideInside.toString());
                            }
                            String cafeDataInsideCords=cafeDataInsideForCords.getString("coordinates");
                            Log.i("---CORDS -- ", cafeDataInsideCords.toString());
                            //intent.putExtra("key", "testing");
                            editorCords.putString("cords", cafeDataInsideCords.toString());

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                editorName.apply();
                editorCords.apply();
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                //startActivity(intent);
            }

        });

    }//end on create

    private String JsonDataFromAsset() {
        String json = null;
        try {
            Log.i("Trying", "trying...");
            InputStream inputStream= getResources().openRawResource(R.raw.cafe_point);
            int sizeOfFile = inputStream.available();
            byte[] bufferdData = new byte[sizeOfFile];
            inputStream.read(bufferdData);
            inputStream.close();
            json = new String(bufferdData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("NOPE", "not accessed");
            return null;
        }
        return json;
    }


    public void logout() {
        try {
            FirebaseAuth.getInstance().signOut();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

}
