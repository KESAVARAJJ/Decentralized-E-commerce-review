/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import connection.DB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Admin1
 */
@WebServlet(urlPatterns = {"/USER_LOG"})
public class USER_LOG extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(true);
        try {
            
            String MAIL_ID=request.getParameter("MAIL_ID");
            String Password=request.getParameter("Password");
            System.out.println(MAIL_ID);
             System.out.println(Password);
             System.out.println("Select * from user_reg where MAIL_ID='"+MAIL_ID+"' and Password='"+Password+"'");
            DB db=new DB();
            ResultSet rs=db.Select("Select * from user_reg where MAIL_ID='"+MAIL_ID+"' and Password='"+Password+"'");
              if(rs.next())
            {
              String st=rs.getString("STS");
              if(st.equals("Rejected"))
              {
                  session.setAttribute("msg","YOUR REQUEST WAS REJECTED!...........");
                  response.sendRedirect("USER_LOG.jsp");
              }
              else
               if(st.equals("BLOCK"))
              {
                  session.setAttribute("msg","YOUR BLOCKET!...........");
                  response.sendRedirect("USER_LOG.jsp");
              }
            else
                if(st.equals("NO"))
              {
                  session.setAttribute("msg","Approval Is Must!...........");
                  response.sendRedirect("USER_LOG.jsp");
              }
              else
                if((MAIL_ID.equalsIgnoreCase(rs.getString("MAIL_ID")))&&(Password.equalsIgnoreCase(rs.getString("Password"))))
                {
                   String name1=rs.getString("MAIL_ID");
                session.setAttribute("msg","Successfully Login");
                session.setAttribute("name",name1);
                response.sendRedirect("USER_HOME.jsp");
                }
                else
                {
                session.setAttribute("msg","Invalid user name and password");
                response.sendRedirect("USER_LOG.jsp");
                }
             
            }
            else
            {
               session.setAttribute("msg","Invalid user name and password");
                response.sendRedirect("USER_LOG.jsp");
                
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(USER_LOG.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(USER_LOG.class.getName()).log(Level.SEVERE, null, ex);
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
