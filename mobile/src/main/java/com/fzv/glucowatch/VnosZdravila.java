package com.fzv.glucowatch;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vinko on 30.3.2015.
 */
public class VnosZdravila {
    String casVnosa;
    String zdravilo;
    Integer odmerek; //mg

    public VnosZdravila(String casVnosa, String zdravilo, Integer odmerek) {
        this.casVnosa = casVnosa;
        this.zdravilo = zdravilo;
        this.odmerek = odmerek;
    }

    public VnosZdravila() {
    }

    public String getCasVnosa() {
        return casVnosa;
    }

    public void setCasVnosa(String casVnosa) {
        this.casVnosa = casVnosa;
    }

    public String getZdravilo() {
        return zdravilo;
    }

    public void setZdravilo(String zdravilo) {
        this.zdravilo = zdravilo;
    }

    public Integer getOdmerek() {
        return odmerek;
    }

    public void setOdmerek(Integer odmerek) {
        this.odmerek = odmerek;
    }
}
