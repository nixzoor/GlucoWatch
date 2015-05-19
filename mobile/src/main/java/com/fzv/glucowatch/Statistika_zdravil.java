package com.fzv.glucowatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Statistika_zdravil extends ActionBarActivity {

    GraphView graph;
    TextView txtavgVseh;
    TextView txtavg10;
    TextView txtmax;
    TextView txtmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika_zdravil);

        graph = (GraphView) findViewById(R.id.graph);
        txtavgVseh = (TextView)findViewById(R.id.textViewAvgzdravil);
        txtavg10 = (TextView)findViewById(R.id.textViewAvg10zdravil);
        txtmin = (TextView)findViewById(R.id.textViewminzdravil);
        txtmax = (TextView)findViewById(R.id.textViewmaxzdravil);
        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        VnosZdravila [] vsiVnosi = dbHandler.vrniVseVnoseZdravil();

        if(vsiVnosi.length != 0) {
            Double[] kolicina = new Double[vsiVnosi.length];
            Date[] datumi = new Date[vsiVnosi.length];

            Date d;
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
            String temp = "";
            for (int i = 0; i < vsiVnosi.length; i++) {
                kolicina[i] = 1.0 * vsiVnosi[i].getOdmerek();
                try {
                    d = df.parse(vsiVnosi[i].getCasVnosa());
                    datumi[i] = d;
                } catch (ParseException e) {

                }
            }
            //UStvarjanje točk s podatki iz polj in računanje statistike

            Double min;
            Double max;
            Double avg = 0.0;
            Double avg10 = 0.0;

            Integer stevec = 0;
            LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

            DataPoint dpoint;
            if (vsiVnosi.length > 10) {
                min = kolicina[kolicina.length - 10];
                max = kolicina[kolicina.length - 10];

                for (int i = vsiVnosi.length - 10; i < vsiVnosi.length; i++) {
                    dpoint = new DataPoint(i, kolicina[i]);
                    series.appendData(dpoint, true, 10);

                    if (kolicina[i] < min) min = kolicina[i];
                    if (kolicina[i] > max) max = kolicina[i];
                    avg10 += kolicina[i];
                    stevec++;
                }
            } else {
                min = kolicina[0];
                max = kolicina[0];
                for (int i = 0; i < vsiVnosi.length; i++) {
                    dpoint = new DataPoint(i, kolicina[i]);
                    series.appendData(dpoint, true, 10);

                    if (kolicina[i] < min) min = kolicina[i];
                    if (kolicina[i] > max) max = kolicina[i];
                    avg10 += kolicina[i];
                    stevec++;
                }
            }
            avg10 = avg10 / stevec;

            series.setTitle("Odmerki inzulina");
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

            graph.addSeries(series);

        /*-----Izris statistike----------*/
            stevec = 0;
            for (int i = 0; i < kolicina.length; i++) {
                avg += kolicina[i];
                stevec++;
            }
            avg = avg / stevec;

            //Izpis
            try {
                DecimalFormat dfFormat = new DecimalFormat("#.##");
                avg = Double.valueOf(dfFormat.format(avg));
                avg10 = Double.valueOf(dfFormat.format(avg10));
            }
            catch (Exception ex)
            {
                /*AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                dlgAlert.setMessage("Napaka pri racunanju!!");
                dlgAlert.setTitle("Napaka pri vnosu");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                            }
                        });
                dlgAlert.create().show();*/
            }

            txtavgVseh.setText(avg.toString());
            txtavg10.setText(avg10.toString());
            txtmax.setText(max.toString());
            txtmin.setText(min.toString());
        }
        else
        {
            txtavgVseh.setText("Ni zapisov");
            txtavg10.setText("Ni zapisov");
            txtmax.setText("Ni zapisov");
            txtmin.setText("Ni zapisov");
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistika_zdravil, menu);
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
