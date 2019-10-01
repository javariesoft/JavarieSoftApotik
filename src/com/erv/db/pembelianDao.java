/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.function.PrintfFormat;
import com.erv.function.Util;
import com.erv.model.pembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.h2.api.Trigger;

/**
 *
 * @author erwadi
 */
public class pembelianDao {

    public static boolean insertIntoPEMBELIAN(Connection con, pembelian p) throws SQLException {
        String sql = "INSERT INTO PEMBELIAN "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, p.getID());
        statement.setString(2, p.getNOFAKTUR());
        statement.setString(3, p.getIDSUPPLIER());
        statement.setString(4, p.getCASH());
        statement.setString(5, p.getTGLBAYAR());
        statement.setDouble(6, p.getDP());
        statement.setDouble(7, p.getPAJAK());
        statement.setDouble(8, p.getDISKON());
        statement.setString(9, p.getTANGGAL());
        statement.setString(10, p.getSTATUS());
        statement.setString(11, p.getNOFAKTURSUPPLIER());
        statement.setString(12, p.getTGLMASUK());
        boolean h = statement.execute();
        statement.close();
        return h;
    }

    public static boolean updatePEMBELIAN(Connection con, int keyId, pembelian p) throws SQLException {
        String sql = "SELECT * FROM PEMBELIAN WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, keyId);
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
        if (p.getNOFAKTUR() != null) {
            entry.updateString("NOFAKTUR", p.getNOFAKTUR());
        }
        if (p.getIDSUPPLIER() != null) {
            entry.updateString("IDSUPPLIER", p.getIDSUPPLIER());
        }
        if (p.getCASH() != null) {
            entry.updateString("CASH", p.getCASH());
        }
        if (p.getTGLBAYAR() != null) {
            entry.updateString("TGLBAYAR", p.getTGLBAYAR());
        }
        entry.updateDouble("DP", p.getDP());
        entry.updateDouble("PAJAK", p.getPAJAK());
        entry.updateDouble("DISKON", p.getDISKON());
        entry.updateString("STATUS", p.getSTATUS());
        if (p.getTANGGAL() != null) {
            entry.updateString("TANGGAL", p.getTANGGAL());
        }
        if (p.getNOFAKTURSUPPLIER() != null) {
            entry.updateString("NOFAKTURSUPPLIER", p.getNOFAKTURSUPPLIER());
        }
        if (p.getTGLMASUK() != null) {
            entry.updateString("TGLMASUK", p.getTGLMASUK());
        }
        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static pembelian getDetails(Connection con, int id) throws SQLException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from PEMBELIAN where ID=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        pembelian ubean = new pembelian();
        while (rs.next()) {
            ubean.setID(rs.getInt(1));
            ubean.setNOFAKTUR(rs.getString(2));
            ubean.setIDSUPPLIER(rs.getString(3));
            ubean.setCASH(rs.getString(4));
            ubean.setTGLBAYAR(rs.getString(5));
            ubean.setDP(rs.getFloat(6));
            ubean.setPAJAK(rs.getFloat(7));
            ubean.setDISKON(rs.getFloat(8));
            ubean.setTANGGAL(rs.getString(9));
            ubean.setSTATUS(rs.getString(10));
            ubean.setNOFAKTURSUPPLIER(rs.getString(11));
            ubean.setTGLMASUK(rs.getString(12));
        }
        rs.close();
        pstmt.close();
        return ubean;
    }

    public static void deleteFromPEMBELIAN(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM PEMBELIAN WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
    }

    public static int getID(Connection con) throws SQLException {
        int hasil = 1;
        PreparedStatement pstmt = con.prepareStatement("select max(id) from PEMBELIAN");
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

    public static String setFaktur(Connection conn) {
        String hasil = "";
        int jum = 1;
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select max(id) from PEMBELIAN where left(NOFAKTUR,2)='" + Util.getthn(tgl).substring(2, 4) + "'");
            rs.next();
            long id = rs.getLong(1);
            ResultSet rs1 = stat.executeQuery("select * from PEMBELIAN where id=" + id + "");

            if (rs1.next()) {
                String[] exploded = Util.split(rs1.getString(2), ".");
                jum = (int) (Long.parseLong(exploded[1]) + 1);
            }
            hasil = (com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("%05d").sprintf(jum));
            rs.close();
            rs1.close();
            stat.close();
//            if (rs.next()) {
//                if (rs.getString(1) != null) {
//                    jum = rs.getInt(1) + 1;
//                }
//            }
//            hasil = (com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("%05d").sprintf(jum));
//            rs.close();
//            rs1.close();
//            stat.close();
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        return hasil;
    }

    public static boolean cekFaktur(Connection conn, String kode) throws SQLException {
        boolean hasil = false;
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select * from PEMBELIAN where NOFAKTUR='" + kode + "'");
        if (rs.next()) {
            if (rs.getString(2) != null) {
                hasil = true;
            }
        }
        rs.close();
        stat.close();
        return hasil;
    }

    public static class triggerPembelian implements Trigger {

        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {
            // initialize the trigger object is necessary
        }

        /**
         * This method is called for each triggered action.
         *
         * @param conn a connection to the database
         * @param oldRow the old row, or null if no old row is available (for
         * INSERT)
         * @param newRow the new row, or null if no new row is available (for
         * DELETE)
         * @throws SQLException if the operation must be undone
         */
        public void fire(Connection conn,
                Object[] oldRow, Object[] newRow)
                throws SQLException {
            String kodeJurnal = "";
            java.sql.Date tgl = null;
            PreparedStatement prep;
            if (newRow != null && oldRow == null) {
            } else if (newRow == null && oldRow != null) {
                kodeJurnal = (String) oldRow[1];
                Statement stat = conn.createStatement();
                stat.execute("delete from RINCIPEMBELIAN where IDPEMBELIAN=" + oldRow[0].toString() + "");
                stat.execute("delete from JURNAL where KODEJURNAL='" + kodeJurnal + "'");
                stat.execute("delete from HUTANG where IDPEMBELIAN=" + oldRow[0].toString() + " AND IDSUPPLIER='" + oldRow[2].toString() + "'");
                stat.execute("delete from STOK where IDPENJUALAN=" + oldRow[0].toString() + " AND KODETRANS='B'");
                System.out.println("Delete Pembelian, Jurnal, Hutang dan Stok");   
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
