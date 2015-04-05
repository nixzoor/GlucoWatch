package com.fzv.glucowatch;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Vinko on 27.3.2015.
 */
public class Uporabnik {
    String ime;
    String priimek;
    String datumRojstva;
    String spol;
    Double visina;
    Double teza;
    String zapislitveniStatus;

    String tipBolezni;
    String osnovnoZdravilo;

    public Uporabnik() {
    }

    public Uporabnik(String ime, String priimek, String datumRojstva, String spol, Double visina, Double teza, String zapislitveniStatus, String tipBolezni, String osnovnoZdravilo) {
        this.ime = ime;
        this.priimek = priimek;
        this.datumRojstva = datumRojstva;
        this.spol = spol;
        this.visina = visina;
        this.teza = teza;
        this.zapislitveniStatus = zapislitveniStatus;
        this.tipBolezni = tipBolezni;
        this.osnovnoZdravilo = osnovnoZdravilo;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getDatumRojstva() {
        return datumRojstva;
    }

    public void setDatumRojstva(String datumRojstva) {
        this.datumRojstva = datumRojstva;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public Double getVisina() {
        return visina;
    }

    public void setVisina(Double visina) {
        this.visina = visina;
    }

    public Double getTeza() {
        return teza;
    }

    public void setTeza(Double teza) {
        this.teza = teza;
    }

    public String getZapislitveniStatus() {
        return zapislitveniStatus;
    }

    public void setZapislitveniStatus(String zapislitveniStatus) {
        this.zapislitveniStatus = zapislitveniStatus;
    }

    public String getTipBolezni() {
        return tipBolezni;
    }

    public void setTipBolezni(String tipBolezni) {
        this.tipBolezni = tipBolezni;
    }

    public String getOsnovnoZdravilo() {
        return osnovnoZdravilo;
    }

    public void setOsnovnoZdravilo(String osnovnoZdravilo) {
        this.osnovnoZdravilo = osnovnoZdravilo;
    }
}
