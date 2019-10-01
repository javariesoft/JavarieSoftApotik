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
public class pembelian {
    private int ID;
    private String NOFAKTUR;
    private String IDSUPPLIER;
    private String CASH;
    private String TGLBAYAR;
    private double DP;
    private double PAJAK;
    private double DISKON;
    private String TANGGAL;
    private String STATUS;
    private String NOFAKTURSUPPLIER;
    private String TGLMASUK;
    public String getCASH() {
        return CASH;
    }

    public void setCASH(String CASH) {
        this.CASH = CASH;
    }

    public double getDISKON() {
        return DISKON;
    }

    public void setDISKON(double DISKON) {
        this.DISKON = DISKON;
    }

    public double getDP() {
        return DP;
    }

    public void setDP(double DP) {
        this.DP = DP;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIDSUPPLIER() {
        return IDSUPPLIER;
    }

    public void setIDSUPPLIER(String IDSUPPLIER) {
        this.IDSUPPLIER = IDSUPPLIER;
    }

    public String getNOFAKTUR() {
        return NOFAKTUR;
    }

    public void setNOFAKTUR(String NOFAKTUR) {
        this.NOFAKTUR = NOFAKTUR;
    }

    public double getPAJAK() {
        return PAJAK;
    }

    public void setPAJAK(double PAJAK) {
        this.PAJAK = PAJAK;
    }

    public String getTGLBAYAR() {
        return TGLBAYAR;
    }

    public void setTGLBAYAR(String TGLBAYAR) {
        this.TGLBAYAR = TGLBAYAR;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getNOFAKTURSUPPLIER() {
        return NOFAKTURSUPPLIER;
    }

    public void setNOFAKTURSUPPLIER(String NOFAKTURSUPPLIER) {
        this.NOFAKTURSUPPLIER = NOFAKTURSUPPLIER;
    }

    public String getTGLMASUK() {
        return TGLMASUK;
    }

    public void setTGLMASUK(String TGLMASUK) {
        this.TGLMASUK = TGLMASUK;
    }
    
}
