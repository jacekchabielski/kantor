package com.example.l5_kantor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper DB;

    Button wyswietlCeny;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner dropdown = findViewById(R.id.ks);
        Button przelicz = findViewById(R.id.przelicz);
        EditText ilosc = findViewById(R.id.ilosc);
        Spinner waluta1 = findViewById(R.id.waluta1);
        Spinner waluta2 = findViewById(R.id.waluta2);




        String[] items = new String[]{"wybierz","Kupno","Sprzedaz"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        DB = new DBHelper(this);
        TextView wysw = findViewById(R.id.wyswietlanie);
        wysw.setMovementMethod(new ScrollingMovementMethod());

        Cursor testres = DB.getdata();
        StringBuffer testbuffer = new StringBuffer();
        if(testres.getCount() == 0){
            wysw.setText("brak rekordow");
        }
        ArrayList<String> nazwy_walut = new ArrayList<String>();
        ArrayList<Float> kurs_kupna = new ArrayList<Float>();
        ArrayList<Float> kurs_sprzedazy = new ArrayList<Float>();


        while(testres.moveToNext()){
            testbuffer.append("Name: "+testres.getString(0)+"\n");
            testbuffer.append("Kupno: "+testres.getString(1)+"\n");
            testbuffer.append("Sprzedaz: "+testres.getString(2)+"\n");

        nazwy_walut.add(testres.getString(0));
        kurs_kupna.add(testres.getFloat(1));
        kurs_sprzedazy.add(testres.getFloat(2));
        }
        wysw.setText(testbuffer.toString());

        System.out.println("kurs kupna: "+kurs_kupna);
        System.out.println("kurs sprzedazy: "+kurs_sprzedazy);

        ArrayAdapter<String> adapterr = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nazwy_walut);
        waluta1.setAdapter(adapterr);
        waluta2.setAdapter(adapterr);


        TextView wynik_finalny = findViewById(R.id.wynik);

        przelicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String kupno_czy_sprzedaz = dropdown.getSelectedItem().toString();
                //int ile_pitosu = Integer.parseInt(String.valueOf(ilosc));

                int ile_pitosu=Integer.parseInt(ilosc.getText().toString());

                int wal1 = waluta1.getSelectedItemPosition();
                int wal2 = waluta2.getSelectedItemPosition() ;

                //System.out.println("tyle pitosu"+ile_pitosu);
                float tmp = 0;
                String wynik = "";
                if(kupno_czy_sprzedaz.equals("Kupno")){
                    //chcemy z eurasow na amerykanskie, to bierzemy ile razy kurs euro i mamy euro
                    tmp = ile_pitosu * kurs_kupna.get(wal1);
                    tmp = tmp / kurs_kupna.get(wal2);
                    wynik = Float.toString(tmp);
                    wynik_finalny.setText(wynik);
                }

                if(kupno_czy_sprzedaz.equals("Sprzedaz")){
                    //chcemy z eurasow na amerykanskie, to bierzemy ile razy kurs euro i mamy euro
                    tmp = ile_pitosu * kurs_sprzedazy.get(wal1);
                    tmp = tmp / kurs_sprzedazy.get(wal2);
                    wynik = Float.toString(tmp);
                    wynik_finalny.setText(wynik);
                }
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(MainActivity.this, logowanie.class);
        startActivity(intent);
    }

}