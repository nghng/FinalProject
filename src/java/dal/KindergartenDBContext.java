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
                sql += " where kname = N'"+kname+ "'";
//                sql += " where kname = N?";
                stm = connection.prepareStatement(sql);
//                stm.setString(1, kname);
            }
            if (kid == -1 && (kname == null || kname.trim().length() == 0)) {
                sql = "select kname,kID from Kindergarten";
                stm = connection.prepareStatement(sql);
            }
            if (kid != -1 && (kname != null && kname.trim().length() > 0)) {
                sql += " where kid = ? and kname = N'"+kname+"'";
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

}
