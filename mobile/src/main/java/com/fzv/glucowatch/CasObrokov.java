package com.fzv.glucowatch;

/**
 * Created by Vinko on 4.4.2015.
 */
public class CasObrokov {
    String CasObroka;
    String VrstaObroka;

    public CasObrokov(String casObroka, String vrstaObroka) {
        CasObroka = casObroka;
        VrstaObroka = vrstaObroka;
    }

    public CasObrokov() {
    }

    public String getCasObroka() {
        return CasObroka;
    }

    public void setCasObroka(String casObroka) {
        CasObroka = casObroka;
    }

    public String getVrstaObroka() {
        return VrstaObroka;
    }

    public void setVrstaObroka(String vrstaObroka) {
        VrstaObroka = vrstaObroka;
    }
}
