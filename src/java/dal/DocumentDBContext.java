/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Document;

/**
 *
 * @author admin
 */
public class DocumentDBContext extends DBContext {

    public ArrayList<Document> getDocNamebyRid(int rid) {
        ArrayList<Document> docs = new ArrayList<>();
        String sql = "select d.did,d.dname  from Document d join GroupDocument gd \n"
                + "on d.did = gd.did join Auth a \n"
                + "on a.authid = gd.authID\n"
                + "where gd.rid = ? and gd.authID = 3";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, rid);
            rs = stm.executeQuery();
            while (rs.next()) {
                Document d = new Document();
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                docs.add(d);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return docs;
    }
}
