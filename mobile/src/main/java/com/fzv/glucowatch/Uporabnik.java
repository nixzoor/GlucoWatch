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
    Double letoRojstva;
    Double mesecRojstva;
    Double danRojstva;
    String spol;
    Double visina;
    Double teza;
    String zapislitveniStatus;

    String tipBolezni;
    String osnovnoZdravilo;
    String uporabljeno_zdravilo;

    public Uporabnik() {
    }

    public Uporabnik(String ime, String priimek, String datumRojstva, Double letoRojstva,Double mesecRojstva, Double danRojstva , String spol, Double visina, Double teza, String zapislitveniStatus, String tipBolezni, String osnovnoZdravilo) {
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
    public Uporabnik(String ime, String priimek, String datumRojstva, Double letoRojstva,Double mesecRojstva, Double danRojstva , String spol, Double visina, Double teza, String zapislitveniStatus, String tipBolezni, String osnovnoZdravilo, String uporabljenoZdravio) {
        this.ime = ime;
        this.priimek = priimek;
        this.datumRojstva = datumRojstva;
        this.spol = spol;
        this.visina = visina;
        this.teza = teza;
        this.zapislitveniStatus = zapislitveniStatus;
        this.tipBolezni = tipBolezni;
        this.osnovnoZdravilo = osnovnoZdravilo;
        this.uporabljeno_zdravilo = uporabljenoZdravio;
        this.letoRojstva = letoRojstva;
        this.mesecRojstva = mesecRojstva;
        this.danRojstva = danRojstva;
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

    public Double getLetoRojstva() {
        return letoRojstva;
    }

    public void setLetoRojstva(Double letoRojstva) {
        this.letoRojstva = letoRojstva;
    }

    public Double getMesecRojstva() {
        return mesecRojstva;
    }

    public void setMesecRojstva(Double mesecRojstva) {
        this.mesecRojstva = mesecRojstva;
    }

    public Double getDanRojstva() {
        return danRojstva;
    }

    public void setDanRojstva(Double danRojstva) {
        this.danRojstva = danRojstva;
    }

    public String getUporabljeno_zdravilo() {
        return uporabljeno_zdravilo;
    }

    public void setUporabljeno_zdravilo(String uporabljeno_zdravilo) {
        this.uporabljeno_zdravilo = uporabljeno_zdravilo;
    }
}
