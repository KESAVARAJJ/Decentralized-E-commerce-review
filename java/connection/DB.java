/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mvinoth
 */
 public class DB {
    private Connection connection;
  public  Statement st=null;
   public Connection con=null;
   public  DB()
     {
         try
         {
           Class.forName("com.mysql.jdbc.Driver");
           con=DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commers","root","admin");
           st=con.createStatement();
         }
         catch(Exception e)
         {
             System.out.println("Class Error"+e);
         }
     }
    public ResultSet Select(String query)
     {
         ResultSet rs=null;
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
           System.out.println("Select Query Error"+ex);
        }
         return rs;
        
        
     }
 public  int Insert(String query)
     {
         int i=0;
        try {
            i=st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Select Query Error"+ex);
        }
        return i;
     }
 
  public  int Update(String query)
     {
         int i=0;
        try {
            i=st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Select Query Error"+ex);
        }
        return i;
     }
  public ResultSet Select(String query, String parameter) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);
            return statement.executeQuery();
        }
     
  }
 }
