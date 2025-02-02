/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.rincijurnal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author erwadi
 */
public class rincijurnalDao {

    public static boolean insertIntoRINCIJURNAL(Connection con, rincijurnal r) throws SQLException {
        String sql = "INSERT INTO RINCIJURNAL "
                + "VALUES (?, ?, ?, ?,?,?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, r.getKODEJURNAL());
        statement.setString(2, r.getKODEPERKIRAAN());
        statement.setDouble(3, r.getDEBET());
        statement.setDouble(4, r.getKREDIT());
        statement.setInt(5, r.getNO());
        statement.setString(6, r.getREF());
        boolean i = statement.execute();
        statement.close();
        return i;
    }

    public static void deleteFromRINCIJURNAL(Connection con, String k1,String k2) throws SQLException {
        String sql = "DELETE FROM RINCIJURNAL WHERE KODEJURNAL = ? and KODEPERKIRAAN=?";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, k1);
        statement.setString(2, k2);
        statement.executeUpdate();
        statement.close();
    }
}
