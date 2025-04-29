/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import javax.servlet.annotation.WebServlet;
/**
 *
 * 
 */
@WebServlet(urlPatterns = {"/sentiword"})
public class sentiword extends HttpServlet {
 String sent="";
    public static String[] stopWordsofwordnet = {
"without",  "unless", "due", "also", "must", "might",  "]", "[", "}", "{", "<", ">", "?", "\"", "\\", "/", ")", "(", "will", "may", "can", "much", "every", "the", "in", "other", "this", "the", "many", "any", "an", "or", "for", "in", "an", "an ", "is", "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", "could",
"couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having",
"he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i ", " i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is",
"isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "of", "off", "on", "once", "only", "ought", "our", "ours", "ourselves",
"out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than",
"that", "that's", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've",
"this", "those", "through", "to", "too", "under", "until", "up",  "was", "wasn't", "we", "we'd", "we'll", "we're", "we've",
"were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom",
"why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours",
"Without", "See", "Unless", "Due", "Also", "Must", "Might",  "Will", "May", "Can", "Much", "Every", "The", "In", "Other", "This", "The", "Many", "Any", "An", "Or", "For", "In", "An", "An ", "Is", "A", "About", "Above", "After", "Again", "Against", "All", "Am", "An", "And", "Any", "Are", "Aren't", "As", "At", "Be", "Because", "Been", "Before", "Being", "Below", "Between", "Both", "But", "By", "Can't", "Could",
"Couldn't", "Did", "Didn't", "Do", "Does", "Doesn't", "Doing", "Don't", "Down", "During", "Each", "Few", "For", "From", "Further", "Had", "Hadn't", "Has", "Hasn't", "Have", "Haven't", "Having",
"He", "He'd", "He'll", "He's", "Her", "Here", "Here's", "Hers", "Herself", "Him", "Himself", "His", "How", "How's", "I ", " I", "I'd", "I'll", "I'm", "I've", "If", "In", "Into", "Is",
"Isn't", "It", "It's", "Its", "Itself", "Let's", "Me", "More", "Most", "Mustn't", "My", "Myself", "No", "Nor",  "Of", "Off", "On", "Once", "Only", "Ought", "Our", "Ours", "Ourselves",
"Out", "Over", "Own", "Same", "Shan't", "She", "She'd", "She'll", "She's", "Should", "Shouldn't", "So", "Some", "Such", "Than",
"That", "That's", "Their", "Theirs", "Them", "Themselves", "Then", "There", "There's", "These", "They", "They'd", "They'll", "They're", "They've",
"This", "Those", "Through", "To", "Too", "Under", "Until", "Up",  "Was", "Wasn't", "We", "We'd", "We'll", "We're", "We've",
"Were", "Weren't", "What", "What's", "When", "When's", "Where", "Where's", "Which", "While", "Who", "Who's", "Whom",
"Why", "Why's", "With", "Won't", "Would", "Wouldn't", "You", "You'd", "You'll", "You're", "You've", "Your", "Yours", "Yourself", "Yourselves"
};

public static ArrayList<String> wordsList = new ArrayList<String>();
private String pathToSWN = "D:\\Final Project\\project2k25\\E_COMMERS\\AMAZ\\SentiWordNet_3.0.0_20130122.txt";
        private HashMap<String, Double> _dict;
        ArrayList list=new ArrayList();
        ArrayList list1=new ArrayList();
          ArrayList list2=new ArrayList();
       public sentiword(){
        
        _dict = new HashMap<String, Double>();
                HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
                try{
                    BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
                    String line = "";           
                    while((line = csv.readLine()) != null)
                    {
                        String[] data = line.split("\t");
                        Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
                        String[] words = data[4].split(" ");
                        for(String w:words)
                        {
                            String[] w_n = w.split("#");
                            w_n[0] += "#"+data[0];
                            int index = Integer.parseInt(w_n[1])-1;
                            if(_temp.equals(w_n[0]))
                            {
                                Vector<Double> v = _temp.get(w_n[0]);
                                if(index>v.size())
                                    for(int i = v.size();i<index; i++)
                                        v.add(0.0);
                                v.add(index, score);
                                _temp.put(w_n[0], v);
                            }
                            else
                            {
                                Vector<Double> v = new Vector<Double>();
                                for(int i = 0;i<index; i++)
                                    v.add(0.0);
                                v.add(index, score);
                                _temp.put(w_n[0], v);
                            }
                        }
                    }
                    Set<String> temp = _temp.keySet();
                    for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                        String word = (String) iterator.next();
                        Vector<Double> v = _temp.get(word);
                        double score = 0.0;
                        double sum = 0.0;
                        for(int i = 0; i < v.size(); i++)
                            score += ((double)1/(double)(i+1))*v.get(i);
                        for(int i = 1; i<=v.size(); i++)
                            sum += (double)1/(double)i;
                        score /= sum;
                        String sent = "";               
                        if(score>=0.75)
                            sent = "strong_positive";
                        else
                    if(score > 0.25 && score<=0.5)
                        sent = "positive";
                    else
                    if(score > 0 && score>=0.25)
                        sent = "weak_positive";
                    else
                    if(score < 0 && score>=-0.25)
                        sent = "weak_negative";
                    else
                    if(score < -0.25 && score>=-0.5)
                        sent = "negative";
                    else
                    if(score<=-0.75)
                        sent = "strong_negative";
                    _dict.put(word, score);
                    //System.out.println(word+","+score);
                }
            }
            catch(Exception e){e.printStackTrace();}   
        
        }
        
        public Double extract(String word)
{
    Double total = new Double(0);
    if(_dict.get(word+"#n") != null)
         total = _dict.get(word+"#n") + total;
    if(_dict.get(word+"#a") != null)
        total = _dict.get(word+"#a") + total;
    if(_dict.get(word+"#r") != null)
        total = _dict.get(word+"#r") + total;
    if(_dict.get(word+"#v") != null)
        total = _dict.get(word+"#v") + total;
    return total;
}
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
        String sentence=request.getParameter("Feedback");
        String username=request.getParameter("username");
            // String ss=request.getParameter("cat");;
        String Vehicle_Id=request.getParameter("Vehicle_Id");;
        String Email=request.getParameter("Email");
            String Model_Name=request.getParameter("Model_Name");
            String loc1=request.getParameter("loc1");
            String lat=request.getParameter("lat");
            String lan=request.getParameter("lan");
          ArrayList aa=new ArrayList();
          String ip= request.getRemoteHost();
         String sen="";
