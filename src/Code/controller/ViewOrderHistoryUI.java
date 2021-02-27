package Code.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.sql.ResultSet;

public class ViewOrderHistoryUI implements Initializable {

    @FXML
    private ImageView CompanyLogo;

    @FXML
    private Button Open;

    @FXML
    private Button back;

    @FXML
    private TextArea OrderArea;
    @FXML
    private TextArea ProductArea;

    @FXML
    private ComboBox OrderList;

    public static String ViewOrder;

    Connection connection = null;
    String Username;
    String CustomerCompanyName;
    String CustomerPhoneNum;
    String CustomerAddress;
    String CustomerEmail;
    String ID;
    ArrayList<String> products = new ArrayList<String>();
    Iterator<String> it = products.iterator();
    String ItemDescription;
    String Quantity;
    String Rate;
    String PPU;
    String UserCompanyName;
    String UserCompanyAddress;
    String UserCompanyContact;

    public ViewOrderHistoryUI() {
        connection = databaseConnector.myConnection();
    }

    final ObservableList options = FXCollections.observableArrayList();
    String newLine = System.getProperty("line.separator");

    public void initialize(URL location, ResourceBundle resources) {
        File CompanyLogoImageView = new File("src/resources/images/Tax_invoice.png");
        Image CompanyLogoImage = new Image(CompanyLogoImageView.toURI().toString());
        CompanyLogo.setImage(CompanyLogoImage);

        OrderList.getItems().addAll(options);
        OrderList.getSelectionModel().select(0);
        OrdersLists();
    }

    public void getBack() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Code/view/MainUI.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Tax Invoice Application");
        stage.setScene(new Scene(root));
        stage.show();
        //-------------------------------------------------
        Stage stage1 = (Stage) back.getScene().getWindow();
        stage1.close();
    }

    public void OrdersLists() {
        try {
            String sql = "SELECT ID FROM invoice_header WHERE User_Name = '" + LoginController.Username + "'";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                options.add(rs.getString("ID"));
            }
            OrderList.setItems(options);

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void SetOpen() throws Exception {
         ViewOrder = (String) OrderList.getValue();
        Parent root = FXMLLoader.load(getClass().getResource("/Code/view/ViewInvoice.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Tax Invoice Application");
        stage.setScene(new Scene(root));
        stage.show();
        //Invoice.InvoiceGen(ViewOrder,OrderArea);

//        try {
//            String sql1 = "SELECT User_name,Company_name, Phone_num, Address, Email, ID FROM invoice_header WHERE  ID = '" + ViewOrder + "'";
//            PreparedStatement pst1 = connection.prepareStatement(sql1);
//            ResultSet rs1 = pst1.executeQuery();
//
//            while(rs1.next()){
//                Username = rs1.getString("User_name");
//                CustomerCompanyName = rs1.getString("Company_name");
//                CustomerPhoneNum = rs1.getString("Phone_num");
//                CustomerAddress = rs1.getString("Address");
//                CustomerEmail = rs1.getString("Email");
//                ID = rs1.getString("ID");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }
//
//        try {
//            String sql = "SELECT Item_Description, Quantity, Rate, PricePerUnit FROM invoice_data WHERE  ID = '" + ViewOrder + "'";
//            PreparedStatement pst = connection.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//
//            while(rs.next()){
//                ItemDescription = rs.getString("Item_Description");
//                Quantity = rs.getString("Quantity");
//                Rate = rs.getString("Rate");
//                PPU = rs.getString("PricePerUnit");
//                products.add("||"+ItemDescription+"||"+Quantity+"||"+Rate+"||"+PPU+"||");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }
//
//        try {
//            String sql2 = "SELECT Company_name, Address, Contact FROM company_details ";
//            PreparedStatement pst2 = connection.prepareStatement(sql2);
//            ResultSet rs2 = pst2.executeQuery();
//
//            while(rs2.next()){
//                UserCompanyName = rs2.getString("Company_name");
//                UserCompanyAddress = rs2.getString("Address");
//                UserCompanyContact = rs2.getString("Contact");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            e.getCause();
//        }


    }


}
