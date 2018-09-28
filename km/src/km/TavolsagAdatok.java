/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package km;


/**
 *
 * @author kelement
 */

public class TavolsagAdatok {
    private String varos;
    private int tavolsag;

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public void setTavolsag(int tavolsag) {
        this.tavolsag = tavolsag;
    }

    public TavolsagAdatok(String varos, int tavolsag) {
        this.varos = varos;
        this.tavolsag = tavolsag;
    }

    public String getVaros() {
        return varos;
    }

    public int getTavolsag() {
        return tavolsag;
    }
    
}