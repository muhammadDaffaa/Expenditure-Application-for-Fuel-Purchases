# Expenditure Console Application for Fuel Purchases
Making CONSOLE APPLICATION - CRUD NATIVE DATABASE with JAVA programming language and using MAVEN. 
This project already uses preparedstatement for MySql Query. Over time this project will continue to grow until it becomes an REST API.<br>

# Installation
In the application there are several dependencies that must be added to the pom.xml in the project.<br> Here are the dependencies we need: mysql, simple json
<!-- - `Connector J` :  https://mvnrepository.com/artifact/com.mysql/mysql-connector-j // Connection JAVA to MySql Database <br>  -->
MySql<br>
```
  <!-- https://mvnrepository.com/artifact/com.mysql/mysql-connector-j -->
    <dependency>
      <groupId>com.mysql</groupId>
      <artifactId>mysql-connector-j</artifactId>
      <version>8.0.32</version>
    </dependency>
```
JSON Simple<br>
```
    <!-- https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple -->
    <dependency>
      <groupId>com.googlecode.json-simple</groupId>
      <artifactId>json-simple</artifactId>
      <version>1.1.1</version>
    </dependency>
```





# Database Connection :
Change the HOST, PORT, USERNAME, and PASSWORD. If you want to fork this repository.

```JAVA
    public static String USERNAME = System.getenv("oci_user_sql");
    public static String PASSWORD = System.getenv("oci_password_sql");
    public static int PORT = 3306;
    public static String DATABASE = "operational";
    public static String HOST = System.getenv("oci_host_sql");
```
