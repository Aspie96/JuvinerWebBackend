/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerwebbackend.userservice.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleTokenVerifierService {
    private String clientId = "464272091439-olt4tmsdh2v6ta73goe6ri6adf4n7d6s.apps.googleusercontent.com";

    private final HttpTransport httpTransport = new NetHttpTransport();
    private final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    public GoogleIdToken verify(String token) {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
            .Builder(httpTransport, jsonFactory)
            .setAudience(Collections.singletonList(clientId))
            .build();

        try {
            return verifier.verify(token);
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalArgumentException();
        }
    }
}
