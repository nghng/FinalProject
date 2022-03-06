/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Kindergarten;

/**
 *
 * @author admin
 */
public class KindergartenDBContext extends DBContext {

    public ArrayList<Kindergarten> getKinder(int kid, String rawkname) throws UnsupportedEncodingException {

        String kname = new String(rawkname.getBytes(), "UTF-8");
        ArrayList<Kindergarten> kindergartens = new ArrayList<>();
        String sql = "select kid,kname from Kindergarten ";
        PreparedStatement stm = null;

        try {
            if (kid != -1 && (kname == null || kname.trim().length() == 0)) {
                sql += " where kid = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);

            }
            if (kname != null && kname.trim().length() > 0 && kid == -1) {
                sql += " where kname = N'" + kname + "'";
//                sql += " where kname = N?";
                stm = connection.prepareStatement(sql);
//                stm.setString(1, kname);
            }
            if (kid == -1 && (kname == null || kname.trim().length() == 0)) {
                return kindergartens;
            }
            if (kid != -1 && (kname != null && kname.trim().length() > 0)) {
                sql += " where kid = ? and kname = N'" + kname + "'";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);
//                stm.setString(2, kname);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Kindergarten k = new Kindergarten();
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                kindergartens.add(k);
            }

        } catch (SQLException ex) {
            Logger.getLogger(KindergartenDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kindergartens;
    }

    public ArrayList<Kindergarten> getKinderForPaging(int pageindex, int pagesize) {
        ArrayList<Kindergarten> kindergartens = new ArrayList<>();
        try {
            String sql = "SELECT kid,kname FROM\n"
                    + "	(SELECT *,ROW_NUMBER() OVER (ORDER BY kid ASC) as row_index FROM Kindergarten) tbl\n"
                    + "	WHERE row_index >= (?-1)*? + 1 \n"
                    + "	AND row_index <= ? * ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, pageindex);
            stm.setInt(2, pagesize);
            stm.setInt(3, pageindex);
            stm.setInt(4, pagesize);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Kindergarten k = new Kindergarten();
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                kindergartens.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(KindergartenDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kindergartens;
    }

    public int count() {
        try {
            String sql = "SELECT COUNT(*) as Total FROM Kindergarten";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(KindergartenDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public Kindergarten getKinderByKid(int kid) {
        String sql = "select kid,kname from Kindergarten\n"
                + "where kid = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, kid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Kindergarten k = new Kindergarten();
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                return k;
            }
        } catch (SQLException ex) {
            Logger.getLogger(KindergartenDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
