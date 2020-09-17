package com.example.app.controller;

import com.example.app.exception.UserNotFoundException;
import com.example.app.model.user;
import com.example.app.repo.userRepo;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/token")
public class apiControllerOAuthToken {

    //Initializing userRepo
    @Autowired
    userRepo userRepo;

    /*******************/
    /** Post TokenID ***/
    /*******************/

    @PostMapping
    public user tokenVerify(@Valid @RequestBody String token) throws Exception {
        JSONObject obj = new JSONObject(token);
        String parsedToken = obj.getString("token");
        return verifyToken(parsedToken);
    }

    /************************/
    /** END Post TokenID ****/
    /************************/

    //Standard items required by Google ID Token Verifier to verify the received token
    private static final HttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new JacksonFactory();
    private static final String CLIENT_ID = "730201351647-309cdsv7frjmncr4vnp4avbhn7omsjrk.apps.googleusercontent.com";

    //Method that verifies the OAuth token from google and checks the DB is the user exists
    private user verifyToken(String idTokenString) throws Exception {

        GoogleIdToken idToken = null;

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.
                Builder(transport, jsonFactory)
                .setIssuers(Arrays.asList("https://accounts.google.com", "accounts.google.com"))
                .setAudience(Collections.singletonList(CLIENT_ID))
                .build();

        //System.out.println("validating:" + idTokenString);

        try {
            idToken = verifier.verify(idTokenString);
        } catch (IllegalArgumentException e) {
            throw new Exception("idToken is invalid");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        String email = payload.getEmail();


        if (userRepo.findByEmail(email) == null) {
            throw new UserNotFoundException(email);
        }
        return userRepo.findByEmail(email);
    }
}
