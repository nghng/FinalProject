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
                    + "k.kID = e.kID where e.eid = ? and e.[password] = ?";
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
}
