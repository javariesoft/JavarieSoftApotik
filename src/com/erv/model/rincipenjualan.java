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
public class rincipenjualan implements Serializable {
    private int ID;
    private int IDPENJUALAN;
    private String KODEBARANG;
    private int JUMLAH;
    private double HARGA;
    private double DISKON;
    private double TOTAL;
    private double PPN;
    private int IDDO;
    private double COGS;
    private String SATUAN;
    private int JUMLAHKECIL;
    private String KODEBATCH;
    private String EXPIRE;
    private double DISKONP;
    private String BONUS;
    
    public double getHARGA() {
        return HARGA;
    }

    public void setHARGA(double HARGA) {
        this.HARGA = HARGA;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDPENJUALAN() {
        return IDPENJUALAN;
    }

    public void setIDPENJUALAN(int IDPENJUALAN) {
        this.IDPENJUALAN = IDPENJUALAN;
    }

    public int getJUMLAH() {
        return JUMLAH;
    }

    public void setJUMLAH(int JUMLAH) {
        this.JUMLAH = JUMLAH;
    }

    public String getKODEBARANG() {
        return KODEBARANG;
    }

    public void setKODEBARANG(String KODEBARANG) {
        this.KODEBARANG = KODEBARANG;
    }

    public double getDISKON() {
        return DISKON;
    }

    public void setDISKON(double DISKON) {
        this.DISKON = DISKON;
    }

    public double getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(double TOTAL) {
        this.TOTAL = TOTAL;
    }

    public double getPPN() {
        return PPN;
    }

    public void setPPN(double PPN) {
        this.PPN = PPN;
    }

    public int getIDDO() {
        return IDDO;
    }

    public void setIDDO(int IDDO) {
        this.IDDO = IDDO;
    }

    public double getCOGS() {
        return COGS;
    }

    public void setCOGS(double COGS) {
        this.COGS = COGS;
    }

    public String getSATUAN() {
        return SATUAN;
    }

    public void setSATUAN(String SATUAN) {
        this.SATUAN = SATUAN;
    }

    public int getJUMLAHKECIL() {
        return JUMLAHKECIL;
    }

    public void setJUMLAHKECIL(int JUMLAHKECIL) {
        this.JUMLAHKECIL = JUMLAHKECIL;
    }

    public String getKODEBATCH() {
        return KODEBATCH;
    }

    public void setKODEBATCH(String KODEBATCH) {
        this.KODEBATCH = KODEBATCH;
    }

    public String getEXPIRE() {
        return EXPIRE;
    }

    public void setEXPIRE(String EXPIRE) {
        this.EXPIRE = EXPIRE;
    }

    public double getDISKONP() {
        return DISKONP;
    }

    public void setDISKONP(double DISKONP) {
        this.DISKONP = DISKONP;
    }

    public String getBONUS() {
        return BONUS;
    }

    public void setBONUS(String BONUS) {
        this.BONUS = BONUS;
    }
    
}
