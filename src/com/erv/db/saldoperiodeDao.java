/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.saldoperiode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erwadi
 */
public class saldoperiodeDao {

    public static boolean insertIntoSALDOPERIODE(Connection con, saldoperiode s) throws SQLException {
        String sql = "INSERT INTO SALDOPERIODE "
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, s.getPERIODE());
        statement.setString(2, s.getKODEAKUN());
        statement.setDouble(3, s.getSALDO());
        statement.setInt(4, s.getGRUP());
        boolean h = statement.execute();
        statement.close();
        return !h;
    }

    public static boolean updateSALDOPERIODE(Connection con, saldoperiode s) throws SQLException {
        String sql = "SELECT * FROM SALDOPERIODE WHERE PERIODE = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setString(1, s.getPERIODE());
        ResultSet entry = statement.executeQuery();
        entry.last();
        int rows = entry.getRow();
        entry.beforeFirst();
        if (rows == 0) {
            entry.close();
            statement.close();
            return false;
        }
        entry.next();
        if (s.getKODEAKUN() != null) {
            entry.updateString("KODEAKUN", s.getKODEAKUN());
        }
        entry.updateDouble("SALDO", s.getSALDO());

        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromSALDOPERIODE(Connection con, String keyId) throws SQLException {
        String sql = "DELETE FROM SALDOPERIODE WHERE PERIODE = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, keyId);
        statement.executeUpdate();
        statement.close();
    }
    public static void deleteFromSALDOPERIODEAKUN(Connection con, String PERIODE, String AKUN) throws SQLException {
        String sql = "DELETE FROM SALDOPERIODE WHERE PERIODE = ? AND KODEAKUN = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, PERIODE);
        statement.setString(2, AKUN);
        statement.executeUpdate();
        statement.close();
    }
    public static double getDebet(Connection c, String kodeakun,int bulan, int tahun){
        double hasil=0;
        String sql="SELECT sum(RINCIJURNAL.DEBET) AS DEBET "
                + "FROM JURNAL INNER JOIN RINCIJURNAL ON JURNAL.ID = RINCIJURNAL.KODEJURNAL "
                + "WHERE RINCIJURNAL.KODEPERKIRAAN LIKE '"+ kodeakun +"' AND MONTH(JURNAL.TANGGAL) = "+bulan+" AND YEAR(JURNAL.TANGGAL) = "+tahun+"";
        
        try {
            Statement stat=c.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            if(rs.next()){
                hasil=rs.getDouble(1);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(saldoperiodeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasil;
    }
    public static double getKredit(Connection c, String kodeakun,int bulan, int tahun){
        double hasil=0;
        String sql="SELECT sum(RINCIJURNAL.KREDIT) AS KREDIT "
                + "FROM JURNAL INNER JOIN RINCIJURNAL ON JURNAL.ID = RINCIJURNAL.KODEJURNAL "
                + "WHERE RINCIJURNAL.KODEPERKIRAAN LIKE '"+ kodeakun +"' AND MONTH(JURNAL.TANGGAL) = "+bulan+" AND YEAR(JURNAL.TANGGAL) = "+tahun+"";
        try {
            Statement stat=c.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            if(rs.next()){
                hasil=rs.getDouble(1);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(saldoperiodeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasil;
    }
    public static double getSaldo(Connection c, String kodeakun,int bulan, int tahun){
        double hasil=0;
        String sql="SELECT SALDO FROM SALDOPERIODE WHERE KODEAKUN='"+kodeakun+"' AND PERIODE = '"+(tahun+"."+bulan)+"'";
        try {
            Statement stat=c.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            if(rs.next()){
                hasil=rs.getDouble(1);
            }
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            Logger.getLogger(saldoperiodeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasil;
    }
    public static boolean cekPeriode(Connection c, String periode){
        boolean hasil=false;
        try {
            Statement stat=c.createStatement();
            ResultSet rs=stat.executeQuery("select * from SALDOPERIODE where PERIODE='"+periode+"'");
            if (rs.next()){
                if(rs.getString(1)!=null){
                hasil=true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(saldoperiodeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasil;
    }
    public static saldoperiode getDetails(Connection con, String periode,String kodeakun)throws SQLException, ClassNotFoundException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from SALDOPERIODE where PERIODE=? and KODEAKUN=?");
        pstmt.setString(1, periode);
        pstmt.setString(2, kodeakun);
        ResultSet rs = pstmt.executeQuery();
        saldoperiode ubean = new saldoperiode();
        while(rs.next())
        {
            ubean.setPERIODE(rs.getString(1));
            ubean.setKODEAKUN(rs.getString(2));
            ubean.setSALDO(rs.getDouble(3));
            ubean.setGRUP(rs.getInt(4));
        }
        return ubean;
    }
}
