package com.tony.randomcomplimentapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends AppCompatActivity {

    private Button mgetComplimentsButton;
    private TextView mShowComplimentText;

    private static final String url = "https://random-compliment.herokuapp.com/random";

    private static final String TAG = "RANDOM_COMPLIMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgetComplimentsButton = findViewById(R.id.get_compliment_btn);
        mShowComplimentText = findViewById(R.id.compliment_text);

        mgetComplimentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomCompliment();
            }
        });
    }

    private void getRandomCompliment() {

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest complimentRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String compliment = response.getString("text");
                            Log.d(TAG, compliment);
                            mShowComplimentText.setText(compliment);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error processing JSON resposne", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching data from compliment server", error);
                    }
                }
        );
        queue.add(complimentRequest);
    }
}
