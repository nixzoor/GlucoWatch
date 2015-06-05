package com.fzv.glucowatch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.TextView;
import android.widget.Toast;

import com.fzv.glucowatch.Utils.WearableUtils;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {

    private PendingIntent pendingIntent;
    private PendingIntent pendingIntent2;
    private PendingIntent pendingIntent3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testNotification();

        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);*/
        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity

            startActivity(new Intent(MainActivity.this, dodajanje_podatkov_uporabnika.class));
        }
        else
        {
            DB_Handler dbhandler = new DB_Handler(this, null, null, 1);
            CasObrokov [] c = dbhandler.vrniCaseObrokov();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

            String cas1 = "07:36";
            String cas2 = "07:37";
            String cas3 = "07:38";

            /*try
            {
                cas1 = c[0].getCasObroka();
                cas2 = c[1].getCasObroka();
                cas3 = c[2].getCasObroka();
            }
            catch(Exception ex)
            {
                Toast.makeText(getApplicationContext(), "Napaka pri branju!!!" + ex.toString(),
                        Toast.LENGTH_LONG).show();
            }*/
            Intent myIntent;
            AlarmManager alarmManager;

            Intent myIntent2;
            AlarmManager alarmManager2;

            Intent myIntent3;
            AlarmManager alarmManager3;


            Integer minute;
            Integer ure;
            try {

                if(cas1.length() == 5)//Ker ne moremo parsati npr števila 05:...
                {
                    ure = Integer.parseInt(cas1.substring(0,2));
                    minute = Integer.parseInt(cas1.substring(3));
                }
                else
                {
                    ure = Integer.parseInt(cas1.substring(0,1));
                    minute = Integer.parseInt(cas1.substring(2));
                }

                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.HOUR_OF_DAY, ure);
                calendar.set(Calendar.SECOND, 2);

                myIntent = new Intent(MainActivity.this, MyReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);


                /****************************/

                if(cas1.length() == 5)//Ker ne moremo parsati npr števila 05:...
                {
                    ure = Integer.parseInt(cas1.substring(0,2));
                    minute = Integer.parseInt(cas1.substring(3));
                }
                else
                {
                    ure = Integer.parseInt(cas1.substring(0,1));
                    minute = Integer.parseInt(cas1.substring(2));
                }
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.HOUR_OF_DAY, ure);
                calendar.set(Calendar.SECOND, 2);

                myIntent2 = new Intent(MainActivity.this, MyReceiver2.class);
                pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent2, 0);

                alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager2.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent2);
                /************************/

                if(cas1.length() == 5)//Ker ne moremo parsati npr števila 05:...
                {
                    ure = Integer.parseInt(cas1.substring(0,2));
                    minute = Integer.parseInt(cas1.substring(3));
                }
                else
                {
                    ure = Integer.parseInt(cas1.substring(0,1));
                    minute = Integer.parseInt(cas1.substring(2));
                }
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.HOUR_OF_DAY, ure);
                calendar.set(Calendar.SECOND, 2);

                myIntent3 = new Intent(MainActivity.this, MyReceiver3.class);
                pendingIntent3 = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent3, 0);

                alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager3.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent3);



            Toast.makeText(getApplicationContext(), "Servis zagnan!",
                    Toast.LENGTH_SHORT).show();
/*
            t1.setText(minute.toString());
            t2.setText(ure.toString());*/
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(), "Napaka pri zagonu servisa!" + ex.toString(),
                        Toast.LENGTH_LONG).show();
            }
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();





        /*calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 44 );
        calendar.set(Calendar.SECOND, 0);*/
        /*calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);*/

    }


    private void testNotification() {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void activityDodajAktivnost(View v)
    {
        startActivity(new Intent(MainActivity.this, DodajanjeAktivnosti.class) );
    }
    public void activityDodajObrok(View v)
    {
        startActivity(new Intent(MainActivity.this, DodajanjeObroka.class) );
    }

    public void activityDodajMeritev(View v)
    {
        startActivity(new Intent(MainActivity.this, dodajanjeMeritve.class) );
    }

    public void activityDodajVnosZdravila(View v)
    {
        startActivity(new Intent(MainActivity.this, AktivnostVnosZdravil.class) );
    }
    public void activityPodatkiUporabnika(View v)
    {
        startActivity(new Intent(MainActivity.this, dodajanje_podatkov_uporabnika.class) );
    }

    public void activityStatistika(View v)
    {
        startActivity(new Intent(MainActivity.this, Statistika.class) );
    }
}
