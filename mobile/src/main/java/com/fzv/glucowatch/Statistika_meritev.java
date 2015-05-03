package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class Statistika_meritev extends ActionBarActivity {


    GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika_meritev);

        graph = (GraphView) findViewById(R.id.graph);

        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();

        Double [] vrednostimeritev = new Double[vseMeritve.length];
        Date [] datumi = new Date[vseMeritve.length];

        Date d;
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String temp = "";
        for(int i = 0; i < vseMeritve.length; i++)
        {
            vrednostimeritev[i] = (Double)vseMeritve[i].getVrednostGlukoze();
            temp = vseMeritve[i].getCasMeritve();
            try
            {
                d = df.parse(vseMeritve[i].getCasMeritve());
                datumi[i] = d;
            }
            catch(ParseException e)
            {

            }
        }
        //UStvarjanje točk s podatki iz polj


        LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

        DataPoint dpoint;
        for(int i = 0; i < vseMeritve.length; i++)
        {
            dpoint = new DataPoint(i, vrednostimeritev[i]);
            series.appendData(dpoint,true,10);
        }
        series.setTitle("Vrednosti glukoze");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        
        graph.addSeries(series);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistika_meritev, menu);
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

    public void IzrisGrafa(View view)
    {
        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();

        Double [] vrednostimeritev = new Double[vseMeritve.length];
        Date [] datumi = new Date[vseMeritve.length];

        Date d;
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String temp = "";
        for(int i = 0; i < vseMeritve.length; i++)
        {
            vrednostimeritev[i] = (Double)vseMeritve[i].getVrednostGlukoze();
            temp = vseMeritve[i].getCasMeritve();
            try
            {
                d = df.parse(vseMeritve[i].getCasMeritve());
                datumi[i] = d;
            }
            catch(ParseException e)
            {

            }
        }
        //UStvarjanje točk s podatki iz polj


        LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

        DataPoint dpoint;
        for(int i = 0; i < vseMeritve.length; i++)
        {
            dpoint = new DataPoint(i, vrednostimeritev[i]);
            series.appendData(dpoint,true,10);
        }
        graph.addSeries(series);

        series.setTitle("Vrednosti glukoze");
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
    }
}
