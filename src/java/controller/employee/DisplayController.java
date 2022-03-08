/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.employee;

import controller.BaseAuthController;
import dal.EmployeeDBContext;
import dal.RoleDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;
import model.Kindergarten;
import model.Role;

/**
 *
 * @author admin
 */
public class DisplayController extends BaseAuthController {

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
        int pagesize = 10;
        String page = request.getParameter("page");
        if (page == null || page.trim().length() == 0) {
            page = "1";
        }
        int pageindex = Integer.parseInt(page);
        RoleDBContext rDB = new RoleDBContext();
        ArrayList<Role> roles = rDB.getRoles();
        request.setAttribute("roles", roles);

        String raw_rid = request.getParameter("rid");
        if (raw_rid == null || raw_rid.trim().length() == 0) {
            raw_rid = "-1";
        }
        int rid = Integer.parseInt(raw_rid);

        Kindergarten kindergarten = (Kindergarten) request.getSession().getAttribute("kindergarten");

        EmployeeDBContext eDB = new EmployeeDBContext();
        ArrayList<Employee> employees = eDB.getEmployeeForPaging(pageindex, pagesize, 
                kindergarten.getKid(),rid);

        int count = eDB.count();
        int totalpage = (count % pagesize == 0) ? (count / pagesize) : (count / pagesize) + 1;

        request.setAttribute("totalpage", totalpage);
        request.setAttribute("pageindex", pageindex);

        request.setAttribute("employees", employees);
        request.setAttribute("rid", rid);

        request.getRequestDispatcher("../../view/Employee/displayEmployee.jsp").forward(request, response);
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
