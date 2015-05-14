package com.fzv.glucowatch;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class Statistika_meritev extends ActionBarActivity {


    GraphView graph;

    TextView txtavgVseh;
    TextView txtavg10;
    TextView txtmax;
    TextView txtmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistika_meritev);

        graph = (GraphView) findViewById(R.id.graph);
        txtavgVseh = (TextView)findViewById(R.id.textViewAvgMeritev);
        txtavg10 = (TextView)findViewById(R.id.textViewavg10);
        txtmin = (TextView)findViewById(R.id.textViewmin);
        txtmax = (TextView)findViewById(R.id.textViewmax);

        //Pridobitev podatkov iz tabele, ter sprememba v double, ter datum Date
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();

        if(vseMeritve.length > 0) {
            Double[] vrednostimeritev = new Double[vseMeritve.length];
            Date[] datumi = new Date[vseMeritve.length];

            Date d;
            SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
            String temp = "";
            for (int i = 0; i < vseMeritve.length; i++) {
                vrednostimeritev[i] = (Double) vseMeritve[i].getVrednostGlukoze();
                temp = vseMeritve[i].getCasMeritve();
                try {
                    d = df.parse(vseMeritve[i].getCasMeritve());
                    datumi[i] = d;
                } catch (ParseException e) {

                }
            }
            //UStvarjanje točk s podatki iz polj in računanje statistike


            LineGraphSeries<DataPoint> series = new LineGraphSeries<com.jjoe64.graphview.series.DataPoint>();

            DataPoint dpoint;

            Double min;
            Double max;
            Double avgVseh = 0.0;
            Double avg = 0.0;
            Integer stevec = 0;

            if (vseMeritve.length > 10) {
                min = vrednostimeritev[vseMeritve.length - 10];
                max = vrednostimeritev[vseMeritve.length - 10];
                for (int i = vseMeritve.length - 10; i < vseMeritve.length; i++) {
                    dpoint = new DataPoint(i, vrednostimeritev[i]);
                    series.appendData(dpoint, true, 10);

                    if (vrednostimeritev[i] > max) max = vrednostimeritev[i];
                    if (vrednostimeritev[i] < min) min = vrednostimeritev[i];
                    avg += vrednostimeritev[i];
                    stevec++;
                }
            } else {
                min = vrednostimeritev[0];
                max = vrednostimeritev[0];
                for (int i = 0; i < vseMeritve.length; i++) {
                    dpoint = new DataPoint(i, vrednostimeritev[i]);
                    series.appendData(dpoint, true, 10);

                    if (vrednostimeritev[i] > max) max = vrednostimeritev[i];
                    if (vrednostimeritev[i] < min) min = vrednostimeritev[i];
                    avg += vrednostimeritev[i];
                    stevec++;
                }
            }
            avg = avg / stevec;

            series.setTitle("Vrednosti glukoze");
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);

            graph.addSeries(series);

        /*------Statistika-------------*/
            stevec = 0;
            for (int i = 0; i < vrednostimeritev.length; i++) {
                avgVseh += vrednostimeritev[i];
                stevec++;
            }
            avgVseh = avgVseh / stevec;

        /*Izpis statistike*/

            DecimalFormat dfFormat = new DecimalFormat("#.##");
            avg = Double.valueOf(dfFormat.format(avg));
            avgVseh = Double.valueOf(dfFormat.format(avgVseh));
            txtavg10.setText(avg.toString());
            txtavgVseh.setText(avgVseh.toString());
            txtmax.setText(max.toString());
            txtmin.setText(min.toString());
        }
        else
        {
            txtavg10.setText("Ni zapisov");
            txtavgVseh.setText("Ni zapisov");
            txtmax.setText("Ni zapisov");
            txtmin.setText("Ni zapisov");
        }
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
