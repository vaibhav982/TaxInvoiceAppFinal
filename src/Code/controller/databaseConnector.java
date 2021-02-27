package Code.controller;

import java.sql.*;

public class databaseConnector {
    Connection databaseLink = null;
    public static Connection myConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection databaseLink = DriverManager.getConnection("jdbc:sqlserver://taxinvoicedb.database.windows.net:1433;database=taxInvoiceDB;user=java@taxinvoicedb;password={Admin@1289};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;");
            return databaseLink;
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
            return null;
        }

    }

}
