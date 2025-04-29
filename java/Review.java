import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@WebServlet(urlPatterns = {"/Review"})
public class Review extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(true);
        try {
            String sentence = request.getParameter("txt5"); // Review text
            String name = request.getParameter("txt6");
            String ss1 = request.getParameter("id");
            String nam = request.getParameter("name");
            String cname = request.getParameter("cname");
            String loc1 = request.getParameter("loc1");
            String lat = request.getParameter("lat");
             String lan = request.getParameter("lan");
             String PTYPE = request.getParameter("PTYPE");
            String ip = request.getRemoteHost();

            String uname = (String) session.getAttribute("name"); // Get the username from the session

            // Establish a database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_commers", "root", "admin");

            // Check if the user has bought the product
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM buy_products WHERE P_Id='" + ss1 + "' AND uname='" + uname + "'");
            if (!rs.next()) {
                response.sendRedirect("Review.jsp?id=" + ss1 + "&name=" + nam + "&msg=You must buy the product before reviewing&cname=" + cname);
                return;
            }

            // Check if a similar review already exists
            ResultSet rs6 = st.executeQuery("SELECT * FROM review WHERE id='" + name + "' AND Product_Name='" + ss1 + "'");
            if (rs6.next()) {
                response.sendRedirect("Review.jsp?id=" + ss1 + "&name=" + nam + "&msg=Duplicate review comment" + "&cname=" + cname);
            } else {
                // Perform sentiment analysis and calculate sentiment score
                String sentiment = analyzeSentiment(sentence);
                double sentimentScore = calculateSentimentScore(sentence);

                // Insert the review with sentiment analysis into the database
                String query = "INSERT INTO review (id, messages, Time, t_score, Opinion, Normal, Cat, Product_Name, Total, negative, IP, loc1, lat, lan, nam,PTYPE) VALUES " +
                        "('" + name + "', '" + sentence + "', NOW(), '" + sentimentScore + "', '" + sentiment + "', NULL, '" + cname + "', '" + ss1 + "', NULL, NULL, '" + ip + "', '" + loc1 + "', '" + lat + "', '" + lan + "', '" + nam + "','" + PTYPE + "')";

                st.executeUpdate(query);

                // Redirect to the review page
                response.sendRedirect("Review.jsp?id=" + ss1 + "&name=" + nam + "&cname=" + cname);
            }
        } catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    // Simple Sentiment Analysis Function
    private String analyzeSentiment(String text) {
        List<String> positiveWords = Arrays.asList("good", "excellent", "amazing", "awesome", "best", "great", "fantastic", "love", "wonderful", "satisfied");
        List<String> negativeWords = Arrays.asList("bad", "terrible", "worst", "awful", "disappointed", "poor", "hate", "horrible", "unsatisfied");

        int positiveScore = 0;
        int negativeScore = 0;

        String[] words = text.toLowerCase().split("\\s+"); // Convert to lowercase and split words
        for (String word : words) {
            if (positiveWords.contains(word)) {
                positiveScore++;
            } else if (negativeWords.contains(word)) {
                negativeScore++;
            }
        }

        if (positiveScore > negativeScore) {
            return "Positive";
        } else if (negativeScore > positiveScore) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    // Sentiment Score Calculation Function (based on basic analysis)
    private double calculateSentimentScore(String text) {
        List<String> positiveWords = Arrays.asList("good", "excellent", "amazing", "awesome", "best", "great", "fantastic", "love", "wonderful", "satisfied");
        List<String> negativeWords = Arrays.asList("bad", "terrible", "worst", "awful", "disappointed", "poor", "hate", "horrible", "unsatisfied");

        int positiveScore = 0;
        int negativeScore = 0;

        String[] words = text.toLowerCase().split("\\s+"); // Convert to lowercase and split words
        for (String word : words) {
            if (positiveWords.contains(word)) {
                positiveScore++;
            } else if (negativeWords.contains(word)) {
                negativeScore++;
            }
        }

        // Normalize sentiment score (positive - negative) / total words
        double totalWords = words.length;
        double sentimentScore = (positiveScore - negativeScore) / totalWords;

        return sentimentScore;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling product reviews with sentiment analysis.";
    }
}
