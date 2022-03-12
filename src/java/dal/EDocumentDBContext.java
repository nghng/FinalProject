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
        String sql = "select ed.eid,ed.did,ed.content,ed.[from], d.dname,ed.modifiedDate from EmployeeDocument ed join Document d\n"
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
                ed.setModifiedDate(rs.getTimestamp("modifiedDate"));
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
            if (rs.next()) {
                byte[] result = rs.getBytes("content");
                return result;
            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void deleteEdoc(int eid, int did, Timestamp datetime) throws SQLException {
        String sql = "delete EmployeeDocument \n"
                + "where eid = ? and did = ? and [from] = ?";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setInt(2, did);
            stm.setTimestamp(3, datetime);
            stm.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

    }

    public boolean updateDocument(int eid, int did, Timestamp from, byte[] content) throws SQLException {
        String sql = "UPDATE [dbo].[EmployeeDocument]\n"
                + "   SET\n"
                + "      [content] = ?,[modifiedDate] = ?\n"
                + " WHERE eid = ? and did = ? and [from] = ?";

        PreparedStatement stm = null;
        int success = 0;
        try {
            Long datetime = System.currentTimeMillis();
            Timestamp current = new Timestamp(datetime);
            stm = connection.prepareStatement(sql);
            stm.setBytes(1, content);
            stm.setTimestamp(2, current);
            stm.setInt(3, eid);
            stm.setInt(4, did);
            stm.setTimestamp(5, from);

            success = stm.executeUpdate();
            if (success >= 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;
    }

    public boolean createEDoc(int eid, int did, byte[] content) throws SQLException {
        String sql = "INSERT INTO [dbo].[EmployeeDocument]\n"
                + "           ([eid]\n"
                + "           ,[did]\n"
                + "           ,[from]\n"
                + "           ,[content]\n"
                + "           ,[modifiedDate])\n"
                + "     VALUES\n"
                + "           (?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?\n"
                + "           ,?)";

        PreparedStatement stm = null;
        int success = 0;

        try {
            Long datetime = System.currentTimeMillis();
            Timestamp current = new Timestamp(datetime);
            stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setInt(2, did);
            stm.setTimestamp(3, current);
            stm.setBytes(4, content);
            stm.setTimestamp(5, null);
            success = stm.executeUpdate();
            if (success >= 1) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return false;
    }

    public ArrayList<EmployeeDocument> getEdocsByEid(int eid) {
        ArrayList<EmployeeDocument> edocs = new ArrayList<>();
        String sql = "select eid,d.did,d.dname,[from],content,modifiedDate from EmployeeDocument ed\n"
                + "join Document d on d.did = ed.did\n"
                + " where eid = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeDocument ed = new EmployeeDocument();
                Document d = new Document();
                ed.setEid(rs.getInt("eid"));
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                ed.setDatetime(rs.getTimestamp("from"));
                ed.setContent(rs.getBytes("content"));
                ed.setModifiedDate(rs.getTimestamp("modifiedDate"));
                ed.setDoc(d);               
                edocs.add(ed);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return edocs;

    }

}
