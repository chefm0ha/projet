package com.projet;

import android.app.Application;
import android.util.Log;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class ProjetApplication extends Application {
    private static final String TAG = "ProjetApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        initializeFirebase();
    }

    private void initializeFirebase() {
        try {
            // Vérifier si Firebase est déjà initialisé
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this);
                Log.d(TAG, "Firebase initialized successfully");
            } else {
                Log.d(TAG, "Firebase was already initialized");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error initializing Firebase: " + e.getMessage(), e);
        }
    }
}