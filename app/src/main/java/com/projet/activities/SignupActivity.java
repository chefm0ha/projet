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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Vérification supplémentaire de l'initialisation de Firebase
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }

        // Initialisation des vues
        nameSignup = findViewById(R.id.nameSignup);
        emailSignup = findViewById(R.id.emailSignup);
        passwordSignup = findViewById(R.id.passwordSignup);
        signupButton = findViewById(R.id.signupButton);
        goToLogin = findViewById(R.id.goToLogin);

        // Gestionnaire de clic pour le bouton d'inscription
        signupButton.setOnClickListener(v -> {
            String name = nameSignup.getText().toString().trim();
            String email = emailSignup.getText().toString().trim();
            String password = passwordSignup.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            User newUser = new User(name, email, password);
            Firebase.getInstance().createUserWithEmailAndPassword(newUser, new Firebase.LoginCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    Toast.makeText(SignupActivity.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(SignupActivity.this, "Erreur: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Redirection vers la connexion
        goToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        });
    }
} 