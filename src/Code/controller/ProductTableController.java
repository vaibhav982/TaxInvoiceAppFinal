package Code.controller;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Code.model.Data;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.stream.Collectors;
import java.sql.Connection;

public class ProductTableController implements Initializable {

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

    @FXML
    TextField name;

    @FXML
    TextField quantity;

    @FXML
    TextField price;

    @FXML
    Button add;

    @FXML
    Button delete;

    @FXML
    private Label Sample;

    @FXML
    Text txt;

    Double FinalCost = 0.0;
    public static String output1;
    public static boolean ProductsAdded = false;
    Connection connectionn = null;

    public ProductTableController() {
        connectionn = databaseConnector.myConnection();
    }

    final ObservableList<Data> data= FXCollections.observableArrayList();

    public String getValueAt(TableView<Data> table, int column, int row){
        return table.getColumns().get(column).getCellObservableValue(row).getValue().toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        itemDes.setCellValueFactory(new PropertyValueFactory<>("rName"));
        iQty.setCellValueFactory(new PropertyValueFactory<>("rQty"));
        rate.setCellValueFactory(new PropertyValueFactory<>("rPrice"));
        ppu.setCellValueFactory(new PropertyValueFactory<>("rTotal"));
        tableID.setItems(data);
    }


    @FXML
    public void onAddItem(ActionEvent event){
        if(name.getText().isEmpty()||quantity.getText().isEmpty()||price.getText().isEmpty()){
            LoginController.ErrorBox("Please enter all information!",null,"Error!");
        }else{
            try{
                float qty = Float.parseFloat(quantity.getText());
                float unitprice =  Float.parseFloat(price.getText());
                float Total;
                //ProductsAdded = true;
                Total = unitprice / qty;

                FinalCost = FinalCost + unitprice;

                output1 = " " + FinalCost;

                Data entry  = new Data(name.getText(), qty, unitprice ,Total);
                data.add(entry);

                Sample.setText(output1);

                ClearForm();
            }catch(NumberFormatException e){
                LoginController.ErrorBox("Please numerical value as quantity and rate!",null,"Error!");
            }
        }


    }


    public void onDeleteItem(ActionEvent event1){
       // ProductsAdded = false;
        ObservableList<Data> productSelected, allProducts;
        allProducts = tableID.getItems();
        productSelected = tableID.getSelectionModel().getSelectedItems();
        double totalCostOfSelectedItems = 0 ;

        for (Data product : productSelected) {
            totalCostOfSelectedItems = totalCostOfSelectedItems + product.getRPrice();
        }

        FinalCost = FinalCost - totalCostOfSelectedItems ;

        allProducts.removeAll(productSelected);
        FinalCost = allProducts.stream().collect(Collectors.summingDouble(Data::getRPrice));
        output1 = " " + FinalCost;
        Sample.setText(output1);
    }

