/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.rincipembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author erwadi
 */
public class rincipembelianDao {

    public static boolean insertIntoRINCIPEMBELIAN(Connection con, rincipembelian p)
            throws SQLException {
        String sql = "INSERT INTO RINCIPEMBELIAN "
                + "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, p.getID());
        statement.setInt(2, p.getIDPEMBELIAN());
        statement.setString(3, p.getKODEBARANG());
        statement.setInt(4, p.getJUMLAH());
        statement.setDouble(5, p.getHARGA());
        statement.setDouble(6, p.getTOTAL());
        statement.setDouble(7, p.getPPN());
        statement.setDouble(8, p.getDISKON());
        statement.setString(9, p.getSATUAN());
        statement.setInt(10, p.getJUMLAHKECIL());
        statement.setString(11, p.getKODEBATCH());
        statement.setString(12, p.getEXPIRE());
        statement.setDouble(13, p.getDISKONP());
        statement.setString(14, p.getBONUS()); 
        boolean h = statement.execute();
        statement.close();
        return h;
    }

    public static boolean updateRINCIPEMBELIAN(Connection con, int keyId, rincipembelian p)
            throws SQLException {
        String sql = "SELECT * FROM RINCIPEMBELIAN WHERE ID = ?";
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

        entry.updateInt("IDPEMBELIAN", p.getIDPEMBELIAN());
        if (p.getKODEBARANG() != null) {
            entry.updateString("KODEBARANG", p.getKODEBARANG());
        }
        entry.updateInt("JUMLAH", p.getJUMLAH());
        entry.updateDouble("HARGA", p.getHARGA());
        entry.updateDouble("TOTAL", p.getTOTAL());
        entry.updateDouble("PPN", p.getPPN());
        entry.updateDouble("DISKON", p.getDISKON());
        entry.updateString("SATUAN", p.getSATUAN());
        entry.updateInt("JUMLAHKECIL", p.getJUMLAHKECIL());
        entry.updateString("KODEBATCH", p.getKODEBATCH());
        entry.updateString("EXPIRE", p.getEXPIRE());
        entry.updateDouble("DISKONP", p.getDISKONP());
        entry.updateString("BONUS", p.getBONUS());
        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromRINCIPEMBELIAN(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM PEMBELIAN WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
    }
    public static int getID(Connection con) throws SQLException {
        int hasil = 1;
        PreparedStatement pstmt = con.prepareStatement("select max(id) from RINCIPEMBELIAN");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil = rs.getInt(1) + 1;
            }
        }
        return hasil;
    }
}
