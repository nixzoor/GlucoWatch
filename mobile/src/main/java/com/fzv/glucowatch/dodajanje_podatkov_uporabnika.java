package com.fzv.glucowatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.internal.zzgo;
import org.w3c.dom.Text;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class dodajanje_podatkov_uporabnika extends ActionBarActivity {

    EditText Ime;
    EditText Priimek;
    DatePicker DatRoj;
    EditText Spol;
    EditText Visina;
    EditText Teza;
    EditText Status;
    EditText Tip;
    EditText Zdravilo;

    TextView prikaz;

    TimePicker casZajtrka;
    TimePicker casKosila;
    TimePicker casVecerje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_podatkov_uporabnika);

        Ime = (EditText)findViewById(R.id.editTextIme);
        Priimek = (EditText)findViewById(R.id.editTextPriimek);
        DatRoj = (DatePicker)findViewById(R.id.datePicker);
        Spol = (EditText)findViewById(R.id.editTextSpol);
        Visina = (EditText)findViewById(R.id.editTextVisina);
        Teza = (EditText)findViewById(R.id.editTextTeza);
        Status = (EditText)findViewById(R.id.editTextStatus);
        Tip = (EditText)findViewById(R.id.editTextTip);
        Zdravilo = (EditText)findViewById(R.id.editTextZdravilo);

        prikaz = (TextView)findViewById(R.id.textViewPrikazPodatkovUporabnika);

        casZajtrka = (TimePicker)findViewById(R.id.timePickerZajtrk);
        casZajtrka.setIs24HourView(true);
        casKosila = (TimePicker)findViewById(R.id.timePickerKosilo);
        casKosila.setIs24HourView(true);
        casVecerje = (TimePicker)findViewById(R.id.timePickerVecerja);
        casVecerje.setIs24HourView(true);

        try {
            DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
            Uporabnik vrnjen = dbHandler.vrniPodatkeUporabnika();
            String podatki = "Ime: " + vrnjen.getIme() + "\n Priimek: " + vrnjen.getPriimek() + "\n Datum rojstva: " + vrnjen.getDatumRojstva() +
                    "\n Spol: " + vrnjen.getSpol() + "\n Visina(cm): " + vrnjen.getVisina().toString() + "\n Teza(kg): " + vrnjen.getTeza().toString() +
                    "\n Zaposlitveni status: " + vrnjen.getZapislitveniStatus() + "\n Tip bolezni: " + vrnjen.getTipBolezni() + "\n Osnovno zdravilo: " + vrnjen.getOsnovnoZdravilo();

            CasObrokov[] obroki = dbHandler.vrniCaseObrokov();

            podatki += "\nPREDVIDENI ČASI OBROKOV \n Predviden čas zajtrka: " + obroki[0].getCasObroka() +
                    "\nPredviden čas kosila: " + obroki[1].getCasObroka() +
                    "\nPredviden čas večerje: " + obroki[2].getCasObroka();

            prikaz.setText(podatki);

            Ime.setText(vrnjen.getIme());
            Priimek.setText(vrnjen.getPriimek());
            Spol.setText(vrnjen.getSpol());
            Visina.setText(vrnjen.getVisina().toString());
            Teza.setText(vrnjen.getTeza().toString());
            Status.setText(vrnjen.getZapislitveniStatus());
            Tip.setText(vrnjen.getTipBolezni());
            Zdravilo.setText(vrnjen.getOsnovnoZdravilo());
        }
        catch(Exception e)
        {

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dodajanje_podatkov_uporabnika, menu);
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

    public void dodajPodatkeUporabnika(View view)
    {
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);

        String DatumRojstva = DatRoj.getDayOfMonth() + "." + DatRoj.getMonth() + "." + DatRoj.getYear();

        Uporabnik u = new Uporabnik(Ime.getText().toString(),Priimek.getText().toString(),DatumRojstva,Spol.getText().toString(),Double.parseDouble(Visina.getText().toString()),Double.parseDouble(Teza.getText().toString()), Status.getText().toString(), Tip.getText().toString(), Zdravilo.getText().toString());
        dbHandler.dodajPodatkeUporabnika(u);
        prikaz.setText("buckeee!");

        //Izbriše vse predhodnje čase obrokov
        dbHandler.BrisiCaseObrokov();
        //Dodajanje casa zajtrka
        CasObrokov casObrokov = new CasObrokov(casZajtrka.getCurrentHour().toString() + ":" + casZajtrka.getCurrentMinute().toString(), "Zajtrk");
        dbHandler.dodajCaseObrokov(casObrokov);

        //Dodajanje casa kosila
        casObrokov = new CasObrokov(casKosila.getCurrentHour().toString() + ":" + casKosila.getCurrentMinute().toString(), "Kosilo");
        dbHandler.dodajCaseObrokov(casObrokov);

        //Dodajanje casa večerje
        casObrokov = new CasObrokov(casVecerje.getCurrentHour().toString() + ":" + casVecerje.getCurrentMinute().toString(), "Večerja");
        dbHandler.dodajCaseObrokov(casObrokov);

        //Sporočilo da so podatki shranjeni
        Toast.makeText(getApplicationContext(), "Podatki shranjeni",
                Toast.LENGTH_LONG).show();

        Uporabnik vrnjen = dbHandler.vrniPodatkeUporabnika();
        String podatki = "Ime: " + vrnjen.getIme() + "\n Priimek: " + vrnjen.getPriimek() + "\n Datum rojstva: " + vrnjen.getDatumRojstva() +
                "\n Spol: " + vrnjen.getSpol() + "\n Visina(cm): " + vrnjen.getVisina().toString() + "\n Teza(kg): " + vrnjen.getTeza().toString() +
                "\n Zaposlitveni status: " + vrnjen.getZapislitveniStatus() + "\n Tip bolezni: " + vrnjen.getTipBolezni() + "\n Osnovno zdravilo: " + vrnjen.getOsnovnoZdravilo();

        CasObrokov[] obroki = dbHandler.vrniCaseObrokov();

        podatki += "\nPREDVIDENI ČASI OBROKOV \n Predviden čas zajtrka: " + obroki[0].getCasObroka() +
                "\nPredviden čas kosila: " + obroki[1].getCasObroka() +
                "\nPredviden čas večerje: " + obroki[2].getCasObroka();

        prikaz.setText(podatki);
    }

    public void IzborSpola(View view) {
        final CharSequence[] items = {
                "Moški", "Ženski"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izberite spol");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                Spol.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public void IzborStatusa(View view) {
        final CharSequence[] items = {
                "Šoloobvezen otrok", "Dijak", "Študent", "Zaposlen", "Brezposeln", "Upokojen"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izberite status");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                Status.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void IzborTipaBolezni(View view) {
        final CharSequence[] items = {
                "Tip 1", "Tip 2"
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izberite tip bolezni");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                Tip.setText(items[item]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void IzborTerapije(View view) {

        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Uporabnik vrnjen = dbHandler.vrniPodatkeUporabnika();
        if(vrnjen.getOsnovnoZdravilo() == "Tip 1")
        {
            final CharSequence[] items = {"Inzulinska črpalka (hitrodelujoči inzulini)",
                    "Injekcije za samoinjiciranje inzulina (kratkodelujoči + dolgodelujoči)"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Izberite tip bolezni");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                    Tip.setText(items[item]);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else if (vrnjen.getTipBolezni() == "Tip 2")
        {
            final CharSequence[] items = {
                    "Terapija samo s prilagojeno prehrano (dieto) in gibanjem",
                    "Terapija s tabletami",
                    "Kombinacija tablete + inzulin",
                    "Terapija samo z inzulinom (lahko inzulinska črpalka ali samoinjekcijska peresa)"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Izberite tip bolezni");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                    Tip.setText(items[item]);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            final CharSequence[] items = {
                    "Inzulinska črpalka (hitrodelujoči inzulini)",
                    "Injekcije za samoinjiciranje inzulina (kratkodelujoči + dolgodelujoči)",
                    "Terapija samo s prilagojeno prehrano (dieto) in gibanjem",
                    "Terapija s tabletami",
                    "Kombinacija tablete + inzulin",
                    "Terapija samo z inzulinom (lahko inzulinska črpalka ali samoinjekcijska peresa)"
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Izberite tip bolezni");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item) {
                    // Do something with the selection
                /*Toast.makeText(getApplicationContext(), "Izbrali ste nekaj",
                        Toast.LENGTH_SHORT).show();*/
                    Tip.setText(items[item]);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }





    }
}
