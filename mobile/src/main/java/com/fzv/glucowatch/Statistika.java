package com.fzv.glucowatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.fitness.data.DataPoint;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.LineGraphSeries;


public class Statistika extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistika, menu);
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


    public void activityStatistikaMeritev(View v)
    {
        startActivity(new Intent(Statistika.this, Statistika_meritev.class) );
    }
    public void activityStatistikaAktivnosti(View v)
    {
        startActivity(new Intent(Statistika.this, statistika_aktivnosti.class) );
    }
    public void activityStatistikaObrokov(View v)
    {
        startActivity(new Intent(Statistika.this, Statistika_obrokov.class) );
    }
    public void activityStatistikaVnosovZdravil(View v)
    {
        startActivity(new Intent(Statistika.this, Statistika_zdravil.class) );
    }
}
