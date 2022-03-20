/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.EmployeeDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Employee;

/**
 *
 * @author admin
 */
public class LoginController extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Employee e = (Employee) request.getSession().getAttribute("employee");
        if (e != null) {
            if (e.getRole().getRid() != 0) {
                request.getRequestDispatcher("kindergarten/detail?kid=" + e.getKinder().getKid()).forward(request, response);
            } else {
                request.getRequestDispatcher("home" + e.getKinder().getKid()).forward(request, response);

            }

        }
        request.getRequestDispatcher("view/login.jsp").forward(request, response);

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
        int eid = -1;
        try {
            eid = Integer.parseInt(request.getParameter("eid"));
        } catch (Exception e) {

        }
        String password = request.getParameter("password");
        EmployeeDBContext db = new EmployeeDBContext();
        Employee employee = db.getEmployee(eid, password);
        if (employee == null) {
            request.getSession().setAttribute("employee", null);
            request.getSession().setAttribute("isLogin", "1");
            response.sendRedirect("login");

        } else {
            request.getSession().setAttribute("employee", employee);
            request.getSession().setAttribute("isLogin", "0");
            if (employee.getRole().getRid() != 0) {
                response.sendRedirect("kindergarten/detail?kid=" + employee.getKinder().getKid());
            } else {
                response.sendRedirect("home");

            }

        }
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
