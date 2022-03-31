package com.example.l5_kantor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class logowanie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logowanie);


        Button logowanie = findViewById(R.id.logowanie);


        logowanie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText login = findViewById(R.id.pole_login);
                EditText haslo = findViewById(R.id.pole_haslo);

                String wpisany_login = login.getText().toString();
                String wpisane_haslo = haslo.getText().toString();

                if(wpisany_login.equals("admin") && wpisane_haslo.equals("admin")){
                    Intent intent = new Intent(logowanie.this, AdminPanel.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(logowanie.this, "zły login lub hasło !", Toast.LENGTH_SHORT).show();
                    login.getText().clear();
                    haslo.getText().clear();

                    System.out.println("wpisany login: "+ wpisany_login);
                    System.out.println("wpisane haslo: "+ wpisane_haslo);
                }
            }
        });


    }
}