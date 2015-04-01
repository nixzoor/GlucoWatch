package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DodajanjeObroka extends ActionBarActivity {


    EditText velikostObroka;
    EditText procentOH;

    TextView vsiObrokiText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_obroka);

        velikostObroka = (EditText)findViewById(R.id.txtVelikostObroka);
        procentOH = (EditText)findViewById(R.id.txtProcentOH);
        vsiObrokiText = (TextView)findViewById(R.id.textViewPrikazObrokov);

        DB_Handler dbHandler = new DB_Handler(this,null,null,1);
        Obrok[] vsiObrokiPolje = dbHandler.vrniVseObroke();
        String text = "VSI OBROKI: \n \n";
        for(Integer i = 0; i < vsiObrokiPolje.length; i++)
        {
            text +=vsiObrokiPolje[i].getDatumCas() + ": Velikost obroka - "+ vsiObrokiPolje[i].getVelikostObroka()+" Odostotek OH: "+ vsiObrokiPolje[i].getOdstotekOH().toString() +"\n";
        }
        vsiObrokiText.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dodajanje_obroka, menu);
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

    public void dodajNovObrok(View view)
    {
        DB_Handler dbHandler = new DB_Handler(this,null,null,1);

        Date d = new Date();
        SimpleDateFormat datumformat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String datum = datumformat.format(d);

        Obrok o = new Obrok(datum,velikostObroka.getText().toString(), Integer.parseInt(procentOH.getText().toString()));

        dbHandler.dodajObrok(o);

        Obrok[] vsiObrokiPolje = dbHandler.vrniVseObroke();

        String text = "VSI OBROKI: \n \n";

        for(Integer i = 0; i < vsiObrokiPolje.length; i++)
        {
            text +=vsiObrokiPolje[i].getDatumCas() + ": Velikost obroka - "+ vsiObrokiPolje[i].getVelikostObroka()+" Odostotek OH: "+ vsiObrokiPolje[i].getOdstotekOH().toString() +"\n";
        }

        vsiObrokiText.setText(text);

    }
}
