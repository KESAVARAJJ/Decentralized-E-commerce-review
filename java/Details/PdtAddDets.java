/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Details;

import connection.DB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alaguraj
 */
public class PdtAddDets {

    DB db = new DB();
    ResultSet rsz, rsy, rsx, rsw;

//Product Code 
    public String pdtcode() throws SQLException {
        String pcode = "";
        int ccde = 0, cde = 0;
        rsz = db.Select("select max(ID) from products");
        if (rsz.next()) {
            cde = rsz.getInt(1);
            ccde = cde + 1;
        } else {
            ccde = 1;
        }
        pcode = "SHP" + ccde;
        return pcode;

    }
    //Camera Product Own ID 

       public String camownid(String brand, String fname) throws SQLException {
        String onids = "";
        int ccde = 0, cde = 0;
        rsz = db.Select("select * from camera where Brand='" + brand + "' and Name='" + fname + "'");
        if (rsz.next()) {
            onids = rsz.getString("ID");
        }
        return onids;
    }
       
}