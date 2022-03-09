/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.document;

import dal.EDocumentDBContext;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.EmployeeDocument;

/**
 *
 * @author admin
 */
public class ViewEmployeeDocument extends HttpServlet {

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

        EDocumentDBContext edb = new EDocumentDBContext();
        int eid = Integer.parseInt(request.getParameter("eid"));
        int did = Integer.parseInt(request.getParameter("did"));
        Timestamp datetime = Timestamp.valueOf(request.getParameter("datetime"));

        byte[] pdf = edb.getContent(eid, did, datetime); // Load PDF byte[] into here
        if (pdf != null) {

            response.setContentType("application/pdf");
            OutputStream outs = response.getOutputStream();
            outs.write(pdf);
            outs.flush();
            outs.close();

            // set pdf content
//            response.setContentType("application/pdf");
//            // if you want to download instead of opening inline
//            // response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
//            // write the content to the output stream
//            BufferedOutputStream fos1 = new BufferedOutputStream(
//                    response.getOutputStream());
//            fos1.write(pdf);
//            fos1.flush();
//            fos1.close();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
