package com.projet.dataAccess;

import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.projet.models.User;

public class Firebase {
    private static Firebase instance;
    private final String TAG = "EmailPassword";
    private final FirebaseAuth mAuth;

    private Firebase() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static synchronized Firebase getInstance() {
        if (instance == null) {
            instance = new Firebase();
        }
        return instance;
    }

    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    public void signInWithEmailAndPassword(String email, String password, LoginCallback callback) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        callback.onSuccess(user);
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        callback.onFailure(task.getException() != null ?
                                task.getException().getMessage() :
                                "Échec de l'authentification");
                    }
                });
    }

    public void createUserWithEmailAndPassword(User user, LoginCallback callback) {
        mAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = task.getResult().getUser();
                        if (firebaseUser != null) {
                            Log.d(TAG, "User created with UID: " + firebaseUser.getUid());
                            String uid = firebaseUser.getUid();
                            user.setId(uid);
                            
                            // Créer l'entrée dans Firestore
                            Firestore firestore = Firestore.getInstance();
                            firestore.addUser(user);
                            callback.onSuccess(firebaseUser);
                        }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        callback.onFailure(task.getException() != null ?
                                task.getException().getMessage() :
                                "Échec de la création du compte");
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public interface LoginCallback {
        void onSuccess(FirebaseUser user);
        void onFailure(String error);
    }
} 