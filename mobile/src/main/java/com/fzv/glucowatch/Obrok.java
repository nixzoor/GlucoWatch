package com.fzv.glucowatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Vinko on 27.3.2015.
 */
public class Obrok {
    String datumCas;
    String velikostObroka;
    Integer odstotekOH;

    public Obrok(String datumCas, String velikostObroka, Integer odstotekOH) {
        this.datumCas = datumCas;
        this.velikostObroka = velikostObroka;
        this.odstotekOH = odstotekOH;
    }

    public Obrok() {
    }

    public String getDatumCas() {
        return datumCas;
    }

    public void setDatumCas(String datumCas) {
        this.datumCas = datumCas;
    }

    public String getVelikostObroka() {
        return velikostObroka;
    }

    public void setVelikostObroka(String velikostObroka) {
        this.velikostObroka = velikostObroka;
    }

    public Integer getOdstotekOH() {
        return odstotekOH;
    }

    public void setOdstotekOH(Integer odstotekOH) {
        this.odstotekOH = odstotekOH;
    }
}