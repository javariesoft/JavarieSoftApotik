/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.returbelirinci;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author erwadi
 */
public class returbelirinciDao {

    public static boolean insertIntoRETURBELIRINCI(Connection con, returbelirinci r)
            throws SQLException {
        String sql = "INSERT INTO RETURBELIRINCI "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, r.getID());
        statement.setInt(2, r.getIDRETURBELI());
        statement.setString(3, r.getKODEBARANG());
        statement.setInt(4, r.getJUMLAH());
        statement.setFloat(5, r.getHARGA());
        statement.setFloat(6, r.getTOTAL());
        statement.setString(7, r.getSATUAN());
        statement.setInt(8, r.getJUMLAHKECIL());
        statement.setString(9, r.getKODEBATCH());
        statement.setString(10, r.getEXPIRE());
        statement.setDouble(11, r.getDISKON());
        statement.setDouble(12, r.getPPN());
        boolean h = statement.execute();
        statement.close();
        return !h;
    }

    public static boolean updateRETURBELIRINCI(Connection con, returbelirinci r)
            throws SQLException {
        String sql = "SELECT * FROM RETURBELIRINCI WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        statement.setInt(1, r.getID());
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

        entry.updateInt("IDRETURBELI", r.getIDRETURBELI());
        if (r.getKODEBARANG() != null) {
            entry.updateString("KODEBARANG", r.getKODEBARANG());
        }
        entry.updateInt("JUMLAH", r.getJUMLAH());
        entry.updateFloat("HARGA", r.getHARGA());
        entry.updateFloat("TOTAL", r.getTOTAL());
        entry.updateString("SATUAN", r.getSATUAN());
        entry.updateInt("JUMLAHKECIL", r.getJUMLAHKECIL());
        if (r.getKODEBATCH() != null) {
            entry.updateString("KODEBATCH", r.getKODEBATCH());
        }
        if(r.getEXPIRE() != null) {
            entry.updateString("EXPIRE", r.getEXPIRE());
        }
        entry.updateDouble("DISKON", r.getDISKON());
        entry.updateDouble("PPN", r.getPPN());
        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromRETURBELIRINCI(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM RETURBELIRINCI WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
    }

    public static int getID(Connection c) throws SQLException {
        int hasil = 1;
        PreparedStatement pstmt = c.prepareStatement("select max(id) from RETURBELIRINCI");
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            if (rs.getString(1) != null) {
                hasil = rs.getInt(1) + 1;
            }
        }
        return hasil;
    }
}
