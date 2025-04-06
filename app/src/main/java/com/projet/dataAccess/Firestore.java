package com.projet.dataAccess;

import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.HashMap;

import com.projet.models.User;
import com.projet.dto.UserDTO;

public class Firestore {
    private static final String USERS_COLLECTION = "users";
    private static Firestore instance;
    private final FirebaseFirestore db;

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

    public void addUser(User user) {
        UserDTO userDTO = UserDTO.fromUser(user);
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", userDTO.getName());
        userData.put("email", userDTO.getEmail());

        db.collection(USERS_COLLECTION)
                .document(userDTO.getId())
                .set(userData)
                .addOnSuccessListener(unused -> 
                    Log.d("Firestore", "User data stored successfully for UID: " + userDTO.getId()))
                .addOnFailureListener(e -> 
                    Log.e("Firestore", "Failed to store user data for UID: " + userDTO.getId(), e));
    }

    public void getUser(String uid, FirestoreCallback<User> callback) {
        db.collection(USERS_COLLECTION)
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserDTO userDTO = documentSnapshot.toObject(UserDTO.class);
                        if (userDTO != null) {
                            userDTO.setId(uid);
                            callback.onSuccess(userDTO.toUser());
                        } else {
                            callback.onFailure("Erreur de conversion des données");
                        }
                    } else {
                        callback.onFailure("Utilisateur non trouvé");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }

    public Task<Void> addDocument(String collection, Map<String, Object> data) {
        return db.collection(collection).document().set(data);
    }

    public Task<QuerySnapshot> getDocuments(String collection) {
        return db.collection(collection).get();
    }

    public Task<Void> updateDocument(String collection, String documentId, Map<String, Object> data) {
        return db.collection(collection).document(documentId).update(data);
    }

    public Task<Void> deleteDocument(String collection, String documentId) {
        return db.collection(collection).document(documentId).delete();
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);
        void onFailure(String error);
    }
} 