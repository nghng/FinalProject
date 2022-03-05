/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class Kindergarten {
    private int kid;
    private String kname;

    public Kindergarten() {
    }

    public Kindergarten(int kid, String kname) {
        this.kid = kid;
        this.kname = kname;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getKname() {
        return kname;
    }

    public void setKname(String kname) {
        this.kname = kname;
    }
    
    
}
