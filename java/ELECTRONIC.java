/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import connection.DB;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author Admin1
 */
@WebServlet(urlPatterns = {"/ELECTRONIC"})
public class ELECTRONIC extends HttpServlet {

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
            throws ServletException, IOException, FileUploadException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session=request.getSession(true);
       
        try {
         
            
            String PCODE="",PTYPE="",PBRAND="",PQTITY="",PPRICE="",PWTTY="",P_DESC="",S_Name="";
            String saveFile="",l="",ln="",mname="",dob="",celno="",typ="";
                String sex="",voterno="",email="",bg="",pstreet="",cstreet="",parea="",carea="",pcity="",ccity="";
                   String ppinno="",cpinno="",pass="",repass="";
            int fileidnum=0,downloadcount=0,vc=0;
           // String contentType = request.getContentType();
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();

// Set factory constraints
            factory.setSizeThreshold(4012);
//factory.setRepository("c:");

// Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

// Set overall request size constraint
            //upload.setSizeMax(10024);

// Parse the request
            List items = null;
            try {
                items = upload.parseRequest(new ServletRequestContext(request));
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            byte[] data = null;
            String fileName = null;
// Process the uploaded items
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (item.isFormField()) {
                    //processFormField(item);

                    String name = item.getFieldName();
                    String value = item.getString();

                    if (name.equalsIgnoreCase("PCODE")) {
                        PCODE = value;
                        System.out.println("PCODE" + PCODE);
                    } 
                    
                  else  if (name.equalsIgnoreCase("PTYPE")) {
                        PTYPE = value;
                        System.out.println("PTYPE" + PTYPE);
                    } 
                  
                  else  if (name.equalsIgnoreCase("PBRAND")) {
                        PBRAND = value;
                        System.out.println("PBRAND" + PBRAND);
                    }
                    else  if (name.equalsIgnoreCase("PQTITY")) {
                        PQTITY = value;
                        System.out.println("PQTITY" + PQTITY);
                    }
                  else  if (name.equalsIgnoreCase("PPRICE")) {
                        PPRICE = value;
                        System.out.println("PPRICE" + PPRICE);
                    }
                  else  if (name.equalsIgnoreCase("PWTTY")) {
                        PWTTY = value;
                        System.out.println("PWTTY" + PWTTY);
                    }
                  else  if (name.equalsIgnoreCase("P_DESC")) {
                        P_DESC = value;
                        System.out.println("P_DESC" + P_DESC);
                    }
                  else  if (name.equalsIgnoreCase("S_Name")) {
                        S_Name = value;
                        System.out.println("S_Name" + S_Name);
                    }
                    else {
                        System.out.println("ERROR");
                    }
                } else {
                    data = item.get();
                    fileName = item.getName();
                }
            }
            saveFile = fileName;
                    String path1 = request.getSession().getServletContext().getRealPath("/");
              // String patt=path.replace("\\build", "");
               
               String strPath1 = path1+"\\"+saveFile;
            File ff1 = new File(strPath1);
            FileOutputStream fileOut1 = new FileOutputStream(ff1);
            fileOut1.write(data, 0, data.length);
            fileOut1.flush();
            fileOut1.close();
        out.println(saveFile);
        /////////////////////////////////////////////////////////

FileInputStream fis11 = null;
File image1 = null;
//FileInputStream fis11 = null;
File image11 = null;
	Connection con7=null;
	PreparedStatement st7=null;
      
       
PreparedStatement st11=null;

image1 = new File(strPath1);
fis11 = new FileInputStream(image1);
        
       

     Class.forName("com.mysql.jdbc.Driver");
     con7 = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commers","root","admin");
  
 
   
     
         
String query="Select * from products where PCODE='"+PCODE+"'";
System.out.println(query);
            Statement st=con7.createStatement();
ResultSet rs=st.executeQuery(query);

if(rs.next())
{
    
   session.setAttribute("msg","Already exist Please Check Values");
      response.sendRedirect("ELECTRONIC.jsp");  
}
else
{
        st7 =con7.prepareStatement("insert into products values (?,?,?,?,?,?,?,?,?,'YES','ELECTRONIC',?)");

st7.setInt(1,0);
st7.setString(2,PCODE);
st7.setString(3,PTYPE);
st7.setString(4,PBRAND);
st7.setString(5,PQTITY);
st7.setString(6,PPRICE);
st7.setString(7,PWTTY);
st7.setString(8,P_DESC);
st7.setString(10,S_Name);
if(fileName != "")
        st7.setBinaryStream(9, (InputStream)fis11, (int)(image1.length()));
else
    st7.setBinaryStream(9, null);

      int i =st7.executeUpdate();
      session.setAttribute("msg","Successfully Upload");
      response.sendRedirect("ELECTRONIC.jsp");
}   
      
        }
        catch(Exception e)
        {
       out.println(e);
        }
        finally {            
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
        } catch (FileUploadException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (FileUploadException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CLOTHING.class.getName()).log(Level.SEVERE, null, ex);
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
