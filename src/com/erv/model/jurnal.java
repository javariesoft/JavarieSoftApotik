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
public class jurnal {
    private int ID;
    private String KODEJURNAL;
    //private Date TANGGAL;
    private String TANGGAL;
    private String DESKRIPSI;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDESKRIPSI() {
        return DESKRIPSI;
    }

    public void setDESKRIPSI(String DESKRIPSI) {
        this.DESKRIPSI = DESKRIPSI;
    }

    public String getKODEJURNAL() {
        return KODEJURNAL;
    }

    public void setKODEJURNAL(String KODEJURNAL) {
        this.KODEJURNAL = KODEJURNAL;
    }

    public String getTANGGAL() {
        return TANGGAL;
    }

    public void setTANGGAL(String TANGGAL) {
        this.TANGGAL = TANGGAL;
    }
    
}
