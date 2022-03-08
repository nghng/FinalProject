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
import model.Role;

/**
 *
 * @author admin
 */
public class RoleDBContext extends DBContext{
    public ArrayList<Role> getRoles()
    {
        ArrayList<Role> roles = new ArrayList<>();
        try {
            String sql = "SELECT rid,rname FROM Role";
            PreparedStatement stm = connection.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Role r = new Role();
                r.setRid(rs.getInt("rid"));
                r.setRname(rs.getString("rname"));
                roles.add(r);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RoleDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return roles;
    }
}
