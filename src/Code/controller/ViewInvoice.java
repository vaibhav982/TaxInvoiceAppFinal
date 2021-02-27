package Code.controller;

import Code.model.Data;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import static Code.controller.ViewOrderHistoryUI.ViewOrder;

public class ViewInvoice implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label userCompanyName;
    @FXML
    private Label userCompanyPhoneNum;
    @FXML
    private Label userCompanyAddress;
    @FXML
    private Label userCompanyEmail;
    @FXML
    private Label taxId;
    @FXML
    private Label CustomerCompanyName;
    @FXML
    private Label CustomerCompanyAddress;
    @FXML
    private Label CustomerCompanyPhoneNum;
    @FXML
    private Label total;

    private ObservableList<Data> data = FXCollections.observableArrayList();
    @FXML
    TableView<Data> tableID;
    @FXML
    TableColumn<Data, String> itemDes;

    @FXML
    TableColumn<Data, Double> iQty;

    @FXML
    TableColumn<Data, Double> rate;

    @FXML
    TableColumn<Data, Double> ppu;

    Connection connection = null;

    public ViewInvoice(){
        connection = databaseConnector.myConnection();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemDes.setCellValueFactory(new PropertyValueFactory<>("rName"));
        iQty.setCellValueFactory(new PropertyValueFactory<>("rQty"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rPrice"));
        ppu.setCellValueFactory(new PropertyValueFactory<>("rTotal"));

        try{
            String SQL = "SELECT Item_Description, Quantity, Rate, PricePerUnit,total from invoice_data WHERE ID ='"+ ViewOrder +"'";
            ResultSet rs = connection.createStatement().executeQuery(SQL);
            float t = 0;
            while(rs.next()){
                data.add(new Data(rs.getString("Item_Description"),rs.getFloat("Quantity"),rs.getFloat("Rate"),rs.getFloat("PricePerUnit")));
               t = rs.getFloat("total");
            }
            total.setText(String.valueOf(t));
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        tableID.setItems(data);
        buildData();
    }

    public void buildData(){
        data = FXCollections.observableArrayList();
        try {
            String sql1 = "SELECT User_name,Company_name, Phone_num, Address, Email, ID FROM invoice_header WHERE  ID = '" + ViewOrder + "'";
            PreparedStatement pst1 = connection.prepareStatement(sql1);
            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next()){
                username.setText(rs1.getString("User_name"));
                userCompanyName.setText(rs1.getString("Company_name"));
                userCompanyPhoneNum.setText(rs1.getString("Phone_num"));
                userCompanyAddress.setText(rs1.getString("Address"));
                userCompanyEmail.setText(rs1.getString("Email"));
                taxId.setText(rs1.getString("ID"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }


        try {
            String sql2 = "SELECT Company_name, Address, Contact FROM company_details ";
            PreparedStatement pst2 = connection.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                CustomerCompanyName.setText(rs2.getString("Company_name"));
                CustomerCompanyAddress.setText(rs2.getString("Address"));
                CustomerCompanyPhoneNum.setText(rs2.getString("Contact"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }



}
