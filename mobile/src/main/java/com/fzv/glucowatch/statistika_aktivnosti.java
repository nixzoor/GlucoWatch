package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class statistika_aktivnosti extends ActionBarActivity {
    GraphView graph;

    TextView txtavg;
    TextView txtmin;
    TextView txtmax;
    TextView txtavg10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika_aktivnosti);

        txtavg = (TextView)findViewById(R.id.txtPovprecnoTrajanje);
        txtmin = (TextView)findViewById(R.id.txtMinTrajanje);
        txtmax = (TextView)findViewById(R.id.txtMaxTrajanje);
        txtavg10 = (TextView)findViewById(R.id.txtAvg10Trajanje);
        //Pridobivanje grafa
        graph = (GraphView) findViewById(R.id.graph);

        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Aktivnost [] vseAktivnosti = dbHandler.vrniVseAktivnost();


        if(vseAktivnosti.length>0) {
            Double[] trajanja = new Double[vseAktivnosti.length];
            //Double [] intenzivnostiTrajanje = new Double[vseAktivnosti.length];
            double test = 24;

            for (int i = 0; i < vseAktivnosti.length; i++) {
                try {
                    trajanja[i] = 1.0 * vseAktivnosti[i].getTrajanje();
                    if (vseAktivnosti[i].getIntenzivnost() == "Srednja") {
                        //intenzivnostiTrajanje[i] = trajanja[i] * 1.5;
                        trajanja[i] = trajanja[i] * 1.5;

                    } else if (vseAktivnosti[i].getIntenzivnost() == "Velika") {
                        //intenzivnostiTrajanje[i] = trajanja[i] * 2.0;
                        trajanja[i] = trajanja[i] * 2;
                    } else {
                        //intenzivnostiTrajanje[i] = trajanja[i];

                    }


                } catch (Exception e) {

                }
            }
            //UStvarjanje točk s podatki iz polj


            LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

            Double avg = 0.0;
            Double min;
            Double max;
            Integer stevec = 0;

            DataPoint dpoint;

            if (vseAktivnosti.length > 10) {
                min = trajanja[trajanja.length - 10];
                max = trajanja[trajanja.length - 10];

                for (int i = vseAktivnosti.length - 10; i < trajanja.length; i++) {
                    dpoint = new DataPoint(i, trajanja[i]);
                    series.appendData(dpoint, true, 10);

                    if (trajanja[i] < min) min = trajanja[i];
                    if (trajanja[i] > max) max = trajanja[i];
                    avg += trajanja[i];
                    stevec++;

            /*dpoint = new DataPoint(i,intenzivnostiTrajanje[i]);
            series2.appendData(dpoint,true,10);*/
                }
            } else {
                min = trajanja[0];
                max = trajanja[0];
                for (int i = 0; i < trajanja.length; i++) {
                    dpoint = new DataPoint(i, trajanja[i]);
                    series.appendData(dpoint, true, 10);

                    if (trajanja[i] < min) min = trajanja[i];
                    if (trajanja[i] > max) max = trajanja[i];
                    avg += trajanja[i];
                    stevec++;
            /*dpoint = new DataPoint(i,intenzivnostiTrajanje[i]);
            series2.appendData(dpoint,true,10);*/
                }
            }
            avg = avg / stevec;

            series.setTitle("Trajanje aktivnosti");

            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

            graph.addSeries(series);

        /*--------Iuračun ter izris------*/
            Double avgvseh = 0.0;
            stevec = 0;
            for (Integer i = 0; i < trajanja.length; i++) {
                avgvseh += trajanja[i];
                stevec++;
            }
            avgvseh = avgvseh / stevec;

            DecimalFormat dfFormat = new DecimalFormat("#.##");
            avg = Double.valueOf(dfFormat.format(avg));
            avgvseh = Double.valueOf(dfFormat.format(avgvseh));

            txtavg.setText(avgvseh.toString() + " min");
            txtavg10.setText(avg.toString() + " min");
            txtmin.setText(min.toString() + " min");
            txtmax.setText(max.toString() + "min");
        }
        else
        {
            txtavg.setText("Ni zapisov");
            txtavg10.setText("Ni zapisov");
            txtmin.setText("Ni zapisov");
            txtmax.setText("Ni zapisov");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistika_aktivnosti, menu);
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
