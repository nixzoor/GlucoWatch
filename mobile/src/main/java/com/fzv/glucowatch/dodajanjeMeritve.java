package com.fzv.glucowatch;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.Object;


public class dodajanjeMeritve extends ActionBarActivity {

    EditText vrednostGlukoze;

    TextView txtVse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodajanje_meritve);
        vrednostGlukoze = (EditText)findViewById(R.id.editTextVrednstGlukoze);
        txtVse = (TextView)findViewById(R.id.textViewVseMeritve);

        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();
        String text = "MERITVE: \n \n";
        for(Integer i = 0; i < vseMeritve.length; i++)
        {
            text += vseMeritve[i].getCasMeritve() +" - Vrednost: " + vseMeritve[i].getVrednostGlukoze().toString() + "\n";
        }
        txtVse.setText(text);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_dodajanje_meritve, menu);
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

    public void dodajMeritev(View view)
    {
        DB_Handler dbHandler = new DB_Handler(this, null, null, 1);


        Date d = new Date();
        SimpleDateFormat datumformat = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        String datum = datumformat.format(d);

        try {
            Meritev m = new Meritev(datum, Double.parseDouble(vrednostGlukoze.getText().toString()));
            Toast.makeText(getApplicationContext(), "Meritev dodana",
                    Toast.LENGTH_SHORT).show();
            vrednostGlukoze.setText("");
            dbHandler.dodajMeritev(m);
        }
        catch(Exception ex)
        {
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setMessage("Pri vnosu je prišlo do napake. \nPreglejte podatke in poizkusite znova.\nDecimalna Števila zapisujte s piko");
            dlgAlert.setTitle("Napaka pri vnosu");
            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //dismiss the dialog
                        }
                    });
            dlgAlert.create().show();
        }
        Meritev [] vseMeritve = dbHandler.vrniVseMeritve();
        String text = "MERITVE: \n \n";

        for(Integer i = 0; i < vseMeritve.length; i++)
        {
            text += vseMeritve[i].getCasMeritve() +" - Vrednost: " + vseMeritve[i].getVrednostGlukoze().toString() + "\n";
        }

        txtVse.setText(text);

    }

    public void activityNapovedniModelClick(View view)
    {
        startActivity(new Intent(dodajanjeMeritve.this, napovedni_model.class) );
    }
}
