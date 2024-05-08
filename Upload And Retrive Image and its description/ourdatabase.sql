mysql> desc product;
+-------------+--------------+------+-----+---------+----------------+
| Field       | Type         | Null | Key | Default | Extra          |
+-------------+--------------+------+-----+---------+----------------+
| productid   | int          | NO   | PRI | NULL    | auto_increment |
| name        | varchar(500) | YES  |     | NULL    |                |
| description | varchar(500) | YES  |     | NULL    |                |
| category    | varchar(200) | YES  |     | NULL    |                |
| price       | int          | YES  |     | NULL    |                |
| stock       | int          | YES  |     | NULL    |                |
| active      | varchar(10)  | YES  |     | NULL    |                |
| filename    | varchar(100) | YES  |     | NULL    |                |
| data        | longblob     | YES  |     | NULL    |                |
