package com.example.anti_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anti_social.Home.HomeActivity;
import com.example.anti_social.net_utils.APIFunctions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Initial page for user login button
 */
public class MainActivity extends AppCompatActivity {
    private static final int SUCCESS_ON_USER_CREATION = 2;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //TODO check if user exists
                /*
                final EditText nameET = (EditText) findViewById(R.id.nameEditText);
                */

                switch (v.getId()) {
                    case R.id.loginBtn:
                        signIn();
                        break;
                }
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("730201351647-309cdsv7frjmncr4vnp4avbhn7omsjrk.apps.googleusercontent.com")
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    protected void onStart(){
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null) {
            // Can use this to auto sign in, but not sure how to make it not auto sign up
            //signIn();
        }
    }


    private void signIn() {
        Log.d("Google_Oauth", "Fetching start up intent");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.d("Google_Oauth","starting sign in intent");
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Log.d("INTENT_RESULT","Received signal from oauth signIn");
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else {
            Log.d("INTENT_RESULT","Received Intent with Result code "+requestCode);
            switch (requestCode) {
                case (SUCCESS_ON_USER_CREATION): {
                    if (resultCode == Activity.RESULT_OK) {
                        Log.d("INTENT_RESULT", "User has been created");
                        final String userID = data.getStringExtra("new.user.id");
                        Intent homePageIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        homePageIntent.putExtra("Mainactivity.id", userID);
                        startActivity(homePageIntent);
                    }
                    break;
                }
            }
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // ID Token
            String idToken = account.getIdToken();
            Log.d("USER ID", idToken);
            Map<String, String> params = new HashMap<String, String>();
            params.put("token", idToken);

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIFunctions.VERIFY_TOKEN,new JSONObject(params),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String id = response.getString("id");
                                Intent homePageIntent = new Intent(getApplicationContext(), HomeActivity.class);
                                homePageIntent.putExtra("Mainactivity.id", id);
                                startActivity(homePageIntent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("TOKEN_VALIDATION","Users with email "+account.getEmail()+" not found.");
                    Intent createNewUserIntent = new Intent(getApplicationContext(), CreateUserActivity.class);
                    createNewUserIntent.putExtra("user.email",account.getEmail());
                    createNewUserIntent.putExtra("user.name",account.getDisplayName());
                    startActivityForResult(createNewUserIntent, SUCCESS_ON_USER_CREATION);
                }
            });
            queue.add(request);
        } catch (ApiException e) {

            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("error", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