String sss=sentence.toLowerCase();
sss=sss.trim().replaceAll("\\s+", " ");
String[] words1 = sss.split(" ");
if(words1.length<0){
wordsList.add("empty");
}
for (String word1 : words1) {
wordsList.add(word1);
}

//remove stop words here from the temp list
for (int i = 0; i < wordsList.size(); i++) {
// get the item as string
for (int j = 0; j < stopWordsofwordnet.length; j++) {
if (stopWordsofwordnet[j].equals(wordsList.get(i))) {
wordsList.remove(i);
}
}
}
for (String str : wordsList) {
aa.add(str);
}  
      out.println(aa);

        try {
           sentiword test = new sentiword();
           if(aa.size()<0){
           aa.add("empty");
           }
    //String sentence="The SentiWordNet interface not provides the possibily to accept user feedback on the values assigned to synsets.";
    String[] words = aa.toString().split("\\s+");  
    double totalScore = 0;
    for(String word : words) {
        word = word.replaceAll("([^a-zA-Z\\s])", "");
        if (test.extract(word) == null)
            continue;
        totalScore += test.extract(word);
      //  System.out.println(word+":"+test.extract(word));
         if(test.extract(word)==0.0){
         System.out.println( "Opinion"+word+":"+test.extract(word));
        list.add(word+":"+test.extract(word)+"\n");
        }
         if(test.extract(word)>0.0){
         list2.add(word+":"+test.extract(word)+"\n");
         }
         if(test.extract(word)<0.0){
         list1.add(word+":"+test.extract(word)+"\n");
         }
    }
        Connection con=null;
	Statement st=null;
String s=list.toString();

String s1=list1.toString();
String s2=list2.toString();
try{

     Class.forName("com.mysql.jdbc.Driver");
     con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_car_bike","root","admin");
      
        Statement st6=con.createStatement();
        ResultSet rs6=st6.executeQuery("select * from review where User_Name='"+username+"' and Vehicle_Id='"+Vehicle_Id+"'");
   
    if(rs6.next())
    {list.clear();
      list1.clear();
      list2.clear();
      aa.clear();
      wordsList.clear();
      sent="";
       response.sendRedirect("Review.jsp?Vehicle_Id="+Vehicle_Id+"&User_Name="+username+"&msg=Duplicate comment"+"&Model_Name="+Model_Name);
    }
    
    else{
    
    
    
        try{

     Class.forName("com.mysql.jdbc.Driver");
     con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rental_car_bike","root","admin");
        st =con.createStatement();
        Statement st1=con.createStatement();
        ResultSet rs5=st1.executeQuery("select Max(Total) as total from review where Vehicle_Id='"+Vehicle_Id+"' and Model_Name='"+Model_Name+"'");
   double totalScore1 = 0;
  if(rs5.next())
   {
      totalScore1=rs5.getDouble("Total")+totalScore;
   }
       
  String query="insert into review values ('"+username+"','"+sentence+"','"+ new java.util.Date() +"','"+totalScore+"','"+s+"','"+s2+"','"+Model_Name+"','"+Vehicle_Id+"','"+totalScore1+"','"+s1+"','"+ip+"','"+loc1+"','"+lat+"','"+lan+"','"+Email+"')";
  
      st.executeUpdate(query);
      String query12="update review set Total='"+totalScore1+"' where Vehicle_Id='"+Vehicle_Id+"' and Model_Name='"+Model_Name+"'";
            st.executeUpdate(query12);
      list.clear();
      list1.clear();
      list2.clear();
      aa.clear();
      wordsList.clear();
      sent="";
     
       response.sendRedirect("Review.jsp?Vehicle_Id="+Vehicle_Id+"&Model_Name="+Model_Name+"&User_Name="+username);
     
       }
catch(Exception e)
        {
             System.out.println("error"+e);
    out.println(e);
            }}}catch(Exception e)
        {
             System.out.println("error"+e);
    out.println(e);
            }
    
    out.println("Total Score:"+totalScore);
        }catch(Exception v){out.println(v);
        System.out.println("error"+v);} finally {            
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
