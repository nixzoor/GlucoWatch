package com.fzv.glucowatch;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vinko on 30.3.2015.
 */
public class DB_Handler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "podatkovnaBaza.db";

    //Imena tabel
    private static final String TABLE_AKTIVNOST = "Aktivnosti";
    private static final String TABLE_MERITEV = "Meritev";
    private static final String TABLE_OBROK = "Obrok";
    private static final String TABLE_VNOS_ZDRAVILA = "VnosZdravila";
    private static final String TABLE_UPORABNIK = "podatkiUporabnik";
    private static final String TABLE_CAS_OBROKOV = "TabelaCasObrokov";


    //Spremenljivke tabele Aktivnosti
    public static final String COLUMN_INTENZIVNOST = "Intenzivnost";
    public static final String COLUMN_TRAJANJE = "Trajanje";
    public static final String COLUMN_VRSTA = "Vrsta";


    //Spremenljivke tabele Meritev
    public static final String COLUMN_CAS_MERITVE = "CasMeritve";
    public static final String COLUMN_VREDNOST_GLUKOZE = "VrednostGlukoze";

    //Spremenljivke tabele Obrok
    public static final String COLUMN_DATUM_CAS = "DatumCas";
    public static final String COLUMN_VELIKOST = "Velikost";
    public static final String COLUMN_PROCENT_OH = "ProcentOH";

    //Spremenljivke tabele VnosZdravila
    public static final String COLUMN_ZDRAVILO = "Zdravilo";
    public static final String COLUMN_ODMEREK = "Odmerek";
    public static final String COLUMN_DATUM_CAS_VNOSA = "DatumInCas";

    public DB_Handler(Context context, String name,
                      SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        //Za tabelo Aktivnosti
        String CREATE_Aktivnost_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_AKTIVNOST + "("
                + "ID_Aktivnosti INTEGER PRIMARY KEY," + COLUMN_VRSTA
                + " TEXT," + COLUMN_TRAJANJE + " NUMBER," + COLUMN_INTENZIVNOST + " TEXT"+ ")";
        db.execSQL(CREATE_Aktivnost_TABLE);

        //Ustvarjanje tabele MEritev
        String CREATE_Meritev_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_MERITEV + "("
                + "ID_Meritve INTEGER PRIMARY KEY," + COLUMN_CAS_MERITVE
                + " TEXT," + COLUMN_VREDNOST_GLUKOZE + " NUMBER" + ")";
        db.execSQL(CREATE_Meritev_TABLE);

        //Za tabelo obrok
        String CREATE_Obrok_TABLE = "CREATE TABLE " +
                TABLE_OBROK + "("
                + "ID_Obroka INTEGER PRIMARY KEY," + COLUMN_DATUM_CAS
                + " DATETIME," + COLUMN_VELIKOST + " TEXT," + COLUMN_PROCENT_OH + " NUMBER"+ ")";
        db.execSQL(CREATE_Obrok_TABLE);

        //Za tabelo vnosZdravila
        String CREATE_VnosZDravila_TABLE = "CREATE TABLE " +
                TABLE_VNOS_ZDRAVILA + "("
                + "ID_Vnosa INTEGER PRIMARY KEY," + COLUMN_ZDRAVILO + " TEXT," +
                COLUMN_ODMEREK + " NUMBER," + COLUMN_DATUM_CAS_VNOSA + " DATETIME " + ")";
        db.execSQL(CREATE_VnosZDravila_TABLE);

        //Za tabelo s podatki uporabnika
        String CREATE_podatkiUporabnika_TABLE = "CREATE TABLE " + TABLE_UPORABNIK + "("
                + "ID_uporabnika INTEGER PRIMARY KEY, Ime TEXT, Priimek TEXT, Datum_rojstva TEXT,"
                +" Spol TEXT, Visina NUMBER, Teza NUMBER, Status TEXT, Tip_bolezni TEXT, Osnovno_zdravilo TEXT )";
        db.execSQL(CREATE_podatkiUporabnika_TABLE);

        //Za tabelo cas obrokov
        String CREATE_Cas_obrokov_TABLE = "CREATE TABLE " + TABLE_CAS_OBROKOV + "("+
                "ID_casa_obroka INTEGER PRIMARY KEY, Cas TEXT, Vrsta_obroka TEXT )";
        db.execSQL(CREATE_Cas_obrokov_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AKTIVNOST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MERITEV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBROK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VNOS_ZDRAVILA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UPORABNIK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAS_OBROKOV);
        onCreate(db);
    }

    //Metode za tabelo AKTIVNOSTI
    public void dodajAktivnost(Aktivnost aktivnost)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_VRSTA, aktivnost.getVrsta());
        values.put(COLUMN_TRAJANJE, aktivnost.getTrajanje());
        values.put(COLUMN_INTENZIVNOST, aktivnost.getIntenzivnost());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_AKTIVNOST, null, values);
        db.close();
    }

    public Aktivnost[] vrniVseAktivnost()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * FROM " + TABLE_AKTIVNOST, null);

        Aktivnost[] vseAktivnosti = new Aktivnost[c.getCount()];
        if(c.getCount()==0){
            return vseAktivnosti;
        }


        Aktivnost a;
        Integer indeks = 0;
        while (c.moveToNext())
        {
            a = new Aktivnost();

            a.setVrsta(c.getString(1));
            a.setTrajanje(Integer.parseInt(c.getString(2)));
            a.setIntenzivnost(c.getString(3));

            vseAktivnosti[indeks] = a;
            indeks++;
        }

        return  vseAktivnosti;
    }

    //Metode za tabelo MERITEV

    public void dodajMeritev(Meritev meritev)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CAS_MERITVE, meritev.getCasMeritve());
        values.put(COLUMN_VREDNOST_GLUKOZE, meritev.getVrednostGlukoze().toString());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MERITEV, null, values);
        db.close();
    }

    public Meritev[] vrniVseMeritve()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("Select * FROM " + TABLE_MERITEV, null);
        Meritev[] vseMeritve = new Meritev[c.getCount()];

        if(c.getCount()==0)return vseMeritve;

        Meritev m;
        Integer indeks = 0;
        while(c.moveToNext())
        {
            m = new Meritev();

            m.setCasMeritve(c.getString(1));
            m.setVrednostGlukoze(Double.parseDouble(c.getString(2)));

            vseMeritve[indeks] = m;
            indeks++;
        }
        return vseMeritve;
    }


    //Metode za tabelo OBROK
    public void dodajObrok(Obrok obrok)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATUM_CAS, obrok.getDatumCas());
        values.put(COLUMN_VELIKOST, obrok.getVelikostObroka());
        values.put(COLUMN_PROCENT_OH, obrok.getOdstotekOH());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_OBROK, null, values);
        db.close();
    }

    public Obrok[] vrniVseObroke()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("Select * FROM " + TABLE_OBROK, null);
        Obrok[] vsiObroki = new Obrok[c.getCount()];

        if(c.getCount()==0)return vsiObroki;

        Obrok o;
        Integer indeks = 0;
        while(c.moveToNext())
        {
            o = new Obrok();

            o.setDatumCas(c.getString(1));
            o.setVelikostObroka(c.getString(2));
            o.setOdstotekOH((Integer.parseInt(c.getString(3))));

            vsiObroki[indeks] = o;
            indeks++;
        }
        return vsiObroki;
    }

    //Metode za tabelo VnosZdravila

    public void dodajVnosZdravila(VnosZdravila vnos)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ZDRAVILO, vnos.getZdravilo());
        values.put(COLUMN_ODMEREK, vnos.getOdmerek().toString());
        values.put(COLUMN_DATUM_CAS_VNOSA, vnos.getCasVnosa());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_VNOS_ZDRAVILA, null, values);
        db.close();
    }

    public VnosZdravila[] vrniVseVnoseZdravil()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * FROM " + TABLE_VNOS_ZDRAVILA, null);

        VnosZdravila [] vnosi = new VnosZdravila[c.getCount()];

        if(c.getCount()==0){
            return vnosi;
        }


        VnosZdravila a;
        Integer indeks = 0;
        while (c.moveToNext())
        {
            a = new VnosZdravila();

            a.setZdravilo(c.getString(1));
            a.setOdmerek(Integer.parseInt(c.getString(2)));

            a.setCasVnosa(c.getString(3));

            vnosi[indeks] = a;
            indeks++;
        }

        return  vnosi;
    }

    //Metode za podatke o uporabniku
    public void dodajPodatkeUporabnika(Uporabnik uporabnik)
    {
        ContentValues values = new ContentValues();
        values.put("Ime", uporabnik.getIme());
        values.put("Priimek", uporabnik.getPriimek());
        values.put("Datum_rojstva" , uporabnik.getDatumRojstva());
        values.put("Spol", uporabnik.getSpol());
        values.put("Visina", uporabnik.getVisina().toString());
        values.put("Teza", uporabnik.getTeza().toString());
        values.put("Status", uporabnik.getZapislitveniStatus());
        values.put("Tip_bolezni", uporabnik.getTipBolezni());
        values.put("Osnovno_zdravilo", uporabnik.getOsnovnoZdravilo());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_UPORABNIK, null, values);
        db.close();
    }

    public Uporabnik vrniPodatkeUporabnika()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * FROM " + TABLE_UPORABNIK, null);

        Uporabnik u = new Uporabnik();

        if(c.getCount()==0){
            return u; //Ne vrne podatkov, ker še niso vpisani
        }

        while (c.moveToNext())
        {
            u.setIme(c.getString(1));
            u.setPriimek(c.getString(2));
            u.setDatumRojstva(c.getString(3));
            u.setSpol(c.getString(4));
            u.setVisina(Double.parseDouble(c.getString(5)));
            u.setTeza(Double.parseDouble(c.getString(6)));
            u.setZapislitveniStatus(c.getString(7));
            u.setTipBolezni(c.getString(8));
            u.setOsnovnoZdravilo(c.getString(9));
        }

        return  u;
    }

    //Metode za Case obrokov, ki spadajo k uporabniku
    public void dodajCaseObrokov(CasObrokov casObrokov)
    {
        ContentValues values = new ContentValues();
        values.put("Cas", casObrokov.getCasObroka());
        values.put("Vrsta_obroka", casObrokov.getVrstaObroka());


        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_CAS_OBROKOV, null, values);
        db.close();
    }

    public void BrisiCaseObrokov()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CAS_OBROKOV);
    }
    public CasObrokov[] vrniCaseObrokov()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("Select * FROM " + TABLE_CAS_OBROKOV, null);

        CasObrokov[] casObrokov = new CasObrokov[c.getCount()];

        if(c.getCount()==0){
            return casObrokov;
        }


        CasObrokov obrok;
        Integer indeks = 0;

        while (c.moveToNext())
        {
            obrok = new CasObrokov();

            obrok.setCasObroka(c.getString(1));
            obrok.setVrstaObroka(c.getString(2));

            casObrokov[indeks] = obrok;
            indeks++;
        }

        return  casObrokov;
    }




}
