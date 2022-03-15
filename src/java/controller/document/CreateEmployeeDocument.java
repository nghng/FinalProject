/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.document;

import controller.BaseAuthController;
import dal.DocumentDBContext;
import dal.EDocumentDBContext;
import dal.RoleDBContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import static java.sql.Timestamp.from;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import model.Document;
import model.Employee;

/**
 *
 * @author admin
 */
public class CreateEmployeeDocument extends BaseAuthController {

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
        DocumentDBContext ddb = new DocumentDBContext();
        ArrayList<Document> docs = new ArrayList<>();
        Employee employee = (Employee) request.getSession().getAttribute("employee");

        docs = ddb.getDocNamebyRid(employee.getRole().getRid());
        request.setAttribute("docs", docs);
        request.getRequestDispatcher("../../view/document/createEmployeeDocument.jsp").forward(request, response);

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
        String error = "";
        Boolean success = false;
        boolean upload = true;
        int did = -1;
        EDocumentDBContext eddb = new EDocumentDBContext();
        Employee employee = (Employee) request.getSession().getAttribute("employee");
        DocumentDBContext ddb = new DocumentDBContext();
        ArrayList<Document> docs = new ArrayList<>();

        try {
            did = Integer.parseInt(request.getParameter("did"));
        } catch (Exception e) {
        }
        response.setContentType("text/html;charset=UTF-8");
        final Part filePart = request.getPart("file");
        InputStream pdfFileBytes = null;

        try {
            if (!filePart.getContentType().equals("application/pdf")) {
                error = "File bạn gửi không có phải file pdf";
                upload = false;
            } else if (filePart.getSize() > 1048576) { //2mb
                {
                    error = "File gửi lên quá lớn (>2mb)";
                    upload = false;

                }
            }

            pdfFileBytes = filePart.getInputStream();
            final byte[] bytes = new byte[pdfFileBytes.available()];
            pdfFileBytes.read(bytes);  //Storing the binary data in bytes array.
            if (upload) {
                success = eddb.createEDoc(employee.getEid(), did, bytes);
            }
        } catch (Exception e) {
            error = "Bạn chưa chọn file nào để gửi";

        }
        docs = ddb.getDocNamebyRid(employee.getRole().getRid());
        request.setAttribute("docs", docs);
        request.setAttribute("error", error);
        request.setAttribute("success", success);
        request.getRequestDispatcher("../../view/document/createEmployeeDocument.jsp").forward(request, response);

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
