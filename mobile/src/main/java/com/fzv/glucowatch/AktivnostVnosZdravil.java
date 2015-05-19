package com.fzv.glucowatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class AktivnostVnosZdravil extends ActionBarActivity {


    EditText EdittextZdravilo;
    EditText EdittextOdmerek;

    TextView PrikazVnosovZdravil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivnost_vnos_zdravil);

        EdittextZdravilo = (EditText)findViewById(R.id.editTextZdravilo);
        EdittextOdmerek = (EditText)findViewById(R.id.editTextOdmerek);
        PrikazVnosovZdravil = (TextView) findViewById(R.id.textViewPrikazVnosovZdravil);

        //Začetni prikaz podatkov

        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        VnosZdravila[] vsiVnosi = dbHandler.vrniVseVnoseZdravil();
        String text = " DOSEDANJI VNOSI ZDRAVIL \n \n";
        for(Integer i = vsiVnosi.length-1; i >= 0 && i > vsiVnosi.length - 11; i--)
        {
            text += vsiVnosi[i].getCasVnosa() + " - Zdravilo:" + vsiVnosi[i].getZdravilo() +" Odmerek: " + vsiVnosi[i].getOdmerek().toString() +"\n";
        }
        PrikazVnosovZdravil.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_aktivnost_vnos_zdravil, menu);
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

    public void dodajVnosZdravil(View view)
    {
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);

        Date d = new Date();
        SimpleDateFormat datumformat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String datum = datumformat.format(d);

        try {
            Integer odmerek = Integer.parseInt(EdittextOdmerek.getText().toString());
            VnosZdravila vnos = new VnosZdravila(datum, EdittextZdravilo.getText().toString(), odmerek);
            dbHandler.dodajVnosZdravila(vnos);
            Toast.makeText(getApplicationContext(), "Vnos zdravil dodan",
                    Toast.LENGTH_SHORT).show();

        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Pri vnosu je prišlo do napake. \nPreglejte podatke in poizkusite znova.");
            dlgAlert.setTitle("Napaka pri vnosu");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.create().show();
        }



        VnosZdravila[] vsiVnosi = dbHandler.vrniVseVnoseZdravil();

        String text = " DOSEDANJI VNOSI ZDRAVIL \n \n";


        for(Integer i = vsiVnosi.length-1; i >= 0 && i > vsiVnosi.length - 11; i--)
        {
            text += vsiVnosi[i].getCasVnosa() + " - Zdravilo:" + vsiVnosi[i].getZdravilo() +" Odmerek: " + vsiVnosi[i].getOdmerek().toString() +"\n";
        }

        PrikazVnosovZdravil.setText(text);

    }
}
