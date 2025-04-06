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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin, passwordLogin;
    private Button loginButton;
    private TextView goToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialisation des vues
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginButton = findViewById(R.id.loginButton);
        goToSignup = findViewById(R.id.goToSignup);

        // Gestionnaire de clic pour le bouton de connexion
        loginButton.setOnClickListener(v -> {
            String email = emailLogin.getText().toString().trim();
            String password = passwordLogin.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Firebase.getInstance().signInWithEmailAndPassword(email, password, new Firebase.LoginCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    Toast.makeText(LoginActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(LoginActivity.this, "Erreur: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Redirection vers l'inscription
        goToSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });
    }
} 