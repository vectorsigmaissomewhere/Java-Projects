<%@ page import="java.sql.*, java.io.*, java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <title>Image Gallery</title>
</head>
<body>
    <h2>Image Gallery</h2>
    
    <h3>Upload Image</h3>
    <form action="imageServlet" method="post" enctype="multipart/form-data">
        Select image to upload:
        <input type="file" name="image" id="image">
        <input type="submit" value="Upload Image" name="submit">
    </form>
    
    <hr>
    
    <h3>Images</h3>
    <%
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/imagecheck", "root", "admin");
            Statement stmt = conn.createStatement();
            String sql = "SELECT id, filename FROM images";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int imageId = rs.getInt("id");
                String fileName = rs.getString("filename");
                
                // Fetch image data from the database
                byte[] imageData = null;
                PreparedStatement imageStatement = conn.prepareStatement("SELECT data FROM images WHERE id = ?");
                imageStatement.setInt(1, imageId);
                ResultSet imageResult = imageStatement.executeQuery();
                if (imageResult.next()) {
                    String base64Image = imageResult.getString("data");
                    imageData = Base64.getDecoder().decode(base64Image);
                }
    %>
                <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(imageData) %>" alt="<%= fileName %>" style="max-width: 300px; margin: 10px;">
    <%
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    %>
</body>
</html>
