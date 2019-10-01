/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erv.db;

import com.erv.exception.JavarieException;
import com.erv.function.PrintfFormat;
import com.erv.function.Util;
import com.erv.model.bank;
import com.erv.model.jurnal;
import com.erv.model.penjualan;
import com.erv.model.piutang;
import com.erv.model.rincipenjualan;
import com.erv.model.stok;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import org.h2.api.Trigger;

/**
 *
 * @author erwadi
 */
public class penjualanDao {

    public boolean insert(Connection con, penjualan bb) throws SQLException {
        String sql = "insert into PENJUALAN values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setInt(1, bb.getID());
        pstmt.setString(2, bb.getFAKTUR());
        //pstmt.setDate(3, bb.getTANGGAL());
        pstmt.setString(3, bb.getTANGGAL());
        pstmt.setString(4, bb.getKODEPELANGGAN());
        pstmt.setString(5, bb.getCASH());
        //pstmt.setDate(6, bb.getTGLLUNAS());
        pstmt.setString(6, bb.getTGLLUNAS());
        pstmt.setDouble(7, bb.getPPN());
        pstmt.setDouble(8, bb.getDP());
        pstmt.setDouble(9, bb.getDISKON());
        pstmt.setString(10, bb.getSTATUS());
        pstmt.setString(11, bb.getIDSALES());
        pstmt.setDouble(12, bb.getTAMBAHANTOTAL());
        pstmt.setString(13, bb.getJENISTRANS());
        pstmt.setInt(14,bb.getIDBANK());
        pstmt.setString(15, bb.getSTATUSDO());
        boolean i = pstmt.execute();
        pstmt.close();
        return i;
    }

    public void createPenjualan(Connection con, List<penjualan> header, List<rincipenjualan> detail) throws SQLException, JavarieException, ClassNotFoundException {
        stok st = null;
        int IDjual = 0;
        penjualan jual = null;
        double totalDiskon = 0;
        double totalPPN = 0;
        double totalJual = 0;
        double totalHPP = 0;
        if (header.size() > 0 && detail.size() > 0) {
            for (ListIterator<penjualan> lt = header.listIterator(); lt.hasNext();) {
                jual = lt.next();
                if (jual.getID() == 0) {
//                    IDjual = getID(con);
//                    jual.setFAKTUR(setFaktur(con));
                    if (jual.getPPN() == 0) {
                        IDjual = getID(con);
                        jual.setFAKTUR(setFakturTanpaPajak(con));
                    } else {
                        IDjual = getID(con);
                        jual.setFAKTUR(setFakturPajak(con));
                    }
                }else{
                    IDjual = jual.getID();
                    jual.setFAKTUR(jual.getFAKTUR()); 
                }
                jual.setID(IDjual);
                insert(con, jual);
            }
            //int count = 0;
            int idrp = rincipenjualanDao.getID(con);

            for (ListIterator<rincipenjualan> lt = detail.listIterator(); lt.hasNext();) {
                rincipenjualan bb = lt.next();
                bb.setID(idrp);
                rincipenjualanDao.insert(con, bb);
                if (bb.getIDDO() == 0) {
                    st = new stok();
                    st.setIDPENJUALAN(IDjual);
                    st.setKODEBARANG(bb.getKODEBARANG());
                    //st.setTANGGAL(java.sql.Date.valueOf(tgl.getText()));
                    st.setTANGGAL(jual.getTANGGAL());
                    st.setIN(0);
                    st.setOUT(bb.getJUMLAHKECIL());
                    st.setKODETRANS("J");
                    st.setKODEBATCH(bb.getKODEBATCH());
                    stokDao.insertIntoSTOK(con, st);
                } else {
//                    st = new stok();
//                    st.setIDPENJUALAN(IDjual);
//                    st.setKODEBARANG(bb.getKODEBARANG());
//                    //st.setTANGGAL(java.sql.Date.valueOf(tgl.getText()));
//                    st.setTANGGAL(jual.getTANGGAL());
//                    st.setIN(0);
//                    st.setOUT(bb.getJUMLAHKECIL());
//                    st.setKODETRANS("1");
//                    st.setKODEBATCH(bb.getKODEBATCH());
//                    stokDao.insertIntoSTOK(con, st);
//                    StokDO stokDO = StokDODao.getDetails(con, "select * from STOKDO where KODEBARANG='"+bb.getKODEBARANG()+"' AND KODEBATCH='"+bb.getKODEBATCH()+"'");
//                    if(stokDO!=null){
//                        stokDO.setSTOK(stokDO.getSTOK()-bb.getJUMLAHKECIL());
//                        StokDODao.updateSTOKDO(con, stokDO);
//                    }
                }
                ////////////////////////////////////
                totalJual += bb.getJUMLAH() * bb.getHARGA();
                totalDiskon += bb.getDISKON();
                totalHPP += bb.getJUMLAHKECIL() * bb.getCOGS();
                totalPPN += bb.getPPN();
                idrp++;
            }
            double totalDiskonSemua = totalDiskon + jual.getDISKON();
            if (totalPPN != 0) {
                totalPPN = 0.1 * (totalJual - totalDiskonSemua);
            }
            double totalBayar = totalJual + totalPPN - totalDiskonSemua;
            jual.setID(IDjual);
            jual.setPPN(totalPPN);
            update(con, jual);
            insertJurnal(con, jual, totalJual, totalBayar, totalDiskonSemua, totalPPN, totalHPP);
        }

    }

