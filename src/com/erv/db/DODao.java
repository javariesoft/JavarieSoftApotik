/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.function.PrintfFormat;
import com.erv.function.Util;
import com.erv.model.DO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.h2.api.Trigger;

/**
 *
 * @author erwadi
 */
public class DODao {

    public static boolean insertIntoDO(Connection con, DO d) throws SQLException {
        String sql = "INSERT INTO DO "
                + "VALUES (?, ?, ?, ?, ?, ?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, d.getID());
        statement.setString(2, d.getKODEDO());
        statement.setString(3, d.getTANGGAL());
        statement.setString(4, d.getKODEPELANGGAN());
        statement.setString(5, d.getSTATUS());
        statement.setString(6, d.getTGLTUTUP());
        statement.setString(7, d.getSTATUSAKTIF());
        boolean h = statement.execute();
        statement.close();
        return !h;
    }

    public static boolean updateDO(Connection con, DO d) throws SQLException {
        String sql = "SELECT * FROM DO WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, d.getID());
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

        if (d.getKODEDO() != null) {
            entry.updateString("KODEDO", d.getKODEDO());
        }
        if (d.getTANGGAL() != null) {
            entry.updateString("TANGGAL", d.getTANGGAL());
        }
        if (d.getKODEPELANGGAN() != null) {
            entry.updateString("KODEPELANGGAN", d.getKODEPELANGGAN());
        }
        if (d.getSTATUS() != null) {
            entry.updateString("STATUS", d.getSTATUS());
        }
        if (d.getTGLTUTUP() != null) {
            entry.updateString("TGLTUTUP", d.getTGLTUTUP());
        }
        if (d.getSTATUSAKTIF() != null) {
            entry.updateString("STATUSAKTIF", d.getSTATUSAKTIF());
        }
        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromDO(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM DO WHERE ID ="+keyId;
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);   
        statement.close();
    }

    public static int getID(Connection con) throws SQLException {
        int hasil = 1;
        PreparedStatement pstmt = con.prepareStatement("select max(ID) from DO");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil = rs.getInt(1) + 1;
            }
        }
        rs.close();
        pstmt.close();
        return hasil;
    }

    public static DO getDetails(Connection con, int id) throws SQLException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from DO where ID=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        DO ubean = new DO();
        while (rs.next()) {
            ubean.setID(rs.getInt(1));
            ubean.setKODEDO(rs.getString(2));
            ubean.setTANGGAL(rs.getString(3));
            ubean.setKODEPELANGGAN(rs.getString(4));
            ubean.setSTATUS(rs.getString(5));
            ubean.setTGLTUTUP(rs.getString(6));
            ubean.setSTATUSAKTIF(rs.getString(7)); 
        }
        rs.close();
        pstmt.close();
        return ubean;
    }

    public static String getKODEDO(Connection conn) {
        String hasil = "";
        int jum = 0;
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select count(ID) from DO where left(KODEDO,2)='" + Util.getthn(tgl).substring(2, 4) + "'");
            if (rs.next()) {
                if (rs.getString(1) != null) {
                    jum = rs.getInt(1) + 1;
                }
            }
            hasil = (com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("DO%04d").sprintf(jum));
            rs.close();
            stat.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } 
        return hasil;
    }

    public static class triggerDO implements Trigger {

        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
            // initialize the trigger object is necessary
        }

        /**
         * This method is called for each triggered action.
         *
         * @param conn a connection to the database
         * @param oldRow the old row, or null if no old row is available (for INSERT)
         * @param newRow the new row, or null if no new row is available (for DELETE)
         * @throws SQLException if the operation must be undone
         */
        public void fire(Connection conn,
                Object[] oldRow, Object[] newRow)
                throws SQLException {
            if (newRow != null && oldRow == null) {
            } else if (newRow == null && oldRow != null) {
                Statement stat = conn.createStatement();
                stat.execute("delete from DORINCI where IDDO=" + oldRow[0].toString() + "");
                stat.execute("delete from STOK where IDPENJUALAN=" + oldRow[0].toString() + " AND KODETRANS='D'");
                stat.execute("delete from STOK where IDPENJUALAN=" + oldRow[0].toString() + " AND KODETRANS='E'");
                System.out.println("Delete DO Rinci, Stok D, Stok E");
            }
        }

        public void close() {
            // ignore
        }

        public void remove() {
            // ignore
        }
    }
}
