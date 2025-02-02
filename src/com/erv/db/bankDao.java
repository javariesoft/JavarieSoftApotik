/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.bank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author erwadi
 */
public class bankDao {

    public static boolean insertIntoBANK(Connection con, bank b) throws SQLException {
        
        String sql = "INSERT INTO BANK "
                + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, b.getIDBANK());
        statement.setString(2, b.getNAMABANK());
        statement.setString(3, b.getKODEAKUN());
        statement.setString(4, b.getNAMAPENABUNG());
        statement.setString(5, b.getNOREK());
        boolean h = statement.execute();
        statement.close();
        return !h;
    }

    public static boolean updateBANK(Connection con, bank b) throws SQLException {
        String sql = "SELECT * FROM BANK WHERE IDBANK = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, b.getIDBANK());
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

        if (b.getNAMABANK() != null) {
            entry.updateString("NAMABANK", b.getNAMABANK());
        }
        if (b.getKODEAKUN() != null) {
            entry.updateString("KODEAKUN", b.getKODEAKUN());
        }
        if (b.getNAMAPENABUNG() != null) {
            entry.updateString("NAMAPENABUNG", b.getNAMAPENABUNG());
        }
        if (b.getNOREK() != null) {
            entry.updateString("NOREK", b.getNOREK());
        }

        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromBANK(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM BANK WHERE IDBANK = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
    }
    public static int getID(Connection con) throws SQLException{
        int hasil=1;
        PreparedStatement pstmt = con.prepareStatement("select max(IDBANK) from BANK");
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            if(rs.getString(1)!=null){
                hasil=rs.getInt(1)+1;
            }
        }
        rs.close();
        pstmt.close();
        return hasil;
    }
    public static String getAkun(Connection con){
        String hasil="";
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        try {
            String kode = settingDao.getAkun(con, "BANK");
            hasil=kode+"."+getID(con);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return hasil;
    }
     public static bank getDetails(Connection con, int id)throws SQLException, ClassNotFoundException
    {
       //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from BANK where IDBANK=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        bank ubean = new bank();
        while(rs.next())
        {
            ubean.setIDBANK(rs.getInt(1));
            ubean.setNAMABANK(rs.getString(2));
            ubean.setKODEAKUN(rs.getString(3));
            ubean.setNAMAPENABUNG(rs.getString(4));
            ubean.setNOREK(rs.getString(5));
        }
        rs.close();
        pstmt.close();
        return ubean;
    }
     
     public static List getAlldetails(Connection con)throws SQLException
    {
        PreparedStatement pstmt = con.prepareStatement("select * from bank");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while(rs.next())
        {
            bank ubean = new bank();
            ubean.setIDBANK(rs.getInt(1));
            ubean.setNAMABANK(rs.getString(2));
            ubean.setKODEAKUN(rs.getString(3));
            ubean.setNAMAPENABUNG(rs.getString(4));
            ubean.setNOREK(rs.getString(5));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();
        return list;
    }
}
