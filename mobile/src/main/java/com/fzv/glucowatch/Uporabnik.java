package com.fzv.glucowatch;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by Vinko on 27.3.2015.
 */
public class Uporabnik {
    String ime;
    String priimek;
    DateFormat datumRojstva;
    String spol;
    Double visina;
    Double teza;

    String TipBolezni;

    String zapislitveniStatus;
    DateFormat[] predvideniObrokiTeden;
    DateFormat[] predvideniObrokiVikend;


}
