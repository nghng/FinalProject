/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class Employee {

    private int eid;
    private String fullname;
    private Role role;
    private Date dob;
    private boolean gender;
    private Kindergarten kinder;
    private String password;

    public Employee() {
    }

    public Employee(int eid, String fullname, Role role, Date dob, boolean gender, Kindergarten kinder, String password) {
        this.eid = eid;
        this.fullname = fullname;
        this.role = role;
        this.dob = dob;
        this.gender = gender;
        this.kinder = kinder;
        this.password = password;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Kindergarten getKinder() {
        return kinder;
    }

    public void setKinder(Kindergarten kinder) {
        this.kinder = kinder;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    static public ArrayList<Employee> getEmployeeByNameEid(ArrayList<Employee> employees, String raw_fullName, int eid) throws UnsupportedEncodingException {
        ArrayList<Employee> result = new ArrayList<>();
        if (raw_fullName != null && raw_fullName.trim().length() > 0) {
            for (Employee e : employees) {
                e.getFullname();
                if (e.getFullname().equals(raw_fullName)) {
                    result.add(e);
                }
            }
        }

        if (eid > -1) {
            for (Employee e : employees) {
                if (e.getEid() == eid) {
                    result.add(e);
                }
            }
        }

        return result;
    }

}