    void insertJurnal(Connection con, penjualan p, double total, double totalBayar, double diskon, double ppn, double hpp) throws JavarieException, SQLException, ClassNotFoundException {
        int IDJurnal = 0;
        jurnal j = new jurnal();
        Statement s;
        IDJurnal = jurnalDao.getIDJurnal(con);
        j.setID(IDJurnal);
        j.setTANGGAL(p.getTANGGAL());
        j.setKODEJURNAL(p.getFAKTUR());
        if (p.getCASH().equals("2")) {
            j.setDESKRIPSI("Penjualan Kepada " + new pelangganDao(con).getDetails(p.getKODEPELANGGAN()).getNAMA() + " Via Bank ");
        } else {
            j.setDESKRIPSI("Penjualan Kepada " + new pelangganDao(con).getDetails(p.getKODEPELANGGAN()).getNAMA());
        }
        jurnalDao.insertIntoJURNAL(con, j);
        /////////////////////////////////////////////////////////
        //////////Insert Rinci
        ////////////////////////////////////////////////////////

        s = con.createStatement();
        if (p.getCASH().equals("0")) {
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "KAS") + "'," + totalBayar + ",0,1,'')");
            if (diskon != 0.0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "DISKONJUAL") + "'," + diskon + ",0,2,'')");
            }
            if (ppn != 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PPNJUAL") + "',0," + ppn + ",3,'')");
            }
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PENJUALAN") + "',0," + total + ",4,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "HPP") + "'," + hpp + ",0,5,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PERSEDIAAN") + "',0," + hpp + ",6,'')");

            s.executeBatch();
        } else if (p.getCASH().equals("1")) {
            pelangganDao dbplg = new pelangganDao(con);
            double sisa = totalBayar - p.getDP();
            if (p.getDP() > 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "KAS") + "'," + p.getDP() + ",0,1,'')");
            }
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + dbplg.getDetails(p.getKODEPELANGGAN()).getKODEAKUN() + "'," + sisa + ",0,1,'')");
            if (diskon != 0.0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "DISKONJUAL") + "'," + diskon + ",0,2,'')");
            }
            if (ppn != 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PPNJUAL") + "',0," + ppn + ",3,'')");
            }

            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PENJUALAN") + "',0," + total + ",4,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "HPP") + "'," + hpp + ",0,5,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PERSEDIAAN") + "',0," + hpp + ",6,'')");
            s.executeBatch();

            dbplg = null;
            prosesSimpanHutang(con, p, sisa);
        } else if (p.getCASH().equals("2")) {
            bank bank;
            bank = bankDao.getDetails(con, p.getIDBANK());

            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + bank.getKODEAKUN() + "'," + totalBayar + ",0,1,'')");
            if (diskon != 0.0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "DISKONJUAL") + "'," + diskon + ",0,2,'')");
            }
            if (ppn != 0) {
                s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PPNJUAL") + "',0," + ppn + ",3,'')");
            }
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PENJUALAN") + "',0," + total + ",4,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "HPP") + "'," + hpp + ",0,5,'')");
            s.addBatch("insert into RINCIJURNAL values(" + IDJurnal + ",'" + settingDao.getAkun(con, "PERSEDIAAN") + "',0," + hpp + ",6,'')");
            s.executeBatch();
            s.close();
        }
    }

    void prosesSimpanHutang(Connection con, penjualan p, double totalHutang) throws SQLException {
        piutang ht = new piutang();
        ht.setKODEPIUTANG(piutangDao.getKodePiutang(con));
        ht.setIDPENJUALAN(p.getID());
        ht.setTANGGAL(p.getTANGGAL());
        ht.setJUMLAH(totalHutang);
        ht.setIDPELANGGAN(p.getKODEPELANGGAN());
        ht.setJATUHTEMPO(p.getTGLLUNAS());
        ht.setKETERANGAN("Piutang Kepada " + new pelangganDao(con).getDetails(p.getKODEPELANGGAN()).getNAMA());
        ht.setSTATUS("1");
        ht.setID(piutangDao.getID(con));
        piutangDao.insertIntoPIUTANG(con, ht);
    }

    public penjualan getDetails(Connection con, int id) throws SQLException, ClassNotFoundException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from PENJUALAN where ID=?");
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        penjualan ubean = null;
        while (rs.next()) {
            ubean = new penjualan();
            ubean.setID(rs.getInt(1));
            ubean.setFAKTUR(rs.getString(2));
            ubean.setTANGGAL(rs.getString(3));
            ubean.setKODEPELANGGAN(rs.getString(4));
            ubean.setCASH(rs.getString(5));
            ubean.setTGLLUNAS(rs.getString(6));
            ubean.setPPN(rs.getFloat(7));
            ubean.setDP(rs.getFloat(8));
            ubean.setDISKON(rs.getFloat(9));
            ubean.setSTATUS(rs.getString(10));
            ubean.setIDSALES(rs.getString(11));
            ubean.setTAMBAHANTOTAL(rs.getFloat(12));
            ubean.setJENISTRANS(rs.getString("JENISTRANS"));
            ubean.setIDBANK(rs.getInt("IDBANK")); 
            ubean.setSTATUSDO(rs.getString("STATUSDO")); 
        }
        rs.close();
        pstmt.close();
        return ubean;
    }

    public penjualan getDetailID(Connection con, String nfak) throws SQLException, ClassNotFoundException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("select * from PENJUALAN where FAKTUR=?");
        pstmt.setString(1, nfak);
        ResultSet rs = pstmt.executeQuery();
        penjualan ubean = new penjualan();
        while (rs.next()) {
            ubean.setID(rs.getInt(1));
            ubean.setFAKTUR(rs.getString(2));
            ubean.setTANGGAL(rs.getString(3));
            ubean.setKODEPELANGGAN(rs.getString(4));
            ubean.setCASH(rs.getString(5));
            ubean.setTGLLUNAS(rs.getString(6));
            ubean.setPPN(rs.getFloat(7));
            ubean.setDP(rs.getFloat(8));
            ubean.setDISKON(rs.getFloat(9));
            ubean.setSTATUS(rs.getString(10));
            ubean.setIDSALES(rs.getString(11));
            ubean.setTAMBAHANTOTAL(rs.getFloat(12));
            ubean.setJENISTRANS(rs.getString("JENISTRANS")); 
            ubean.setIDBANK(rs.getInt("IDBANK")); 
            ubean.setSTATUSDO(rs.getString("STATUSDO")); 
        }

        return ubean;
    }

    public boolean update(Connection con, penjualan bb) throws SQLException {
        String sql = "update PENJUALAN set FAKTUR=?,TANGGAL=?,KODEPELANGGAN=?,CASH=?,"
                + "TGLLUNAS=?,PPN=?,DP=?,DISKON=?,STATUS=?,IDSALES=?,TAMBAHANTOTAL=?, JENISTRANS=?, IDBANK=?, STATUSDO=?  where ID=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        //set values to prepared statement object by getting values from bean object
        pstmt.setString(1, bb.getFAKTUR());
        pstmt.setString(2, bb.getTANGGAL());
        pstmt.setString(3, bb.getKODEPELANGGAN());
        pstmt.setString(4, bb.getCASH());
        pstmt.setString(5, bb.getTGLLUNAS());
        pstmt.setDouble(6, bb.getPPN());
        pstmt.setDouble(7, bb.getDP());
        pstmt.setDouble(8, bb.getDISKON());
        pstmt.setString(9, bb.getSTATUS());
        pstmt.setString(10, bb.getIDSALES());
        pstmt.setDouble(11, bb.getTAMBAHANTOTAL());
        pstmt.setString(12, bb.getJENISTRANS());
        pstmt.setInt(13, bb.getIDBANK());
        pstmt.setString(14, bb.getSTATUSDO());
        pstmt.setInt(15, bb.getID());

        boolean i = pstmt.execute();
        return i;
    }

    public void deleteDetails(Connection con, int id) throws SQLException, ClassNotFoundException {
        //here we will write code to get a single record from database
        PreparedStatement pstmt = con.prepareStatement("delete from PENJUALAN where ID=?");
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public int getID(Connection con) throws SQLException {
        int hasil = 1;
        PreparedStatement pstmt = con.prepareStatement("select max(id) from PENJUALAN");
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

    public String getAkun(String kode) throws SQLException {
        String hasil = "";
        Connection con = koneksi.getKoneksiJ();
        Statement stat = con.createStatement();
        ResultSet rs = stat.executeQuery("select AKUN from SETTING where KODE='" + kode + "'");
        if (rs.next()) {
            hasil = rs.getString(1);
        }
        rs.close();
        stat.close();
        return hasil;
    }

//    public static String setFaktur(Connection conn) {
////        String hasil = "";
////        int jum = 0;
////        String tgl = com.erv.function.Util.toDateStringSql(new Date());
////        try {
////            Statement stat = conn.createStatement();
////            ResultSet rs = stat.executeQuery("select max(id) from PENJUALAN where left(FAKTUR,2)='" + Util.getthn(tgl).substring(2, 4) + "'");
////            if (rs.next()) {
////                if (rs.getString(1) != null) {
////                    jum = rs.getInt(1) + 1;
////                }
////            }
////            hasil = (com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("%06d").sprintf(jum));
////            rs.close();
////            stat.close();
////        } catch (SQLException ex) {
////            System.out.println(ex.toString());
////        }
////        return hasil;
//        String hasil = "";
//        long jum = 1;
//        String tgl = com.erv.function.Util.toDateStringSql(new Date());
//        try {
//            Statement stat = conn.createStatement();
//            ResultSet rs = stat.executeQuery("select max(id) from PENJUALAN where left(FAKTUR,2)='" + Util.getthn(tgl).substring(2, 4) + "'");
//            rs.next();
//            long id = rs.getLong(1);
//            ResultSet rs1 = stat.executeQuery("select * from penjualan where id=" + id + "");
//
//            if (rs1.next()) {
//                String[] exploded = Util.split(rs1.getString(2), ".");
//                jum = Long.parseLong(exploded[1]) + 1;
//            }
//            hasil = (com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("%06d").sprintf(jum));
//            rs.close();
//            rs1.close();
//            stat.close();
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//        }
//        return hasil;
//    }
    
    public static String setFakturPajak(Connection conn) {

        String hasil = "";
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        int jum = 1;
//         String sql = "select max(right(kodejurnal,4)) from jurnal "
//                + "where substring(kodejurnal,4,2)='" + Util.getbln(tgl) + "' "
//                + "and substring(kodejurnal,6,2)='" + Util.getthn(tgl).substring(2, 4) + "' and left(kodejurnal,"+kodeDepan.length()+")='"+kodeDepan+"'";

        String sql = "select max(right(faktur,6)) from penjualan "
                + "where substring(faktur,0,2)='" + Util.getthn(tgl).substring(2, 4) + "'";
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString(1) != null) {
                    jum = rs.getInt(1) + 1;
                }
            }
            hasil = com.erv.function.Util.getthn(tgl).substring(2, 4) + "." + new PrintfFormat("%06d").sprintf(jum);
            rs.close();
            stat.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return hasil;
    }
    
    public static String setFakturTanpaPajak(Connection conn) {

        String hasil = "";
        String tgl = com.erv.function.Util.toDateStringSql(new Date());
        int jum = 1;
//         String sql = "select max(right(kodejurnal,4)) from jurnal "
//                + "where substring(kodejurnal,4,2)='" + Util.getbln(tgl) + "' "
//                + "and substring(kodejurnal,6,2)='" + Util.getthn(tgl).substring(2, 4) + "' and left(kodejurnal,"+kodeDepan.length()+")='"+kodeDepan+"'";

        String sql = "select max(right(faktur,4)) from penjualan "
                + "where substring(faktur,4,2)='" + Util.getthn(tgl).substring(2, 4) + "' and left(faktur,2)='MA'";
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString(1) != null) {
                    jum = rs.getInt(1) + 1;
                }
            }
            hasil = "MA." + com.erv.function.Util.getthn(tgl).substring(2, 4) + "" + new PrintfFormat("%04d").sprintf(jum);
            rs.close();
            stat.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return hasil;
    }

    public static boolean cekFaktur(Connection conn, String kode) {
        boolean hasil = false;
        try {
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery("select * from PENJUALAN where FAKTUR='" + kode + "'");
            if (rs.next()) {
                if (rs.getString(2) != null) {
                    hasil = true;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        return hasil;
    }

    public static class triggerPenjualan implements Trigger {

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
            if (newRow != null && oldRow == null) {
            } else if (newRow == null && oldRow != null) {
                Statement stat = conn.createStatement();
                stat.execute("delete from RINCIPENJUALAN where IDPENJUALAN=" + oldRow[0].toString() + "");
                stat.execute("delete from JURNAL where KODEJURNAL='" + oldRow[1].toString() + "'");
                stat.execute("delete from PIUTANG where IDPENJUALAN=" + oldRow[0].toString() + " AND IDPELANGGAN='" + oldRow[3].toString() + "'");
                stat.execute("delete from STOK where IDPENJUALAN=" + oldRow[0].toString() + " AND KODETRANS='J'");
                stat.execute("delete from STOK where IDPENJUALAN=" + oldRow[0].toString() + " AND KODETRANS='1'");
                System.out.println("Delete Penjualan, Rinci Penjualan, Jurnal, Piutang, Stok");
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
