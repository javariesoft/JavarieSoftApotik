/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.rinciretur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author erwadi
 */
public class rincireturDao {

    public static boolean insertIntoRINCIRETUR(Connection con, rinciretur r)
            throws SQLException {
        String sql = "INSERT INTO RETURRINCI "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, r.getID());
        statement.setInt(2, r.getIDRETUR());
        statement.setString(3, r.getKODEBARANG());
        statement.setInt(4, r.getJUMLAH());
        statement.setFloat(5, r.getHARGA());
        statement.setFloat(6, r.getTOTAL());
        statement.setFloat(7, r.getDISKON());
        statement.setFloat(8, r.getPPN());
        statement.setInt(9, r.getIDDO());
        statement.setString(10, r.getSATUAN());
        statement.setInt(11, r.getJUMLAHKECIL());
        statement.setDouble(12, r.getCOGS());
        statement.setString(13, r.getKODEBATCH());
        statement.setString(14, r.getEXPIRE());
        boolean h = statement.execute();
        statement.close();
        return !h;
    }

    public static boolean updateRINCIRETUR(Connection con, rinciretur r)
            throws SQLException {
        String sql = "SELECT * FROM RINCIRETUR WHERE ID = ?";
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
        entry.updateInt("KODERETUR", r.getIDRETUR());

        if (r.getKODEBARANG() != null) {
            entry.updateString("KODEBARANG", r.getKODEBARANG());
        }
        entry.updateInt("JUMLAH", r.getJUMLAH());
        entry.updateFloat("HARGA", r.getHARGA());
        entry.updateFloat("TOTAL", r.getTOTAL());
        entry.updateFloat("DISKON", r.getDISKON());
        entry.updateFloat("PPN", r.getPPN());
        entry.updateInt("IDDO",r.getIDDO());
        entry.updateString("SATUAN",r.getSATUAN());
        entry.updateInt("JUMLAHKECIL",r.getJUMLAHKECIL());
        entry.updateDouble("COGS",r.getCOGS());
        entry.updateString("KODEBATCH",r.getKODEBATCH());
        entry.updateString("EXPIRE",r.getEXPIRE());
        entry.updateRow();
        entry.close();
        statement.close();
        return true;
    }

    public static void deleteFromRINCIRETUR(Connection con, int keyId) throws SQLException {
        String sql = "DELETE FROM RETURRINCI WHERE ID = ?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, keyId);
        statement.executeUpdate();
        statement.close();
    }
    public static int getID(Connection c) throws SQLException{
        int hasil=1;
        PreparedStatement pstmt = c.prepareStatement("select max(id) from RETURRINCI");
        ResultSet rs=pstmt.executeQuery();
        if(rs.next()){
            if(rs.getString(1)!=null){
                hasil=rs.getInt(1)+1;
            }
        }
        return hasil;
    }
}
