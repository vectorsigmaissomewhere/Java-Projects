// Fetch the product images and description with only using the servlet class

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class FetchProduct extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection settings
    private static final String jdbcURL = "jdbc:mysql://localhost:3306/customers";
    private static final String jdbcUsername = "root";
    private static final String jdbcPassword = "admin";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Initialize PrintWriter
        PrintWriter out = response.getWriter();

        // HTML for displaying product information
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>All Products</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>All Products</h2>");

        // Establish database connection
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            // Prepare SQL statement
            String sql = "SELECT * FROM product";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Execute query
            ResultSet rs = statement.executeQuery();

            // Display product information
            out.println("<table border='1'>");
            out.println("<tr><th>Product ID</th><th>Name</th><th>Description</th><th>Category</th><th>Price</th><th>Stock</th><th>Active</th><th>Filename</th></tr>");
            while (rs.next()) {
                int productId = rs.getInt("productid");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String category = rs.getString("category");
                int price = rs.getInt("price");
                int stock = rs.getInt("stock");
                String active = rs.getString("active");
                String filename = rs.getString("filename");

                out.println("<tr>");
                out.println("<td>" + productId + "</td>");
                out.println("<td>" + name + "</td>");
                out.println("<td>" + description + "</td>");
                out.println("<td>" + category + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + stock + "</td>");
                out.println("<td>" + active + "</td>");
                out.println("<td>" + filename + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

        } catch (SQLException ex) {
            // Handle any SQL errors
            ex.printStackTrace();
            out.println("Error: " + ex.getMessage());
        }

        out.println("</body>");
        out.println("</html>");
    }
}
