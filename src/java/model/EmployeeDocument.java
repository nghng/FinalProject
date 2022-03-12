/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class EmployeeDocument {

    private int eid;
    private Document doc;
    private Timestamp datetime;
    private byte[] content;
    private Timestamp modifiedDate;

    public EmployeeDocument() {
    }

    public EmployeeDocument(int eid, Document doc, Timestamp datetime, byte[] content, Timestamp modifiedDate) {
        this.eid = eid;
        this.doc = doc;
        this.datetime = datetime;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}
