package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class napovedni_model extends ActionBarActivity {

    TextView napoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napovedni_model);


        napoved = (TextView)findViewById(R.id.txtNapovedni);
        //Get data about meals
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Obrok [] vsiObroki = dbHandler.vrniVseObroke();
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();
        Aktivnost [] vseAktivnosti = dbHandler.vrniVseAktivnost();


        String text = "";

        if(vseMeritve.length > 5) {

            //Pridobimo povprečne dnevne vrednosti obrokov
            Date d = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");
            Date temp;

            List<Integer> DnevneVrednosti = new ArrayList<Integer>();

            int stevec = 0;
            Double vsota = 0.0;


            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

            Calendar current = Calendar.getInstance();
            try
            {
                for (Integer i = vsiObroki.length - 1; i > 0; i--)
                {
                    temp = sf.parse(vsiObroki[i].getDatumCas());
                    cal.setTime(sdf.parse(vsiObroki[i].getDatumCas()));

                    //napoved.setText(temp.toString() + ",..." + d.toString());
                    if(current.get(Calendar.DAY_OF_WEEK) == cal.get(Calendar.DAY_OF_WEEK) && current.get(Calendar.MONTH) == cal.get(Calendar.MONTH) && current.get(Calendar.YEAR) == cal.get(Calendar.YEAR))
                    {
                        DnevneVrednosti.add(vsiObroki[i].getOdstotekOH());
                    }
                }
            }
            catch (Exception ex)
            {

            }


            //Če je povprečje zadnjih treh meritev večje od celotnega povprečja sklepa da se mu bo cuker zvišač
            double avgLast3 = (vseMeritve[vseMeritve.length - 1].getVrednostGlukoze() + vseMeritve[vseMeritve.length - 2].getVrednostGlukoze() + vseMeritve[vseMeritve.length - 3].getVrednostGlukoze()) / 3;
            double sum = 0;
            double counter = 0;
            for(Integer i = 0; i < vseMeritve.length - 3; i++)
            {
                sum += vseMeritve[i].getVrednostGlukoze();
                counter++;
            }
            sum = sum / counter;


            //Današnji obroki
            if(avgLast3 > sum)
            {
                if(DnevneVrednosti.size() > 0)
                {
                    double sumOH = 0;
                    counter = 0;
                    for (Integer i = 0; i < DnevneVrednosti.size(); i++)
                    {
                        sumOH += DnevneVrednosti.get(i);
                        counter ++;
                    }
                    sumOH = sumOH / counter;

                    if(sumOH > 70) text += "Napovedni model pričakuje močno povišanje krvnega sladkorja!";
                    else if(sumOH > 50) text += "Napovedni model pričakuje povišanje vrednosti krvnega sladkorja";
                    else text += "Napovedni model pričakuje rahlo povišanje vrednosti krvnega sladkorja! :)";
                }
                else {
                    text += "Povišanje vrednosti sladkorja v zadnjih meritvah. Napovedni model predvideva, da bo sladkor še naprej rasel.";
                }
            }
            if(avgLast3 < sum)
            {
                if(DnevneVrednosti.size() > 0)
                {
                    double sumOH = 0;
                    counter = 0;
                    for (Integer i = 0; i < DnevneVrednosti.size(); i++)
                    {
                        sumOH += DnevneVrednosti.get(i);
                        counter ++;
                    }
                    sumOH = sumOH / counter;

                    if(sumOH > 70) text += "Napovedni model pričakuje rahlo povišanje krvnega sladkorja!";
                    else if(sumOH > 50) text += "Napovedni model pričakuje rahlo padanje vrednosti krvnega sladkorja";
                    else text += "Napovedni model pričakuje močno znižanje vrednosti krvnega sladkorja! :)";
                }
                else {
                    text += "Vrednosti glukoze v zadnjih meritvah padajo. Napovedni model predvideva, da bodo ostale konstantne, ali padale naprej.";
                }
            }

            text += "\n \n \n \n";
            text += "Povprečje zadnjih treh meritev:\n" + avgLast3 + "\n";

            double sumOH = 0;
            counter = 0;
            for (Integer i = 0; i < DnevneVrednosti.size(); i++)
            {
                sumOH += DnevneVrednosti.get(i);
                counter ++;
            }
            sumOH = sumOH / counter;

            text += "Povprečje danes zaužitih ogljikovih hiratov: \n" + sumOH;


        }
        else
        {
            text += "Premalo podatkov za izdelavo napovednega modela...";
        }

        napoved.setText(text);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_napovedni_model, menu);
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
}
