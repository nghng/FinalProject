/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.document;

import controller.BaseAuthController;
import dal.EDocumentDBContext;
import dal.EmployeeDBContext;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
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
public class ViewEmployeeDocument extends BaseAuthController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EmployeeDBContext authdb = new EmployeeDBContext();
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        EDocumentDBContext edb = new EDocumentDBContext();
        int eid = Integer.parseInt(request.getParameter("eid"));
        int did = Integer.parseInt(request.getParameter("did"));
        Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));
        if (authdb.getPermissionForUsingDoc(employee.getRole().getRid(), did, 2)) {

            byte[] pdf = edb.getContent(eid, did, datetime); // Load PDF byte[] into here
            if (pdf != null) {

                response.setContentType("application/pdf");
                OutputStream outs = response.getOutputStream();
                outs.write(pdf);
                outs.flush();
                outs.close();

            }
        }else{
              response.sendRedirect(request.getContextPath() + "/view/failedEdit.jsp");
        }

    }

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
        processRequest(request, response);
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
        processRequest(request, response);
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
