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
public class UtiCel {
    private String varos;
    private int tavolsag;
    private double uzemanyagAr;
    private double utiKoltseg;
    private int korrekcio;
    
    
    public void setVaros(String varos) {
        this.varos = varos;
    }

    public void setTavolsag(int tavolsag) {
        this.tavolsag = tavolsag;
    }

    public void setKorrekcio(int korrekcio) {
        this.korrekcio = korrekcio;
        if (this.korrekcio  != 0) {
            this.tavolsag += this.korrekcio;
        }
    }

    public void setUzemanyagAr(double uzemanyagAr) {
        this.uzemanyagAr = uzemanyagAr;
    }
    

    public String getVaros() {
        return varos;
    }

    public int getTavolsag() {
        return tavolsag;
    }

    public double getUzemanyagAr() {
        return uzemanyagAr;
    }

    public int getKorrekcio() {
        return korrekcio;
    }
    
    
    
    
    

    public UtiCel(String[] varosEsTav , double uzemanyagAr, int korrekcio) {
        this.varos = varosEsTav[0];
        this.tavolsag = Integer.parseInt(varosEsTav[1]);
        this.uzemanyagAr = uzemanyagAr;
        setKorrekcio(korrekcio);
    }
    
    public double getUtiKoltseg() {
        return this.uzemanyagAr * this.tavolsag;
    }
    
    
    
    
}