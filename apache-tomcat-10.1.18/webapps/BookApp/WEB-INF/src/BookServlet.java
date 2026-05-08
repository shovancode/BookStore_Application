import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class BookServlet extends HttpServlet {

    // Handles GET request
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        // Set response type
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get search keyword from HTML form
        String key = req.getParameter("key");

        try {
      Class.forName("org.postgresql.Driver");

Connection con = DriverManager.getConnection(
    "jdbc:postgresql://aws-1-ap-southeast-2.pooler.supabase.com:5432/postgres?sslmode=require&connectTimeout=10",
    "postgres.uvyclnewrsgssbwpmymy",
    "Db@2026Pass#"
);


      String sql = "SELECT * FROM public.books WHERE title ILIKE ? OR author ILIKE ?";

           PreparedStatement ps = con.prepareStatement(sql);
                 ps.setString(1, "%" + key + "%");
          ps.setString(2, "%" + key + "%");

            // Execute query
            ResultSet rs = ps.executeQuery();

            // Display result in HTML table
            out.println("<h2>Book List</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Title</th><th>Author</th><th>Price</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("author") + "</td>");
                out.println("<td>" + rs.getInt("price") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            // Close connection
            con.close();

        } catch (Exception e) {
            out.println("<p>Error: " + e + "</p>");
        }
    }
}
