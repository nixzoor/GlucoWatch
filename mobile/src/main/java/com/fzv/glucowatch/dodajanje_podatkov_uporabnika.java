package com.fzv.glucowatch;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
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
    EditText UporabljenoZdravilo;

    TextView prikaz;

    TimePicker casZajtrka;
    TimePicker casKosila;
    TimePicker casVecerje;

    //Za servise
    private PendingIntent pendingIntent;
    private PendingIntent pendingIntent2;
    private PendingIntent pendingIntent3;

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
        UporabljenoZdravilo = (EditText)findViewById(R.id.editTextUporabljenoZdravilo);


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
            String podatki = "VAŠI PODATKI \nIme: " + vrnjen.getIme() + "\nPriimek: " + vrnjen.getPriimek() + "\nDatum rojstva: " + vrnjen.getDatumRojstva() +
                    "\nSpol: " + vrnjen.getSpol() + "\nVisina(cm): " + vrnjen.getVisina().toString() + "\nTeza(kg): " + vrnjen.getTeza().toString() +
                    "\nZaposlitveni status: " + vrnjen.getZapislitveniStatus() + "\nTip bolezni: " + vrnjen.getTipBolezni() + "\nOsnovno zdravilo: " + vrnjen.getOsnovnoZdravilo();

            try
            {
                podatki += "\nUporabljeno Zdravilo: " + vrnjen.getUporabljeno_zdravilo() + "\n";
            }
            catch (Exception ex2)
            {

            }
            CasObrokov[] obroki = dbHandler.vrniCaseObrokov();

            podatki += "\nPREDVIDENI ČASI OBROKOV \nPredviden čas zajtrka: " + obroki[0].getCasObroka() +
                    "\nPredviden čas kosila: " + obroki[1].getCasObroka() +
                    "\nPredviden čas večerje: " + obroki[2].getCasObroka();

            prikaz.setText(podatki);


            //Integer leto = vrnjen.getLetoRojstva().intValue();
            DatRoj.updateDate(vrnjen.getLetoRojstva().intValue(), vrnjen.getMesecRojstva().intValue(), vrnjen.getDanRojstva().intValue());
            Ime.setText(vrnjen.getIme());
            Priimek.setText(vrnjen.getPriimek());
            Spol.setText(vrnjen.getSpol());
            Visina.setText(vrnjen.getVisina().toString());
            Teza.setText(vrnjen.getTeza().toString());
            Status.setText(vrnjen.getZapislitveniStatus());
            Tip.setText(vrnjen.getTipBolezni());
            Zdravilo.setText(vrnjen.getOsnovnoZdravilo());

            try
            {
                UporabljenoZdravilo.setText(vrnjen.getUporabljeno_zdravilo());
            }
            catch (Exception ex2)
            {

            }

            //Nastavljanje časov na timepickerjih!
            String[] cas = obroki[0].getCasObroka().split(":");
            Integer ura = Integer.parseInt(cas[0]);
            Integer minute = Integer.parseInt(cas[1]);

            casZajtrka.setCurrentHour(ura);
            casZajtrka.setCurrentMinute(minute);

            //Za kosilo!
            cas = obroki[1].getCasObroka().split(":");
            ura = Integer.parseInt(cas[0]);
            minute = Integer.parseInt(cas[1]);
            casKosila.setCurrentHour(ura);
            casKosila.setCurrentMinute(minute);
            //Za večerjo
            cas = obroki[2].getCasObroka().split(":");
            ura = Integer.parseInt(cas[0]);
            minute = Integer.parseInt(cas[1]);
            casVecerje.setCurrentHour(ura);
            casVecerje.setCurrentMinute(minute);
        }
        catch(Exception e)
        {

            /*Toast.makeText(getApplicationContext(), e.toString(),
                    Toast.LENGTH_LONG).show();*/
            prikaz.setHeight(0);
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
        try {
            String DatumRojstva = DatRoj.getDayOfMonth() + "." + DatRoj.getMonth() + "." + DatRoj.getYear();

            Double leto = DatRoj.getYear() * 1.0;
            Double mesec = DatRoj.getMonth() * 1.0;
            Double dan = DatRoj.getDayOfMonth() * 1.0;

            Uporabnik u = new Uporabnik(Ime.getText().toString(), Priimek.getText().toString(), DatumRojstva, leto, mesec, dan, Spol.getText().toString(), Double.parseDouble(Visina.getText().toString()), Double.parseDouble(Teza.getText().toString()), Status.getText().toString(), Tip.getText().toString(), Zdravilo.getText().toString(), UporabljenoZdravilo.getText().toString());

            //Uporabnik u2 = new Uporabnik(Ime.getText().toString(), Priimek.getText().toString(), DatumRojstva,DatRoj.getYear(), DatRoj.getMonth(), DatRoj.getDayOfMonth(), Spol.getText().toString(), Double.parseDouble(Visina.getText().toString()), Double.parseDouble(Teza.getText().toString()), Status.getText().toString(), Tip.getText().toString(), Zdravilo.getText().toString());
            dbHandler.dodajPodatkeUporabnika(u);
            //prikaz.setText("buckeee!");

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
            String podatki = "Ime: " + vrnjen.getIme() + "\nPriimek: " + vrnjen.getPriimek() + "\nDatum rojstva: " + vrnjen.getDatumRojstva() +
                    "\nSpol: " + vrnjen.getSpol() + "\nVisina(cm): " + vrnjen.getVisina().toString() + "\nTeza(kg): " + vrnjen.getTeza().toString() +
                    "\nZaposlitveni status: " + vrnjen.getZapislitveniStatus() + "\nTip bolezni: " + vrnjen.getTipBolezni() + "\nOsnovno zdravilo: " + vrnjen.getOsnovnoZdravilo();

            CasObrokov[] obroki = dbHandler.vrniCaseObrokov();

            podatki += "\nPREDVIDENI ČASI OBROKOV \n Predviden čas zajtrka: " + obroki[0].getCasObroka() +
                    "\nPredviden čas kosila: " + obroki[1].getCasObroka() +
                    "\nPredviden čas večerje: " + obroki[2].getCasObroka();

            prikaz.setText(podatki);



            startActivity(new Intent(dodajanje_podatkov_uporabnika.this, MainActivity.class) );
        }
        catch (Exception ex)
        {
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Napaka pri vnosu podatkov \nPopravite vpisane podatke in poizkusite znova.");
            dlgAlert.setTitle("Napaka pri vnosu");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.create().show();
        }



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
                    Zdravilo.setText(items[item]);
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
                    Zdravilo.setText(items[item]);
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
                    Zdravilo.setText(items[item]);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }





    }

    public void NavodilaMoznosti(View view)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Kliknite na prostor za vpisovanja, da se vam prikažejo možnosti.");
        dlgAlert.setTitle("Navodilo");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.create().show();
    }
}
