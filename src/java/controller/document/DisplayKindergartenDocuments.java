/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.document;

import controller.BaseAuthController;
import dal.EDocumentDBContext;
import dal.RoleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;
import model.EmployeeDocument;
import model.Role;

/**
 *
 * @author admin
 */
public class DisplayKindergartenDocuments extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public Timestamp convertString(String raw_date) {
        if (raw_date != null && raw_date.trim().length() > 0) {
            raw_date += ":00";
            raw_date = raw_date.replace("T", " ");
            Timestamp datetime = Timestamp.valueOf(raw_date);
            return datetime;

        }
        return null;
    }

    public boolean checkString(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }

    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int count = 0;
        ArrayList<EmployeeDocument> edocs = new ArrayList<>();
        ArrayList<Role> roles = new ArrayList<>();
        EDocumentDBContext eddb = new EDocumentDBContext();
        RoleDBContext rdb = new RoleDBContext();
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        int rid = -1;
        int pagesize = 4;
        int totalpage = 0;
        Timestamp from = convertString(request.getParameter("from"));
        Timestamp to = convertString(request.getParameter("to"));
        String page = request.getParameter("page");
        if (page == null || page.trim().length() == 0) {
            page = "1";
        }
        int pageindex = Integer.parseInt(page);
        int kid = employee.getKinder().getKid();

        roles = rdb.getRoles();

        try {
            rid = Integer.parseInt(request.getParameter("rid"));
        } catch (Exception e) {
        }
        if (kid != -1 && from == null && to == null) {
            edocs = eddb.getEDocumentByKid(kid, rid, pageindex, pagesize);
            count = eddb.getCountEDocumentByKid(kid, rid);

        } else {
            edocs = eddb.getEDocumentByKidRidFromTo(kid, from, to, rid, pageindex, pagesize);
            count = eddb.getCountEDocumentByKidRidFromTo(kid, from, to, rid);
        }

        if (count > 0) {
            totalpage = (count % pagesize == 0) ? (count / pagesize) : (count / pagesize) + 1;

        }
        request.setAttribute("roles", roles);
        request.setAttribute("edocs", edocs);
        request.setAttribute("totalpage", totalpage);
        request.setAttribute("pageindex", pageindex);
        request.getRequestDispatcher("/view/document/displayKindergartenDocuments.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
