package com.projet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.projet.R;
import com.projet.dataAccess.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseUser;
import com.projet.models.User;

public class SignupActivity extends AppCompatActivity {
    private EditText nameSignup, emailSignup, passwordSignup;
    private Button signupButton;
    private TextView goToLogin;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebase = Firebase.getInstance();

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        nameSignup = findViewById(R.id.nameSignup);
        emailSignup = findViewById(R.id.emailSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        signupButton = findViewById(R.id.signupButton);
        goToLogin = findViewById(R.id.goToLogin);
    }

    private void setupListeners() {
        signupButton.setOnClickListener(v -> {
            String name = nameSignup.getText().toString().trim();
            String email = emailSignup.getText().toString().trim();
            String password = passwordSignup.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(name, email, password);
            firebase.createUserWithEmailAndPassword(newUser);
            Toast.makeText(SignupActivity.this, "Account created successfully!", Toast.LENGTH_LONG).show();
            // Finish current activity and go back to login
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // Redirection vers la connexion
        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }
}