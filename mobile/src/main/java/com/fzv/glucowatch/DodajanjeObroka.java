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
import java.util.Date;


public class DodajanjeObroka extends ActionBarActivity {


    EditText procentOH;

    TextView vsiObrokiText;

    String velikostObrokaGumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_obroka);

        procentOH = (EditText)findViewById(R.id.txtProcentOH);
        vsiObrokiText = (TextView)findViewById(R.id.textViewPrikazObrokov);
        velikostObrokaGumb = "Srednje velik";

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
        //getMenuInflater().inflate(R.menu.menu_dodajanje_obroka, menu);
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

        Obrok o = new Obrok(datum,velikostObrokaGumb, Integer.parseInt(procentOH.getText().toString()));

        dbHandler.dodajObrok(o);
        Toast.makeText(getApplicationContext(), "Obrok dodan",
                Toast.LENGTH_SHORT).show();

        Obrok[] vsiObrokiPolje = dbHandler.vrniVseObroke();

        String text = "VSI OBROKI: \n \n";

        for(Integer i = 0; i < vsiObrokiPolje.length; i++)
        {
            text +=vsiObrokiPolje[i].getDatumCas() + ": Velikost obroka - "+ vsiObrokiPolje[i].getVelikostObroka()+" Odostotek OH: "+ vsiObrokiPolje[i].getOdstotekOH().toString() +"\n";
        }

        vsiObrokiText.setText(text);

    }

    public void spremeniVelikostObrokaMajhen(View view)
    {
        velikostObrokaGumb = "Majhen obrok";
    }
    public void spremeniVelikostObrokaSrednji(View view)
    {
        velikostObrokaGumb = "Srednje velik obrok";
    }
    public void spremeniVelikostObrokaVelik(View view)
    {
        velikostObrokaGumb = "Velik obrok";
    }

    public void NavodilaObrok(View view)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("30% - To velja, ko oseba poje sestavljen obrok, ki sestoji iz mesa, juhe, solate in kjer ogljikovi hidrati (riž, testenine, krompir, kruh…) predstavljajo samo prilogo.\n \n" +
                "50%-60% - Kadar oseba poje obrok, ki je sestavljen približno iz polovice ogljikovih hidrato in polovice drugih component (npr. zrezek+krompir, večina hitre prehrane, sendviči itd.)\n \n" +
                "80%-90% - Obroki, ki so sestavljeni pretežno iz ogljikovih hidratov in imajo zgolj dodano meso ali zelenjavo (rižote, testenine z omakami…)");
        dlgAlert.setTitle("Navodila za vnos oglikovih hidratov");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.create().show();
    }
}
