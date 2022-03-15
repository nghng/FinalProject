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

    public ArrayList<EmployeeDocument> getEDocumentByKid(int kid, int rid, int pageindex, int pagesize) {
        ArrayList<EmployeeDocument> eDocs = new ArrayList<>();
        String sql = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],ed.meid,em.rid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + " on ed.did = d.did join Employee em \n"
                + " on em.eid = ed.eid where kID = ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";
        String sql_rid = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],ed.meid,em.rid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + " on ed.did = d.did join Employee em \n"
                + " on em.eid = ed.eid where kID = ? and rid = ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (rid >= 0) {
                stm = connection.prepareStatement(sql_rid);
                stm.setInt(1, kid);
                stm.setInt(2, rid);
                stm.setInt(3, pageindex);
                stm.setInt(4, pagesize);
                stm.setInt(5, pageindex);
                stm.setInt(6, pagesize);

            } else {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);
                stm.setInt(2, pageindex);
                stm.setInt(3, pagesize);
                stm.setInt(4, pageindex);
                stm.setInt(5, pagesize);
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeDocument ed = new EmployeeDocument();
                Document d = new Document();
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                ed.setEid(rs.getInt("eid"));
                ed.setContent(rs.getBytes("content"));
                ed.setModifiedDate(rs.getTimestamp("modifiedDate"));
                ed.setDatetime(rs.getTimestamp("from"));
                ed.setDoc(d);
                ed.setMeid(rs.getInt("meid"));
                eDocs.add(ed);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eDocs;

    }
    
    public int getCountEDocumentByKid(int kid, int rid) {
        int total = 0;
        String sql = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],ed.meid,em.rid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + " on ed.did = d.did join Employee em \n"
                + " on em.eid = ed.eid where kID = ?) tbl\n";

        String sql_rid = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],ed.meid,em.rid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + " on ed.did = d.did join Employee em \n"
                + " on em.eid = ed.eid where kID = ? and rid = ?) tbl\n";
              

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (rid >= 0) {
                stm = connection.prepareStatement(sql_rid);
                stm.setInt(1, kid);
                stm.setInt(2, rid);
               

            } else {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);
              
            }

            rs = stm.executeQuery();
            while (rs.next()) {
               total = rs.getInt("total");
               
            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;

    }

    public ArrayList<EmployeeDocument> getEDocumentByEidDid(int eid, int did) {
        ArrayList<EmployeeDocument> eDocs = new ArrayList<>();
        String sql = "select ed.eid,ed.did,ed.content,ed.[from], d.dname,ed.modifiedDate,ed.meid from EmployeeDocument ed join Document d\n"
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
                ed.setMeid(rs.getInt("meid"));
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

    public boolean updateDocument(int eid, int did, Timestamp from, byte[] content, int meid) throws SQLException {
        String sql = "UPDATE [dbo].[EmployeeDocument]\n"
                + "   SET\n"
                + "      [content] = ?,[modifiedDate] = ?,[meid] = ?\n"
                + " WHERE eid = ? and did = ? and [from] = ?";

        PreparedStatement stm = null;
        int success = 0;
        try {
            Long datetime = System.currentTimeMillis();
            Timestamp current = new Timestamp(datetime);
            stm = connection.prepareStatement(sql);
            stm.setBytes(1, content);
            stm.setTimestamp(2, current);
            stm.setInt(3, meid);
            stm.setInt(4, eid);
            stm.setInt(5, did);
            stm.setTimestamp(6, from);

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
        String sql = "select eid,d.did,d.dname,[from],content,modifiedDate,meid from EmployeeDocument ed\n"
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
                ed.setMeid(rs.getInt("meid"));
                edocs.add(ed);

            }
        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return edocs;

    }

    public ArrayList<EmployeeDocument> getEDocumentByKidRidFromTo(int kid, Timestamp from, Timestamp to, int rid, int pageindex, int pagesize) {
        ArrayList<EmployeeDocument> eDocs = new ArrayList<>();
        String afterAndBefore = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and ed.[from] <= ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";
        String afterAndBefore_rid = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and ed.[from] <= ? and em.rid = ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";

        String after_query = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";
        String after_query_rid = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and em.rid = ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";

        String before_query = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] <= ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";
        String before_query_rid = " SELECT * FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] <= ? and em.rid = ?) tbl\n"
                + "WHERE row_index >=   (?-1)*? + 1\n"
                + "AND row_index <= ? * ?\n";

        PreparedStatement stm = null;
        ResultSet rs = null;

        String rid_query = " and rid = ?";

        try {
            if (from != null && to == null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(after_query_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, rid);
                    stm.setInt(4, pageindex);
                    stm.setInt(5, pagesize);
                    stm.setInt(6, pageindex);
                    stm.setInt(7, pagesize);
                } else {
                    stm = connection.prepareStatement(after_query);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, pageindex);
                    stm.setInt(4, pagesize);
                    stm.setInt(5, pageindex);
                    stm.setInt(6, pagesize);
                }

            }
            if (from == null && to != null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(before_query_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, rid);
                    stm.setInt(4, pageindex);
                    stm.setInt(5, pagesize);
                    stm.setInt(6, pageindex);
                    stm.setInt(7, pagesize);
                } else {
                    stm = connection.prepareStatement(before_query);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, pageindex);
                    stm.setInt(4, pagesize);
                    stm.setInt(5, pageindex);
                    stm.setInt(6, pagesize);
                }
            }
            if (from != null && to != null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(afterAndBefore_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setTimestamp(3, to);
                    stm.setInt(4, rid);
                    stm.setInt(5, pageindex);
                    stm.setInt(6, pagesize);
                    stm.setInt(7, pageindex);
                    stm.setInt(8, pagesize);
                } else {
                    stm = connection.prepareStatement(afterAndBefore);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setTimestamp(3, to);
                    stm.setInt(4, pageindex);
                    stm.setInt(5, pagesize);
                    stm.setInt(6, pageindex);
                    stm.setInt(7, pagesize);
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                EmployeeDocument ed = new EmployeeDocument();
                Document d = new Document();
                d.setDid(rs.getInt("did"));
                d.setDname(rs.getString("dname"));
                ed.setEid(rs.getInt("eid"));
                ed.setContent(rs.getBytes("content"));
                ed.setModifiedDate(rs.getTimestamp("modifiedDate"));
                ed.setDatetime(rs.getTimestamp("from"));
                ed.setDoc(d);
                ed.setMeid(rs.getInt("meid"));
                eDocs.add(ed);

            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return eDocs;

    }

    public int getCountEDocumentByKidRidFromTo(int kid, Timestamp from, Timestamp to, int rid) {
        int total = 0;

        String afterAndBefore = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and ed.[from] <= ?) tbl\n";

        String afterAndBefore_rid = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and ed.[from] <= ? and em.rid = ?) tbl\n";

        String after_query = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ?) tbl\n";

        String after_query_rid = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] >= ? and em.rid = ?) tbl\n";

        String before_query = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] <= ?) tbl\n";

        String before_query_rid = " SELECT count(*) as total FROM \n"
                + "(select ed.eid,d.did,d.dname,ed.content,ed.modifiedDate,ed.[from],em.rid,ed.meid,ROW_NUMBER() OVER (ORDER BY ed.[from] ASC) as row_index from EmployeeDocument ed join Document d\n"
                + "on ed.did = d.did join Employee em \n"
                + "on em.eid = ed.eid where kID = ? and ed.[from] <= ? and em.rid = ?) tbl\n";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            if (from != null && to == null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(after_query_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, rid);
                   
                } else {
                    stm = connection.prepareStatement(after_query);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                  
                }

            }
            if (from == null && to != null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(before_query_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setInt(3, rid);
                 
                } else {
                    stm = connection.prepareStatement(before_query);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                 
                }
            }
            if (from != null && to != null) {
                if (rid >= 0) {
                    stm = connection.prepareStatement(afterAndBefore_rid);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setTimestamp(3, to);
                    stm.setInt(4, rid);
                   
                } else {
                    stm = connection.prepareStatement(afterAndBefore);
                    stm.setInt(1, kid);
                    stm.setTimestamp(2, from);
                    stm.setTimestamp(3, to);
                   
                }
            }

            rs = stm.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");

            }

        } catch (SQLException ex) {
            Logger.getLogger(EDocumentDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }

        return total;

    }

}
