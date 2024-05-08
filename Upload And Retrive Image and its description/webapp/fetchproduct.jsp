<%@ page import="java.sql.*, java.io.*, java.util.Base64" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Gallery</title>
    <style>
        .product-container {
            display: flex;
            flex-wrap: wrap;
            justify-content: flex-start;
        }
        .product-card {
            margin: 10px;
            border: 1px solid #dddddd;
            padding: 10px;
            width: 300px;
        }
        .product-image {
            max-width: 100%;
            height: auto;
        }
        .image-container {
            width: 300px;
            height: 300px;
            overflow: hidden;
            border: 1px solid #dddddd;
            margin-bottom: 10px;
        }
        .image-container img {
            max-width: 100%;
            height: auto;
        }
    </style>
</head>
<body>
    <h2>Product Gallery</h2>

    <div class="product-container">
        <% 
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/customers", "root", "admin");
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM product";
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    int productId = rs.getInt("productid");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    int price = rs.getInt("price");
                    int stock = rs.getInt("stock");
                    String active = rs.getString("active");
                    String fileName = rs.getString("filename");
                    
                    // Retrieve image data
                    byte[] imageData = null;
                    PreparedStatement imageStatement = conn.prepareStatement("SELECT data FROM product WHERE filename = ?");
                    imageStatement.setString(1, fileName);
                    ResultSet imageResult = imageStatement.executeQuery();
                    if (imageResult.next()) {
                        String base64Image = imageResult.getString("data");
                        imageData = Base64.getDecoder().decode(base64Image);
                    }
        %>
                    <div class="product-card">
                        <h3><%= name %></h3>
                        <p>Description: <%= description %></p>
                        <p>Category: <%= category %></p>
                        <p>Price: <%= price %></p>
                        <p>Stock: <%= stock %></p>
                        <p>Active: <%= active %></p>
                        <div class="image-container">
                            <img src="data:image/jpeg;base64,<%= Base64.getEncoder().encodeToString(imageData) %>" class="product-image" alt="<%= name %>">
                        </div>
                    </div>
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
    </div>
</body>
</html>

