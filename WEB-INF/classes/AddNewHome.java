import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddNewHome extends HttpServlet {

    private PreparedStatement pstmt;

    public void init() throws ServletException {
        initializeJdbc();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String floor_space = request.getParameter("floor_space");
        String floors = request.getParameter("floors");
        String bedrooms = request.getParameter("bedrooms");
        String bathrooms = request.getParameter("bathrooms");
        String landsize = request.getParameter("landsize");
        String year_constructed = request.getParameter("year_constructed");
        String category = request.getParameter("category");
        String homePrice = request.getParameter("homePrice");
        String selltime = request.getParameter("selltime");
        String agentId = request.getParameter("agentId");
        String ssn = request.getParameter("ssn");

        try {
            if (floor_space.length() == 0 || floors.length() == 0 || bedrooms.length() == 0 ||
                    bathrooms.length() == 0 || landsize.length() == 0 || year_constructed.length() == 0 ||
                    category.length() == 0 || homePrice.length() == 0 || selltime.length() == 0 ||
                    agentId.length() == 0 || ssn.length() == 0) {
                out.println("Please fill all the fields");
                return;
            }

            storeHome(floor_space, floors, bedrooms, bathrooms, landsize, year_constructed, category, homePrice, selltime, agentId, ssn);

            out.println("<html><head><title>Homes Registration Report</title>");
            out.print("<br /><b><center><font color=\"RED\"><H2>Homes Registration Report</H2></font>");
            out.println("</center><br />");

            out.println("</table></center>");

            out.println("Home details are added successfully");
            out.println("</body></html>");
        }
        catch(Exception ex) {
            out.println("\n Error: " + ex.getMessage());
        }
        finally {
            out.close();
        }
    }

    private void initializeJdbc() {
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            String user = "CSIPROJECT";
            String password = "mohammed";
            Connection conn = DriverManager.getConnection(url,user, password);
            pstmt = conn.prepareStatement("INSERT INTO HOMES " +
                    "(FLOOR_SPACE, FLOORS, BEDROOMS, BATHROOMS, LANDSIZE, YEAR_CONSTRUCTED, CATEGORY, HOME_PRICE, " +
                    "SELL_TIME, AGENT_ID, SSN) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void storeHome(String floor_space, String floors, String bedrooms,
                           String bathrooms, String landsize, String year_constructed,
                           String category, String homePrice, String selltime, String agentId, String ssn) throws SQLException {
        pstmt.setString(1, floor_space);
        pstmt.setString(2, floors);
        pstmt.setString(3, bedrooms);
        pstmt.setString(4, bathrooms);
        pstmt.setString(5, landsize);
        pstmt.setString(6, year_constructed);
        pstmt.setString(7, category);
        pstmt.setString(8, homePrice);
        pstmt.setString(9, selltime);
        pstmt.setString(10, agentId);
        pstmt.setString(11, ssn);
        }
}
