import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class AddAgent extends HttpServlet {
    private PreparedStatement pstmt;
    private PreparedStatement pstmt2;
    private PreparedStatement pstmt3;

    public void init() throws ServletException {
        initializeJdbc();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String PersonId = request.getParameter("PersonId");
        String LicenseNum = request.getParameter("LicenseNum");

        try {
            if (PersonId.length() == 0 || LicenseNum.length() == 0) {
                out.println("Please enter both Person ID and License Number.");
                return;
            }

            storeAgent(PersonId, LicenseNum);
            storeCompanyAgent("CName", PersonId, "A001", "C001");

            out.println("<html><head><title>Agent Registration Report</title></head><body>");
            out.print("<br /><b><center><font color=\"RED\"><H2>Agent Registration Report</H2></font></center>");
            out.println("<br />");
            out.println("<center><table border=\"1\">");
            out.println("<tr BGCOLOR=\"#cccccc\">");
            out.println("<td align=\"justify\"><font face=\"times new roman\" size=\"4pt\">Person ID</td>");
            out.println("<td align=\"justify\"><font face=\"times new roman\" size=\"4pt\">License Number</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>" + PersonId + "</td>");
            out.println("<td>" + LicenseNum + "</td>");
            out.println("</tr>");
            out.println("</table></center>");

            out.println(PersonId + " " + LicenseNum + " is now added to the Agent table.");
            out.println("</body></html>");
        } catch (Exception ex) {
            out.println("\n Error: " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    private void initializeJdbc() {
        try {
            String driver = "oracle.jdbc.driver.OracleDriver";
            Class.forName(driver);

            // database URL is the unique identifier of the database on the Internet
            // thin is the oracle server
            String url = "jdbc:oracle:thin:@127.0.0.1:1521:orcl";
            String user = "CSIPROJECT";
            String password = "mohammed";
            Connection conn = DriverManager.getConnection(url, user, password);

            pstmt = conn.prepareStatement("insert into agent " + "(PersonID, LicenseNum) values (?, ?)");
            pstmt2 = conn.prepareStatement("insert into company_agent " + "(CName, AName, AgentID, CompanyID) values (?, ?, ?, ?)");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void storeAgent(String PersonId, String LicenseNum) throws SQLException {
        pstmt.setString(1, PersonId);
        pstmt.setString(2, LicenseNum);
        pstmt.executeUpdate();
    }

    private void storeCompanyAgent(String CName, String AName, String AgentID, String CompanyID) throws SQLException {
        pstmt2.setString(1, CName);
        pstmt2.setString(2, AName);
        pstmt2.setString(3, AgentID);
        pstmt2.setString(4, CompanyID);
        pstmt2.executeUpdate();
    }
}
