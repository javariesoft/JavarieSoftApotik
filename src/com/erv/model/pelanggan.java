/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;

import java.sql.Date;

/**
 *
 * @author erwadi
 */
public class pelanggan {
    private String KODEPELANGGAN;
    private String NAMA;
    private String ALAMAT;
    private String HP;
    //private Date TGLREG;
    private String TGLREG;
    private float BATASKREDIT;
    private String KODEAKUN;
    private String NPWP;
    private String STATUSCABANG;
    private String NAMAPEMILIK;
    private int STATUSAKTIF;
    
    public pelanggan() {
        this.KODEPELANGGAN = "";
        this.NAMA = "";
        this.ALAMAT = "";
        this.HP = "";
        //this.TGLREG = java.sql.Date.valueOf(com.erv.function.Util.toDateStringSql(new java.util.Date()));
        this.TGLREG = "";
        this.BATASKREDIT = 0;
        this.KODEAKUN = "";
        this.NPWP="";
        this.STATUSCABANG="";
        this.NAMAPEMILIK="";
        this.STATUSAKTIF=0;
    }

    public String getKODEPELANGGAN() {
        return KODEPELANGGAN;
    }

    public void setKODEPELANGGAN(String KODEPELANGGAN) {
        this.KODEPELANGGAN = KODEPELANGGAN;
    }

    public String getNAMA() {
        return NAMA;
    }

    public void setNAMA(String NAMA) {
        this.NAMA = NAMA;
    }

    public String getALAMAT() {
        return ALAMAT;
    }

    public void setALAMAT(String ALAMAT) {
        this.ALAMAT = ALAMAT;
    }

    public String getHP() {
        return HP;
    }

    public void setHP(String HP) {
        this.HP = HP;
    }

    public String getTGLREG() {
        return TGLREG;
    }

    public void setTGLREG(String TGLREG) {
        this.TGLREG = TGLREG;
    }

    public float getBATASKREDIT() {
        return BATASKREDIT;
    }

    public void setBATASKREDIT(float BATASKREDIT) {
        this.BATASKREDIT = BATASKREDIT;
    }

    public String getKODEAKUN() {
        return KODEAKUN;
    }

    public void setKODEAKUN(String KODEAKUN) {
        this.KODEAKUN = KODEAKUN;
    }

    public String getNPWP() {
        return NPWP;
    }

    public void setNPWP(String NPWP) {
        this.NPWP = NPWP;
    }

    public String getSTATUSCABANG() {
        return STATUSCABANG;
    }

    public void setSTATUSCABANG(String STATUSCABANG) {
        this.STATUSCABANG = STATUSCABANG;
    }

    public String getNAMAPEMILIK() {
        return NAMAPEMILIK;
    }

    public void setNAMAPEMILIK(String NAMAPEMILIK) {
        this.NAMAPEMILIK = NAMAPEMILIK;
    }

    public int getSTATUSAKTIF() {
        return STATUSAKTIF;
    }

    public void setSTATUSAKTIF(int STATUSAKTIF) {
        this.STATUSAKTIF = STATUSAKTIF;
    }

}
