package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;  //Volley это библиотека, которая делает сетевые приложения для Android проще и, самое главное, быстрее.
                                           //Она управляет обработкой и кэшированием сетевых запросов, и это экономит драгоценное время разработчиков
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue; //Request queue: используется для отправки сетевых запросов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        final Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);

        buttonParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonParse.setVisibility(View.GONE);
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        String url = "https://api.jikan.moe/v3/search/anime?q=blood&limit=16";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);

                                String Title = result.getString("title");
                                int episodes = result.getInt("episodes");
                                String type = result.getString("type");

                                mTextViewResult.append("Name: "+Title + ",   Episodes: " + String.valueOf(episodes) + ",   Type: " + type + "\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace(); //Это очень простой, но очень полезный инструмент
                                                 // для диагностики исключения. Он рассказывает вам, что произошло, и где в коде это произошло
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}
