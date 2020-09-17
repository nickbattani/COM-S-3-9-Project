package com.example.anti_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anti_social.net_utils.APIFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to create a new user to add to the database.
 * accessed when user clicks create new user, or tries to log in to an account that does not yet exist
 */
public class CreateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        if(getIntent().hasExtra("user.email")){
            final EditText firstET = (EditText) findViewById(R.id.firstNameET);
            final EditText lastET = (EditText) findViewById(R.id.lastNameET);
            final EditText userET = (EditText) findViewById(R.id.userNameET);
            Button createBtn = (Button) findViewById(R.id.createUserBTN);
            String email = getIntent().getStringExtra("user.email");

            setOnClick(createBtn, firstET, lastET, userET, email);
        }
    }

    /**
     * Sets up the on click listener for the create user button
     * @param createBtn the button to create the listener for
     * @param firstET the edit text containing the new users first name
     * @param lastET the edit text containing the new users last name
     * @param userET the edit text containing the new users user name
     * @param email a string containing the new users email address
     */
    private void setOnClick(final Button createBtn, final EditText firstET, final EditText lastET, final EditText userET, final String email){
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("firstName", firstET.getText().toString());
                params.put("lastName", lastET.getText().toString());
                params.put("userName", userET.getText().toString());
                params.put("email",email);

                sendRequest(params);
            }
        });
    }

    /**
     * Sends the post request to the server to add the new user to the database
     * @param params hashmap containing the information for the new user containing, first name, last name, user name, and email
     */
    private void sendRequest(Map<String, String> params){
        RequestQueue queue = Volley.newRequestQueue(CreateUserActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIFunctions.CREATEUSER, new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("new.user.id", response.getString("id"));
                            Log.d("USER_CREATION","New user created with id "+response.getString("id"));
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        queue.add(request);
    }
}