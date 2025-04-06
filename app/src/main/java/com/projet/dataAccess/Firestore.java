package com.projet.dataAccess;

import com.google.firebase.firestore.FirebaseFirestore;
import android.util.Log;

import com.projet.dto.UserDTO;
import com.projet.models.User;

public class Firestore {
    private static final String OFFERS_COLLECTION = "Offer";
    private static Firestore instance;
    private final FirebaseFirestore db;
    private static final String USERS_COLLECTION = "users";
    private static final String CLIENTS_COLLECTION = "clients";

    private Firestore() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized Firestore getInstance() {
        if (instance == null) {
            instance = new Firestore();
        }
        return instance;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public void addUser(String uid, User user) {
        UserDTO userDTO = UserDTO.fromUser(user);
        db.collection(USERS_COLLECTION)
                .document(uid)
                .set(userDTO)
                .addOnSuccessListener(unused -> Log.d("Firestore", "User data stored successfully for UID: " + uid))
                .addOnFailureListener(e -> Log.e("Firestore", "Failed to store user data for UID: " + uid, e));
    }


    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(String error);
    }
}