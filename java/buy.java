/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.DB;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mvinoth
 */
@WebServlet(urlPatterns = {"/buy"})
public class buy extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(true);
        try {
          String P_Id,S_Name,T_NUM,quantity,Pri,uname,S_ame;
          P_Id=request.getParameter("P_Id");
          S_Name=request.getParameter("S_Name");
           S_ame=request.getParameter("S_ame");
          T_NUM=request.getParameter("T_NUM");
          quantity=request.getParameter("quantity");
          Pri=request.getParameter("Pri");
          uname=(String)session.getAttribute("name");
           DB db=new DB();
         int i=db.Insert("insert into buy_products(P_Id,S_Name,S_ame,T_NUM,quantity,Pri,uname) values ('"+P_Id+"','"+S_Name+"','"+S_ame+"','"+T_NUM+"','"+quantity+"','"+Pri+"','"+uname+"')");
         if (i > 0) {
                // Success, set message in session
                session.setAttribute("msg", "Successfully bought the product.");
                response.sendRedirect("USER_HOME.jsp");  // Redirect to USER_HOME.jsp
            } else {
                // Database error, set message in session
                session.setAttribute("msg", "Database error, please try again.");
                response.sendRedirect("Buy.jsp");  // Redirect to Buy.jsp
            }
                 
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
