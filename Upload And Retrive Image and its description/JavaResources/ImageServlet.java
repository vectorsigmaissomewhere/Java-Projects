import java.io.*;
import java.sql.*;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/imageServlet")
@MultipartConfig(maxFileSize = 16177215) // upload file's size up to 16MB
public class ImageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection settings
    private String jdbcURL = "jdbc:mysql://localhost:3306/customers";
    private String jdbcUsername = "root";
    private String jdbcPassword = "admin";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        InputStream inputStream = null; // input stream of the upload file
        Part filePart = request.getPart("image");
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        String fileName = filePart.getSubmittedFileName(); // get submitted file name
        String productName = request.getParameter("productname");
        String productDescription = request.getParameter("productdescription");
        String productCategory = request.getParameter("productcategory");
        int productPrice = Integer.parseInt(request.getParameter("productprice"));
        int productStock = Integer.parseInt(request.getParameter("productstock"));
        String productActive = request.getParameter("productactive");

        Connection conn = null; // connection to the database
        String message = null; // message will be sent back to client

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Encode image data to Base64
            byte[] imageBytes = inputStream.readAllBytes();
            String imageData = Base64.getEncoder().encodeToString(imageBytes);

            // constructs SQL statement
            String sql = "INSERT INTO product (name, description, category, price, stock, active, filename, data) values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, productName); // set product name
            statement.setString(2, productDescription); // set product description
            statement.setString(3, productCategory); // set product category
            statement.setInt(4, productPrice); // set product price
            statement.setInt(5, productStock); // set product stock
            statement.setString(6, productActive); // set product active
            statement.setString(7, fileName); // set file name
            statement.setString(8, imageData); // set Base64 encoded image data

            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException | ClassNotFoundException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            request.setAttribute("Message", message);

            // forwards to the message page
            getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int imageId = Integer.parseInt(request.getParameter("id"));

        Connection conn = null; // connection to the database

        try {
            // Connect to the database
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);

            // Retrieve image data from the database
            String sql = "SELECT filename, data FROM images WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, imageId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String fileName = result.getString("filename");
                String imageData = result.getString("data");

                // Decode Base64 image data
                byte[] imageBytes = Base64.getDecoder().decode(imageData);

                // Set content type and attachment disposition
                response.setContentType("image/jpeg");
                response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

                // Write image data to the response output stream
                OutputStream outputStream = response.getOutputStream();
                outputStream.write(imageBytes);
                outputStream.close();
            } else {
                response.getWriter().write("Image not found");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            response.getWriter().write("ERROR: " + ex.getMessage());
        } finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}

