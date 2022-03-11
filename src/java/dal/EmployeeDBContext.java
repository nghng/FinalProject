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
import model.Employee;
import model.Kindergarten;
import model.Role;

/**
 *
 * @author admin
 */
public class EmployeeDBContext extends DBContext {

    public Employee getEmployee(int eid, String password) {
        try {
            String sql = "select e.eid,e.fullName,e.rID,e.dob,e.gender,k.kid,k.kname,e.[password],r.rname,k.kname\n"
                    + " from Employee e join [Role] r\n"
                    + "on e.rID = r.rid join Kindergarten k on\n"
                    + "k.kID = e.kID where e.eid = ? and e.[password] = ? ";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Employee e = new Employee();
                Role r = new Role();
                Kindergarten k = new Kindergarten();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                e.setEid(rs.getInt("eid"));
                e.setFullname(rs.getString("fullname"));
                e.setRole(r);
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setKinder(k);
                e.setPassword(rs.getString("password"));
                return e;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getPermission(int eid, String url) {
        try {
            String sql = "select count(*) as total from Employee e join GroupFeature gf\n"
                    + "on e.rID = gf.rid join Feature f on f.fid = gf.fid\n"
                    + "where eid = ? and url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, eid);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public ArrayList<Employee> getEmployeeByRole(int kid, int rid) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            String sql = "select e.eid,e.fullName,e.rID,e.dob,e.gender,k.kid,k.kname,e.[password],r.rname,k.kname\n"
                    + " from Employee e join [Role] r\n"
                    + "on e.rID = r.rid join Kindergarten k on\n"
                    + "k.kID = e.kID where e.kid = ? and e.rid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, kid);
            stm.setInt(2, rid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Employee e = new Employee();
                Role r = new Role();
                Kindergarten k = new Kindergarten();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                e.setEid(rs.getInt("eid"));
                e.setFullname(rs.getString("fullname"));
                e.setRole(r);
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setKinder(k);
                e.setPassword(rs.getString("password"));
                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;

    }

    public ArrayList<Employee> getEmployeeForPaging(int pageindex, int pagesize, int kid, int rid) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            PreparedStatement stm = null;
            String sql = "SELECT * FROM \n"
                    + "            	(Select e.eid,e.fullName,e.rID,e.dob,e.gender,k.kid,k.kname,e.[password],r.rname\n"
                    + "                      ,ROW_NUMBER() OVER (ORDER BY e.eid ASC) as row_index from Employee e join [Role] r\n"
                    + "                     on e.rID = r.rid join Kindergarten k on\n"
                    + "                     k.kID = e.kID) tbl\n"
                    + "            	WHERE row_index >=   (?-1)*? + 1\n"
                    + "            	AND row_index <= ? * ? and kid = ?";

            String sql_rid = "SELECT * FROM \n"
                    + "            	(Select e.eid,e.fullName,e.rID,e.dob,e.gender,k.kid,k.kname,e.[password],r.rname\n"
                    + "                      ,ROW_NUMBER() OVER (ORDER BY e.eid ASC) as row_index from Employee e join [Role] r\n"
                    + "                     on e.rID = r.rid join Kindergarten k on\n"
                    + "                     k.kID = e.kID where r.rid = ?) tbl\n"
                    + "            	WHERE row_index >=   (?-1)*? + 1\n"
                    + "            	AND row_index <= ? * ? and kid = ?";

            if (rid >= 0) {
                stm = connection.prepareStatement(sql_rid);
                stm.setInt(1, rid);
                stm.setInt(2, pageindex);
                stm.setInt(3, pagesize);
                stm.setInt(4, pageindex);
                stm.setInt(5, pagesize);
                stm.setInt(6, kid);
            } else {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, pageindex);
                stm.setInt(2, pagesize);
                stm.setInt(3, pageindex);
                stm.setInt(4, pagesize);
                stm.setInt(5, kid);
            }

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Employee e = new Employee();
                Role r = new Role();
                Kindergarten k = new Kindergarten();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                k.setKid(rs.getInt("kid"));
                k.setKname(rs.getString("kname"));
                e.setEid(rs.getInt("eid"));
                e.setFullname(rs.getString("fullname"));
                e.setRole(r);
                e.setDob(rs.getDate("dob"));
                e.setGender(rs.getBoolean("gender"));
                e.setKinder(k);
                e.setPassword(rs.getString("password"));
                employees.add(e);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employees;
    }

    public int count(int kid, int rid) {
        try {
            PreparedStatement stm = null;
            String sql = "SELECT COUNT(*) as Total FROM Employee where kid = ?";

            if (rid >= 0) {
                sql += " and rid = ?";
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);
                stm.setInt(2, rid);
            } else {
                stm = connection.prepareStatement(sql);
                stm.setInt(1, kid);
            }

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean getPermissionForUsingDoc(int rid, int did, int authid) {
        // authid 1: modify+delete 
        //    2: view
        String sql = "select count(*) as total from Document d join GroupDocument gd \n"
                + "on d.did = gd.did join [Role] r\n"
                + "on r.rid = gd.rid join Auth a	\n"
                + "on a.authid = gd.authID \n"
                + "where r.rid = ? and d.did = ? and a.authid = ?";
        int total = 0;
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, rid);
            stm.setInt(2, did);
            stm.setInt(3, authid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
            if (total > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
   
}
