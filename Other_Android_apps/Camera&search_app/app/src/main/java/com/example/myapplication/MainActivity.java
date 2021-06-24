package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity   {
    private final String CHANNEL_ID = "personal_notifications";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button search = findViewById(R.id.search);
        final EditText editSearch = findViewById(R.id.editSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.google.ru/search?q="+editSearch.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW );
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        final Button buttonOK = findViewById(R.id.buttonOK);
        final RadioButton radioFront = findViewById(R.id.radioFront);
        final RadioButton radioBack = findViewById(R.id.radioBack);
        final RadioGroup radioGroupe = findViewById(R.id.radioGroup2);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedID = radioGroupe.getCheckedRadioButtonId();
                RadioButton chosenCamera = findViewById(selectedID);

                if(chosenCamera == radioFront) {
                    Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    i.putExtra("android.intent.extras.CAMERA_FACING", 1);
                    startActivityForResult(i, 1337);
                }

                else{
                        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i, 1337);
                    }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap thumbnail = null;
        if (requestCode == 1337) {
            if (resultCode == RESULT_OK) {
                thumbnail = (Bitmap) data.getExtras().get("data");
                Intent i = new Intent(this, Main2Activity.class);
                i.putExtra("name", thumbnail);
                startActivity(i);
            }
        }
    }

    public void Notification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_monetization_on_black);
        builder.setContentTitle("My notification");
        builder.setContentText("Your account has been credited");
        Notification notifcation = builder.build();
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, notifcation);
    }

    public void displayNotification(View view) {
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //The code you want to run after the time is up
                Notification();
            }
        }, 10000);
    }

}