    public void Save(){
        ProductsAdded = true;
        LoginController.infoBox("Saved!",null,"Saved!");
        Stage stagee = (Stage) add.getScene().getWindow();
        stagee.close();
        String sql =  "INSERT INTO invoice_data (Item_Description, Quantity, Rate, PricePerUnit, ID, total) VALUES (?,?,?,?,?,?) ";
        try {
            for (int i = 0; i <= tableID.getItems().size(); i++) {
                PreparedStatement statement = connectionn.prepareStatement(sql);
                statement.setString(1, getValueAt(tableID, 0, i));
                statement.setString(2, getValueAt(tableID, 1, i));
                statement.setString(3, getValueAt(tableID, 2, i));
                statement.setString(4, getValueAt(tableID, 3, i));
                statement.setString(5, NewUI.id);
                statement.setString(6, output1);
                statement.executeUpdate();
            }
            LoginController.infoBox("Saved!",null,"Saved!");
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void ClearForm() {
        name.clear();
        quantity.clear();
        price.clear();
    }

}



//package Code.controller;
//
//
//import Code.model.InvoiceItems;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.text.Text;
//import javafx.stage.Stage;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//public class ProductTableController implements Initializable {
//
//    @FXML
//    TableView<InvoiceItems> tableID;
//
//    @FXML
//    TableColumn<InvoiceItems, String> itemDes;
//
//    @FXML
//    TableColumn<InvoiceItems, Float> iQty;
//
//    @FXML
//    TableColumn<InvoiceItems, Float> rate;
//
//    @FXML
//    TableColumn<InvoiceItems, Float> ppu;
//
//    @FXML
//    TextField name;
//
//    @FXML
//    TextField quantity;
//
//    @FXML
//    TextField price;
//
//    @FXML
//    Button add;
//
//    @FXML
//    Button delete;
//
//    @FXML
//    private Label Sample;
//
//    @FXML
//    Text txt;
//
//    Double FinalCost = 0.0;
//    String output1;
//
//    Connection connectionn = null;
//
//    public ProductTableController() {
//        connectionn = databaseConnector.myConnection();
//    }
//
//    final ObservableList<InvoiceItems> data = FXCollections.observableArrayList();
//
//    public String getValueAt(TableView<InvoiceItems> table, int column, int row){
//        return table.getColumns().get(column).getCellObservableValue(row).getValue().toString();
//    }
//
//
//    @Override
//    public void initialize(URL url, ResourceBundle rb) {
//        itemDes.setCellValueFactory(new PropertyValueFactory<>("ItemDescription"));
//        iQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
//        ppu.setCellValueFactory(new PropertyValueFactory<>("PPU"));
//        rate.setCellValueFactory(new PropertyValueFactory<>("Rate"));
//        tableID.setItems(data);
//    }
//
//
//    @FXML
//    public void onAddItem(ActionEvent event){
//
//        if(name.getText().isEmpty()||quantity.getText().isEmpty()||price.getText().isEmpty()){
//            LoginController.ErrorBox("Please enter all information!",null,"Error!");
//        }else{
//            try{
//                float qty = Float.parseFloat(quantity.getText());
//                float unitprice =  Float.parseFloat(price.getText());
//                float Total;
//
//                Total = unitprice * qty;
//
//                FinalCost = FinalCost + Total;
//
//                output1 = " " + FinalCost;
//
//                InvoiceItems entry  = new InvoiceItems(name.getText(), qty, unitprice ,Total);
//                System.out.println(name.getText().toString());
//                data.add(entry);
//
//                Sample.setText(output1);
//
//                ClearForm();
//            }catch(NumberFormatException e){
//                LoginController.ErrorBox("Please numerical value as quantity and rate!",null,"Error!");
//            }
//        }
//
//
//    }
//
//
//    public void onDeleteItem(ActionEvent event1){
//        ObservableList<InvoiceItems> productSelected, allProducts;
//        allProducts = tableID.getItems();
//        productSelected = tableID.getSelectionModel().getSelectedItems();
//        float totalCostOfSelectedItems = 0 ;
//
//        for (InvoiceItems product : productSelected) {
//            totalCostOfSelectedItems = totalCostOfSelectedItems + product.getPPU();
//        }
//
//        FinalCost = FinalCost - totalCostOfSelectedItems ;
//
//        allProducts.removeAll(productSelected);
//        FinalCost = allProducts.stream().collect(Collectors.summingDouble(InvoiceItems::getPPU));
//        output1 = " " + FinalCost;
//        Sample.setText(output1);
//    }
//
//    public void Save(){
//        Stage stagee = (Stage) add.getScene().getWindow();
//        stagee.close();
//        String sql =  " INSERT INTO invoice_data (Item_Description, Quantity, Rate, PricePerUnit, ID) VALUES (?,?,?,?,?) ";
//        try {
//            for (int i = 0; i <= tableID.getItems().size(); i++) {
//                PreparedStatement statement = connectionn.prepareStatement(sql);
//                statement.setString(1, getValueAt(tableID, 0, i));
//                statement.setString(2, getValueAt(tableID, 1, i));
//                statement.setString(3, getValueAt(tableID, 2, i));
//                statement.setString(4, getValueAt(tableID, 3, i));
//                statement.setString(5, NewUI.id);
//                statement.executeUpdate();
//            }
//            LoginController.infoBox("Saved!",null,"Saved!");
//        }catch (SQLException throwables) {
//            throwables.printStackTrace();
//            LoginController.infoBox(throwables.getMessage(),null,"SQL Saving Error!");
//        }catch(Exception e){
//            LoginController.infoBox(e.getMessage(),null,"Saving Error!");
//        }
//    }
//
//    private void ClearForm() {
//        name.clear();
//        quantity.clear();
//        price.clear();
//    }
//
//}
//
