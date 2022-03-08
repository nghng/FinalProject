/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.document;

import controller.BaseAuthController;
import dal.EDocumentDBContext;
import dal.EmployeeDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;
import model.EmployeeDocument;

/**
 *
 * @author admin
 */
public class DisplayEmployeeDocument extends BaseAuthController {

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
    @Override
    protected void processGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employee employee = new Employee();
        int did = Integer.parseInt(this.getInitParameter("did"));
        employee = (Employee) request.getSession().getAttribute("employee");
        EmployeeDBContext edb = new EmployeeDBContext();
        if (edb.getPermissionForUsingDoc(employee.getRole().getRid(),
                did, 2) == false) {
            response.getWriter().print("Bạn không thể xem tài liệu này");
        } else {
            EDocumentDBContext eddb = new EDocumentDBContext();
            ArrayList<EmployeeDocument> edocs = eddb.getEDocumentByEidDid(employee.getEid(), did);
            request.setAttribute("edocs", edocs);
            response.getWriter().print("Bạn có thể xem tài liệu này");

        }

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