package com.example.l5_kantor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminPanel extends AppCompatActivity {

    EditText name, contact, dob;
    Button insert, update, delete, view;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.kupno);
        dob = findViewById(R.id.sprzedaz);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();


                Boolean checkinsertdata = DB.insertuserdata(nameTXT,contactTXT,dobTXT);

                if(checkinsertdata == true)
                    Toast.makeText(AdminPanel.this, "wstawiono do bazy", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AdminPanel.this, "nie udalo sie wstawic", Toast.LENGTH_SHORT).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                Boolean checkupdatedata = false;
                float sprawdz1 = Float.parseFloat(contact.getText().toString());
                float sprawdz2 = Float.parseFloat(dob.getText().toString());
                if(sprawdz1 > sprawdz2){
                     checkupdatedata = DB.updateuserdata(nameTXT,contactTXT,dobTXT);
                }else{
                    Toast.makeText(AdminPanel.this, "popraw dane !", Toast.LENGTH_SHORT).show();
                }


                if(checkupdatedata == true)
                    Toast.makeText(AdminPanel.this, "zaktualizowano baze", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AdminPanel.this, "nie udalo sie zaktualizowac", Toast.LENGTH_SHORT).show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();

                Boolean checkdeletedata = DB.deletedata(nameTXT);

                if(checkdeletedata == true)
                    Toast.makeText(AdminPanel.this, "usunieto", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AdminPanel.this, "nie udalo sie usunac", Toast.LENGTH_SHORT).show();
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Cursor res = DB.getdata();
                 if(res.getCount() ==0){
                     Toast.makeText(AdminPanel.this, "pusto", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 StringBuffer buffer = new StringBuffer();
                 while(res.moveToNext()){
                     buffer.append("Name: "+res.getString(0)+"\n");
                     buffer.append("Kupno: "+res.getString(1)+"\n");
                     buffer.append("Sprzedaz: "+res.getString(2)+"\n");
                 }
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanel.this);
                 builder.setCancelable(true);
                 builder.setTitle("zawartosc bazy");
                 builder.setMessage(buffer.toString());
                 builder.show();
            }
        });


    }
    public void powrot(View view) {
        Intent intent = new Intent(AdminPanel.this, MainActivity.class);
        startActivity(intent);
    }
}