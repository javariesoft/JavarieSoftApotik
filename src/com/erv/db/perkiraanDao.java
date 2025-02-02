/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.model.perkiraan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erwadi
 */
public class perkiraanDao {

    public boolean insert(Connection con, perkiraan bb) throws SQLException {
        String sql = "insert into PERKIRAAN values (?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1, bb.getKODEPERKIRAAN());
        pstmt.setString(2, bb.getNAMAPERKIRAAN());
        pstmt.setInt(3, bb.getGRUP());
        pstmt.setString(4, bb.getTIPE());
        boolean i = pstmt.execute();
        pstmt.close();
        return i;
    }

    public List getAlldetails(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement("select * from PERKIRAAN");
        ResultSet rs = pstmt.executeQuery();
        List list = new ArrayList();
        while (rs.next()) {
            perkiraan ubean = new perkiraan();
            ubean.setKODEPERKIRAAN(rs.getString(1));
            ubean.setNAMAPERKIRAAN(rs.getString(2));
            ubean.setGRUP(rs.getInt(3));
            ubean.setTIPE(rs.getString(4));
            list.add(ubean);
        }
        rs.close();
        pstmt.close();
        return list;
    }

    public perkiraan getDetails(Connection con, String id) throws SQLException  {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from PERKIRAAN where KODEPERKIRAAN=?");
        pstmt.setString(1, id);
        ResultSet rs = pstmt.executeQuery();
        perkiraan ubean = new perkiraan();
        while (rs.next()) {
            ubean.setKODEPERKIRAAN(rs.getString(1));
            ubean.setNAMAPERKIRAAN(rs.getString(2));
            ubean.setGRUP(rs.getInt(3));
            ubean.setTIPE(rs.getString(4));
        }
        rs.close();
        pstmt.close();
        return ubean;
    }

    public boolean update(Connection con, perkiraan bb) throws SQLException {
        String sql = "update PERKIRAAN set NAMAPERKIRAAN=?,GRUP=?,TIPE=? where KODEPERKIRAAN=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(4, bb.getKODEPERKIRAAN());
        pstmt.setString(1, bb.getNAMAPERKIRAAN());
        pstmt.setInt(2, bb.getGRUP());
        pstmt.setString(3, bb.getTIPE());
        boolean i = pstmt.execute();
        pstmt.close();
        return i;
    }

    public void deleteDetails(String id) throws SQLException {
        //here we will write code to get a single record from database
        Connection con = koneksi.getKoneksiJ();
        PreparedStatement pstmt = con.prepareStatement("delete from PERKIRAAN where KODEPERKIRAAN=?");
        pstmt.setString(1, id);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
