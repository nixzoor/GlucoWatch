package com.fzv.glucowatch;

/**
 * Created by Vinko on 26.3.2015.
 */
public class Aktivnost {

    private String intenzivnost;
    private String vrsta;
    private int trajanje;

    public Aktivnost(String vrsta, int trajanje, String intenzivnost) {
        this.intenzivnost = intenzivnost;
        this.vrsta = vrsta;
        this.trajanje = trajanje;
    }

    public Aktivnost() {

    }

    public String getIntenzivnost() {
        return intenzivnost;
    }

    public void setIntenzivnost(String intenzivnost) {
        this.intenzivnost = intenzivnost;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public void setTrajanje(int trajanje) {
        this.trajanje = trajanje;
    }
}
