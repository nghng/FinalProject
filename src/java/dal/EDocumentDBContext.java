/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Document;
import model.EmployeeDocument;
import model.Kindergarten;

/**
 *
 * @author admin
 */
public class EDocumentDBContext extends DBContext {

    public ArrayList<EmployeeDocument> getEDocumentByKid(int kid) {
        ArrayList<EmployeeDocument> eDocs = new ArrayList<>();
        String sql = "select ed.did,ed.eid,ed.[from],ed.content,d.dname from EmployeeDocument ed join Employee e\n"
                + "on e.eid = ed.eid join Kindergarten k \n"
                + "on k.kID = e.kID join Document d on d.did = ed.did where k.kID = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, kid);
            rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeDocument ed = new EmployeeDocument();
                Document d = new Document();
                ed.setEid(rs.getInt("eid"));
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                ed.setDoc(d);
                ed.setContent(rs.getBytes("content"));
                ed.setDatetime(rs.getTimestamp("from"));
                eDocs.add(ed);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eDocs;

    }

    public ArrayList<EmployeeDocument> getEDocumentByEidDid(int eid, int did) {
        ArrayList<EmployeeDocument> eDocs = new ArrayList<>();
        String sql = "select ed.eid,ed.did,ed.content,ed.[from], d.dname from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did where ed.eid = ? and d.did = ? \n";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setInt(2, did);
            rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeDocument ed = new EmployeeDocument();
                Document d = new Document();
                ed.setEid(rs.getInt("eid"));
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                ed.setDoc(d);
                ed.setContent(rs.getBytes("content"));
                ed.setDatetime(rs.getTimestamp("from"));
                eDocs.add(ed);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eDocs;

    }

    public byte[] getContent(int eid, int did, Timestamp datetime) {
        String sql = "select content  from EmployeeDocument \n"
                + "where eid = ? and did = ? and [from] = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setInt(2, did);
            stm.setTimestamp(3, datetime);
            
            ResultSet rs = stm.executeQuery();
            if(rs.next()){
                byte[] result = rs.getBytes("content");
                return result;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
        
    }

}
