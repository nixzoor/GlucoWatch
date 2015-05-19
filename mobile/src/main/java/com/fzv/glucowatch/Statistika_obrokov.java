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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Statistika_obrokov extends ActionBarActivity {

    GraphView graph;
    TextView txtavg;
    TextView txtavg10;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika_obrokov);

        txtavg = (TextView)findViewById(R.id.txtPovprecjeOH);
        txtavg10 = (TextView)findViewById(R.id.txtPovprecjeOh10);
        test = (TextView) findViewById(R.id.textView11);

        graph = (GraphView) findViewById(R.id.graph);
        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Obrok [] vsiObroki = dbHandler.vrniVseObroke();

        if(vsiObroki.length > 0) {
            Double[] procentOH = new Double[vsiObroki.length];


            Date d = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("dd.MM.yyyy");

            Date temp;
            List<Double> povprecneDnevneVrednosti = new ArrayList<Double>();

            int stevec = 0;
            Double vsota = 0.0;
            try {
                //Nastavimo začetni datum
                temp = sf.parse(vsiObroki[0].getDatumCas());

                //Gremo skozi vse zapise
                for (int i = 0; i < vsiObroki.length; i++) {

                    d = sf.parse(vsiObroki[i].getDatumCas());
                    if (d.equals(temp))//Preverjamo če je obrok še v istem dnevu
                    {
                        stevec++;
                        vsota += vsiObroki[i].getOdstotekOH();
                    } else {
                        povprecneDnevneVrednosti.add(vsota / stevec);
                        stevec = 1;
                        vsota = 0.0;
                        temp = sf.parse(vsiObroki[i].getDatumCas());
                        vsota += vsiObroki[i].getOdstotekOH();
                    }


                }
                povprecneDnevneVrednosti.add(vsota / stevec);
            } catch (Exception e) {

            }


            //UStvarjanje točk s podatki iz polj

            LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

            DataPoint dPoint;
            String testBesedilo = "";

            Double avg10 = 0.0;
            stevec = 0;
            if (povprecneDnevneVrednosti.size() > 10) {
                for (int i = povprecneDnevneVrednosti.size() - 10; i < povprecneDnevneVrednosti.size(); i++) {
                    dPoint = new DataPoint(i, povprecneDnevneVrednosti.get(i));
                    series.appendData(dPoint, true, 10);
                    testBesedilo += povprecneDnevneVrednosti.get(i).toString() + ", ";

                    avg10 += povprecneDnevneVrednosti.get(i);
                    stevec++;
                }
            } else {
                for (int i = 0; i < povprecneDnevneVrednosti.size(); i++) {
                    dPoint = new DataPoint(i, povprecneDnevneVrednosti.get(i));
                    series.appendData(dPoint, true, 10);
                    testBesedilo += povprecneDnevneVrednosti.get(i).toString() + ", ";

                    avg10 += povprecneDnevneVrednosti.get(i);
                    stevec++;
                }
            }
            avg10 = avg10 / stevec;
            graph.addSeries(series);

        /*------------Izracun in izris-----------------*/
            stevec = 0;
            Double avgvseh = 0.0;
            for (Integer i = 0; i < povprecneDnevneVrednosti.size(); i++) {
                avgvseh += povprecneDnevneVrednosti.get(i);
                stevec++;
            }
            avgvseh = avgvseh / stevec;

            //Izris
            try {
                DecimalFormat dfFormat = new DecimalFormat("#.##");
                avg10 = Double.valueOf(dfFormat.format(avg10));
                avgvseh = Double.valueOf(dfFormat.format(avgvseh));
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

            txtavg.setText(avgvseh.toString() + " %");
            txtavg10.setText(avg10.toString() + " %");
        }
        else
        {
            txtavg.setText("Ni zapisov");
            txtavg10.setText("Ni zapisov");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistika_obrokov, menu);
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
