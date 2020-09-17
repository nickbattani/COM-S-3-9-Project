package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import org.jetbrains.annotations.NotNull;

/**
 * DEMO ACTIVITY HOW TO DISPLAY INFO FORM THE LOGGED USER
 */

public class Main2Activity extends AppCompatActivity {

    ImageView imageView;
    TextView name, email, id;
    Button signOut;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.nameview);
        email = findViewById(R.id.emailview);
        id = findViewById(R.id.idview);
        signOut = findViewById(R.id.signoutbutton);

        signOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                switch(view.getId()){
                    case R.id.signoutbutton:
                        signOut();
                        break;
                }
            }
        });

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct != null){
            String pname = acct.getDisplayName();
            String pemail = acct.getEmail();
            String pid = acct.getId();

            name.setText(pname);

            email.setText(pemail);

            id.setText(pid);

        }
    }

    private void signOut(){
        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>(){
           @Override
           public void onComplete(@NotNull Task<Void> task){
               Toast.makeText(Main2Activity.this, "Signed out!", Toast.LENGTH_SHORT).show();
               finish();
            }
        });
    }
}
