package com.fzv.glucowatch;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.widget.Toast;


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
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putBoolean("isFirstRun", false).commit();
            startActivity(new Intent(MainActivity.this, dodajanje_podatkov_uporabnika.class));
        }
        else
        {
            /************Zagon servisev******/
            DB_Handler dbhandler = new DB_Handler(this, null, null, 1);
            CasObrokov [] c = dbhandler.vrniCaseObrokov();

            Calendar calendar = Calendar.getInstance();
            Calendar calendar2 = Calendar.getInstance();
            Calendar calendar3 = Calendar.getInstance();


            //Pridobitev in nastavitev časa
            //Nastavljanje časov na timepickerjih!

            String[] cas = c[0].getCasObroka().split(":");
            Integer ura = Integer.parseInt(cas[0]);
            Integer minute = Integer.parseInt(cas[1]);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.HOUR_OF_DAY, ura);
            calendar.set(Calendar.SECOND, 2);


            Intent myIntent;
            AlarmManager alarmManager;


            try {



                Calendar cTemp = Calendar.getInstance();
                if(cTemp.get(Calendar.HOUR_OF_DAY) == calendar.get(Calendar.HOUR_OF_DAY) ||cTemp.get(Calendar.HOUR_OF_DAY) < calendar.get(Calendar.HOUR_OF_DAY))
                {
                    if(cTemp.get(Calendar.MINUTE) < calendar.get(Calendar.MINUTE))
                    {
                        Toast.makeText(getApplicationContext(), "Servis zagnan!",
                                Toast.LENGTH_SHORT).show();

                        myIntent = new Intent(MainActivity.this, MyReceiver.class);

                        pendingIntent = PendingIntent.getBroadcast(this,0,myIntent,0);
                        pendingIntent.cancel(); //Najprej zapremo
                        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

                        alarmManager = (AlarmManager) getSystemService(MyAlarmService.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
                    }
                }




                /****************************/


                /*Toast.makeText(getApplicationContext(), "Servis zagnan!",
                        Toast.LENGTH_SHORT).show();*/
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
