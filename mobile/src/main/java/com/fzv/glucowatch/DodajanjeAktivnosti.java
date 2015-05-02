package com.fzv.glucowatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Dialog;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

public class DodajanjeAktivnosti extends ActionBarActivity {

    EditText vrsta;
    EditText trajanje;
    EditText intenzivnost;

    TextView aktivnostiBesedilo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_aktivnosti);

        vrsta = (EditText) findViewById(R.id.txtVrsta);
        trajanje = (EditText) findViewById(R.id.txtVelikostObroka);
        intenzivnost = (EditText)findViewById(R.id.txtProcentOH);
        aktivnostiBesedilo = (TextView)findViewById(R.id.textViewPrikazAktivnosti);

        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Aktivnost [] a = dbHandler.vrniVseAktivnost();
        String vseAktivnosti = "AKTIVNOSTI \n \n";
        for (Integer i = 0; i < a.length; i++)
        {
            vseAktivnosti += "Aktivnost: "+a[i].getVrsta()+" Trajanje: " + a[i].getTrajanje() +" Intenzivnost: "+ a[i].getIntenzivnost() + "\n";
        }
        aktivnostiBesedilo.setText(vseAktivnosti);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dodajanje_aktivnosti, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void dodajAktivnost(View view)
    {
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);

        Aktivnost aktivnost = new Aktivnost(vrsta.getText().toString(), Integer.parseInt(trajanje.getText().toString()), intenzivnost.getText().toString());

        dbHandler.dodajAktivnost(aktivnost);

        Toast.makeText(getApplicationContext(), "Aktivnost dodana",
                Toast.LENGTH_SHORT).show();

        vrsta.setText("");
        intenzivnost.setText("");
        trajanje.setText("");


        Aktivnost [] a = dbHandler.vrniVseAktivnost();

        String vseAktivnosti = "AKTIVNOSTI \n \n";

        for (Integer i = 0; i < a.length; i++)
        {
            vseAktivnosti += "Aktivnost: "+a[i].getVrsta()+" Trajanje: " + a[i].getTrajanje() +" Intenzivnost: "+ a[i].getIntenzivnost() + "\n";
        }

        aktivnostiBesedilo.setText(vseAktivnosti);

    }

    public void IzborAktivnosti(View view) {
        final CharSequence[] items = {
                "hitra hoja", "plavanje", "tenis", "tek", "hoja po stopnicah", "kolesarjenje", "skupinske vadbe(aerobika, joga, pilates...)", "smučanje", "skupinski športi(nogomet, košarka...)", "ples"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();
                vrsta.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void IzborIntenzivnosti(View view) {
        final CharSequence[] items = {
                "Majhna", "Srednja", "Velika"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izberite intenzivnost");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                intenzivnost.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
