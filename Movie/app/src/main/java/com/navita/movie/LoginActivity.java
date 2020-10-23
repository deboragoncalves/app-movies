package com.navita.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText username = (EditText) LoginActivity.this.findViewById(R.id.input_login);
        final EditText password = (EditText) LoginActivity.this.findViewById(R.id.input_password);
        Button buttonLogin = (Button) LoginActivity.this.findViewById(R.id.button_login);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean validateLogin = validateLogin(username.getText().toString(), password.getText().toString());

                if (validateLogin) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });
    }

    private boolean validateLogin(String textUsername, String textPassword) {

        if (textUsername.contains("@") && textUsername.contains(".com")) {
            if (textPassword.length() > 7) {

                // Salvar em Shared Preferences

                saveSharedPreferences(textUsername, textPassword);
                return true;
            } else {
                Toast.makeText(LoginActivity.this, R.string.alert_password, Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(LoginActivity.this, R.string.alert_username, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void saveSharedPreferences(String textUsername, String textPassword) {

        // Armazenar em SharedPreferences, determinando a chave

        SharedPreferences sharedPreferences = getSharedPreferences("data_login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorSharedPrerences = sharedPreferences.edit();

        // Adicionar e salvar com o apply

        editorSharedPrerences.putString("username", textUsername);
        editorSharedPrerences.putString("password", textPassword);
        editorSharedPrerences.apply();
    }
}