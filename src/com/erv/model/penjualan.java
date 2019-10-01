/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.model;
import java.io.Serializable;

/**
 *
 * @author erwadi
 */
public class penjualan implements Serializable {
    private int ID;
    private String FAKTUR;
    private String TANGGAL;
    private String KODEPELANGGAN;
    private String CASH;
    private String TGLLUNAS;
    private double PPN;
    private double DP;
    private double DISKON;
    private String STATUS;
    private String IDSALES;
    private double TAMBAHANTOTAL;
    private int IDBANK;
    private String PELANGGAN;
    private String JENISTRANS;
    private String STATUSDO;
    public penjualan() {
        this.ID = 0;
        this.FAKTUR = "";
        this.KODEPELANGGAN = "";
        this.CASH = "0";
        this.PPN = 0f;
        this.DP = 0f;
        this.DISKON = 0f;
        this.STATUS = "0";
        this.IDSALES= "0";
        this.TAMBAHANTOTAL = 0f;
        this.PELANGGAN="";
        this.JENISTRANS = "";
        //this.IDDO=0;
    }
    public String getCASH() {
        return CASH;
    }

    public void setCASH(String CASH) {
        this.CASH = CASH;
    }

    public String getFAKTUR() {
        return FAKTUR;
    }

    public void setFAKTUR(String FAKTUR) {
        this.FAKTUR = FAKTUR;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getKODEPELANGGAN() {
        return KODEPELANGGAN;
    }

    public void setKODEPELANGGAN(String KODEPELANGGAN) {
        this.KODEPELANGGAN = KODEPELANGGAN;
    }

    public double getPPN() {
        return PPN;
    }

    public void setTAMBAHANTOTAL(double TAMBAHANTOTAL) {
        this.TAMBAHANTOTAL = TAMBAHANTOTAL;
    }
    
    public double getTAMBAHANTOTAL() {
        return TAMBAHANTOTAL;
    }

    public void setPPN(double PPN) {
        this.PPN = PPN;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public String getTGLLUNAS() {
        return TGLLUNAS;
    }

    public void setTGLLUNAS(String TGLLUNAS) {
        this.TGLLUNAS = TGLLUNAS;
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

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getIDSALES() {
        return IDSALES;
    }

    public void setIDSALES(String IDSALES) {
        this.IDSALES = IDSALES;
    }

//    public int getIDDO() {
//        return IDDO;
//    }
//
//    public void setIDDO(int IDDO) {
//        this.IDDO = IDDO;
//    }
    public int getIDBANK() {
        return IDBANK;
    }

    public void setIDBANK(int IDBANK) {
        this.IDBANK = IDBANK;
    }

    public String getPELANGGAN() {
        return PELANGGAN;
    }

    public void setPELANGGAN(String PELANGGAN) {
        this.PELANGGAN = PELANGGAN;
    }

    public String getJENISTRANS() {
        return JENISTRANS;
    }

    public void setJENISTRANS(String JENISTRANS) {
        this.JENISTRANS = JENISTRANS;
    }

    public String getSTATUSDO() {
        return STATUSDO;
    }

    public void setSTATUSDO(String STATUSDO) {
        this.STATUSDO = STATUSDO;
    }
    
    

    
}
