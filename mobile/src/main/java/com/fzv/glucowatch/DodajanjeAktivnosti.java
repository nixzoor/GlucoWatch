package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


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
        getMenuInflater().inflate(R.menu.menu_dodajanje_aktivnosti, menu);
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
}
