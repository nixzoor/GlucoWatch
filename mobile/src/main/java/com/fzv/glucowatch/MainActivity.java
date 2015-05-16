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
    TextView t1;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //testNotification();

        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);*/

        t1 = (TextView)findViewById(R.id.textView24);
        t2 = (TextView)findViewById(R.id.textView25);

        DB_Handler dbhandler = new DB_Handler(this, null, null, 1);
        CasObrokov [] c = dbhandler.vrniCaseObrokov();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");

        String cas1 = "20:50";
        String cas2 = "20:52";
        String cas3 = "15:55";
        Intent myIntent;
        AlarmManager alarmManager;

        Intent myIntent2;
        AlarmManager alarmManager2;


        Integer minute;
        Integer ure;
        try {
            ure = Integer.parseInt(cas1.substring(0,2));
            minute = Integer.parseInt(cas1.substring(3));
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.HOUR_OF_DAY, ure);
            calendar.set(Calendar.SECOND, 0);

            myIntent = new Intent(MainActivity.this, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            /************************/

            ure = Integer.parseInt(cas2.substring(0,2));
            minute = Integer.parseInt(cas2.substring(3));
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.HOUR_OF_DAY, ure);
            calendar.set(Calendar.SECOND, 0);

            myIntent = new Intent(MainActivity.this, MyReceiver2.class);
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);

            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            /*Toast.makeText(getApplicationContext(), "Servis zagnan!",
                    Toast.LENGTH_SHORT).show();

            t1.setText(minute.toString());
            t2.setText(ure.toString());*/
        }
        catch (Exception ex)
        {

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
        Button notificationButton = (Button) findViewById(R.id.notificationBtn);
        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WearableUtils.showNotificationWithConfirmation(getApplicationContext(),"Opozorilo","Potrebno bo nekaj pojesti.");
            }
        });
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
