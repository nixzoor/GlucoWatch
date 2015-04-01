package com.fzv.glucowatch;

import java.text.DateFormat;

/**
 * Created by Vinko on 30.3.2015.
 */
public class Meritev {
    String casMeritve;
    Double vrednostGlukoze;

    public Meritev() {
    }

    public Meritev(String casMeritve, Double vrednostGlukoze) {
        this.casMeritve = casMeritve;
        this.vrednostGlukoze = vrednostGlukoze;
    }

    public String getCasMeritve() {
        return casMeritve;
    }

    public void setCasMeritve(String casMeritve) {
        this.casMeritve = casMeritve;
    }

    public Double getVrednostGlukoze() {
        return vrednostGlukoze;
    }

    public void setVrednostGlukoze(Double vrednostGlukoze) {
        this.vrednostGlukoze = vrednostGlukoze;
    }
}
