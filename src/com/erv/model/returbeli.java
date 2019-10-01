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
public class returbeli {
    private int ID;
    private String KODERETURBELI;
    private String TANGGAL;
    private String IDSUPPLIER;
    private String KETERANGAN;
    private int IDPEMBELIAN;
    private int STATUS;
    private double TOTALRETUR;
    private double TOTALDISKON;
    private double TOTALPPN;
    public returbeli() {
        this.STATUS=0;
        this.TOTALRETUR=0;
        this.TOTALDISKON=0;
        this.TOTALPPN=0;
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

    public String getKETERANGAN() {
        return KETERANGAN;
    }

    public void setKETERANGAN(String KETERANGAN) {
        this.KETERANGAN = KETERANGAN;
    }

    public String getKODERETURBELI() {
        return KODERETURBELI;
    }

    public void setKODERETURBELI(String KODERETURBELI) {
        this.KODERETURBELI = KODERETURBELI;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }

    public int getIDPEMBELIAN() {
        return IDPEMBELIAN;
    }

    public void setIDPEMBELIAN(int IDPEMBELIAN) {
        this.IDPEMBELIAN = IDPEMBELIAN;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public double getTOTALRETUR() {
        return TOTALRETUR;
    }

    public void setTOTALRETUR(double TOTALRETUR) {
        this.TOTALRETUR = TOTALRETUR;
    }

    public double getTOTALDISKON() {
        return TOTALDISKON;
    }

    public void setTOTALDISKON(double TOTALDISKON) {
        this.TOTALDISKON = TOTALDISKON;
    }

    public double getTOTALPPN() {
        return TOTALPPN;
    }

    public void setTOTALPPN(double TOTALPPN) {
        this.TOTALPPN = TOTALPPN;
    }
    
    
   
}
