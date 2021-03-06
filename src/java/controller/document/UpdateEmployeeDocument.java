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
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Employee;

/**
 *
 * @author admin
 */
public class UpdateEmployeeDocument extends BaseAuthController {

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
        employee = (Employee) request.getSession().getAttribute("employee");
        EmployeeDBContext edb = new EmployeeDBContext();
        int did = -1;

        try {
            did = Integer.parseInt(request.getParameter("did"));
        } catch (Exception e) {
        }
        if (edb.getPermissionForUsingDoc(employee.getRole().getRid(), did, 1)) {
            request.getRequestDispatcher("../../../../view/document/updateEmployeeDocument.jsp").forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/view/failedEdit.jsp");
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
        Employee employee = new Employee();
        int eid = -1;
        int did = -1;
        Timestamp from = null;
        String error = "";
        EDocumentDBContext eddb = new EDocumentDBContext();
        Boolean success = false;
        employee = (Employee) request.getSession().getAttribute("employee");
        boolean upload = true;
        try {
            eid = Integer.parseInt(request.getParameter("eid"));
            from = Timestamp.valueOf(request.getParameter("datetime"));
            did = Integer.parseInt(request.getParameter("did"));

        } catch (Exception e) {
        }
       
            response.setContentType("text/html;charset=UTF-8");
            final Part filePart = request.getPart("file");
            InputStream pdfFileBytes = null;

            try {
                if (!filePart.getContentType().equals("application/pdf")) {
                    error = "File b???n g???i kh??ng c?? ph???i file pdf";
                    upload = false;
                } else if (filePart.getSize() > 1048576) { //2mb
                    {
                        error = "File g???i l??n qu?? l???n (>2mb)";
                        upload = false;

                    }
                }

                pdfFileBytes = filePart.getInputStream();
                final byte[] bytes = new byte[pdfFileBytes.available()];
                pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.
                try {
                    if (upload) {
                        success = eddb.updateDocument(eid, did, from, bytes,employee.getEid());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(UpdateEmployeeDocument.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (Exception e) {
                error = "B???n ch??a ch???n file n??o ????? g???i";

            }

            request.setAttribute("error", error);
            request.setAttribute("success", success);
            request.getRequestDispatcher("../../../../view/document/updateEmployeeDocument.jsp").forward(request, response);

        
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
