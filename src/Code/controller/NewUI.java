package Code.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.File;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;

import java.io.FileOutputStream;

import java.io.OutputStream;

import java.util.Date;

import static Code.controller.Invoice.newLine;


//import com.itextpdf.*;



public class NewUI implements Initializable {

    public static String userName;
    @FXML
    private Label HeaderLabel;

    @FXML
    private Label Username ;

    @FXML
    private Label taxID;

    @FXML
    private Label TotalBeforeVAT;

    @FXML
    private Label TotalAfterVAT;

    @FXML
    private Label AmountBeforeVAT;

    @FXML
    private Label AmountAfterVAT;

    @FXML
    private TextField CompanyNameField;

    @FXML
    private TextArea AddressTextArea;

    @FXML
    private TextField PhoneNumberField;

    @FXML
    private TextField EmailTextField;

    @FXML
    private ImageView UserImage;

    @FXML
    private Button SaveButton;

    @FXML
    private Button savePDFbutton;

    public static String CompanyName;
    public static String Address;
    public static String PhoneNumber;
    public static String Email;

    int index;
    public static String id;

    String CustomerCompanyName;
    String CustomerPhoneNum;
    String CustomerAddress;
    String CustomerEmail;
    String ID;
    String ItemDescription;
    String Quantity;
    String Rate;
    String PPU;
    String UserCompanyName;
    String UserCompanyAddress;
    String UserCompanyContact;
    int c = 0;

    Connection connectionn = null;
    public NewUI() {
        connectionn = databaseConnector.myConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        if(!LoginController.Username.isEmpty()){
            Username.setText(LoginController.Username);
        }else if (!Register.usernameNew.isEmpty()){
            Username.setText(Register.usernameNew);
        }
        userName = Username.getText();
        File LogoImageView = new File("src/resources/images/Admin.jpg");
        Image LogoImage = new Image(LogoImageView.toURI().toString());
        UserImage.setImage(LogoImage);
        LastSessionSaver(taxID);
    }

    public void saveInvoice() throws Exception {
        CompanyName = CompanyNameField.getText();
        Address = AddressTextArea.getText();
        Email = EmailTextField.getText();
        PhoneNumber = PhoneNumberField.getText();
            try{
                if ( CompanyName.isEmpty() || Address.isEmpty() || PhoneNumber.isEmpty() || Email.isEmpty() ){
                    LoginController.ErrorBox("Please fill-in all Information","All Information needed!","Error!");
                }else if(!CompanyName.isEmpty() || !Address.isEmpty() || !PhoneNumberField.getText().isEmpty() || !Email.isEmpty()){
                    SetFilename();
                    if (ProductTableController.ProductsAdded = true){
                        ClearForm();
                    }
//                SaveButton.setOnMouseClicked((event)->{
//                    FinalIndex++;
//                    int i = FinalIndex + records;
//
//                });
                    //System.out.println(FinalIndex);
//                fetRowList();
                }
            }catch(Exception e){
//            e.printStackTrace();
//            e.getCause();
                System.out.println("Error occured while saving data in the database!");

            }

    }

    public void  Products(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Code/view/ProductTableUI.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Tax Invoice Application");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void SaveInvoiceAsPDF(){
//        LoginController.infoBox("Sorry this function is N/A as of now!",null," N/A!");
        if(SaveFileName.Exists == true&&ProductTableController.ProductsAdded==true){
            try {
                String sql2 = "SELECT Company_name, Address, Contact FROM company_details ";
                PreparedStatement pst2 = connectionn.prepareStatement(sql2);
                ResultSet rs2 = pst2.executeQuery();

                while(rs2.next()) {
                    UserCompanyName = rs2.getString("Company_name");
                    UserCompanyAddress = rs2.getString("Address");
                    UserCompanyContact = rs2.getString("Contact");
                }

                String sql = "SELECT Item_Description, Quantity, Rate, PricePerUnit FROM invoice_data WHERE ID ='"+ NewUI.id +"'";
                PreparedStatement pst = connectionn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();

                OutputStream file = new FileOutputStream(new File("C:\\Users\\Legendary_VAIBHAV\\IdeaProjects\\taxInvoiceApplication_V1.4\\src\\resources\\reports\\"+SaveFileName.Filename+".pdf"));
                Document document = new Document();
                PdfWriter.getInstance(document, file);
                document.open();

                document.add(new Paragraph(
                        NewUI.userName + "                                                              " + new Date().toString()+ newLine +
                                UserCompanyName + newLine +
                                UserCompanyAddress + newLine +
                                UserCompanyContact + newLine +
                                "Tax ID: " + NewUI.id
                                + newLine+ newLine
                ));
                document.add(new Paragraph(
                        "Customer's Details: "+ newLine +
                                NewUI.CompanyName + newLine +
                                NewUI.PhoneNumber + newLine +
                                NewUI.Address + newLine +
                                NewUI.Email + newLine
                                + newLine+ newLine

                ));

                PdfPTable table = new PdfPTable(4);

                PdfPCell header = new PdfPCell(new Phrase("Item Description"));
                table.addCell(header);

                header = new PdfPCell(new Phrase("Quantity"));
                table.addCell(header);

                header = new PdfPCell(new Phrase("Rate"));
                table.addCell(header);

                header = new PdfPCell(new Phrase("Price per Unit"));
                table.addCell(header);
                table.setHeaderRows(1);
                document.add(new Paragraph(
                            "Products table: "+ newLine
                        + newLine+ newLine
                ));

                PdfPCell table_cell;

                while (rs.next()) {
                    ItemDescription = rs.getString("Item_Description");
                    table_cell=new PdfPCell(new Phrase(ItemDescription));
                    table.addCell(table_cell);

                    Quantity =rs.getString("Quantity");
                    table_cell=new PdfPCell(new Phrase(Quantity));
                    table.addCell(table_cell);

                    Rate =rs.getString("Rate");
                    table_cell=new PdfPCell(new Phrase(Rate));
                    table.addCell(table_cell);

                    PPU =rs.getString("PricePerUnit");
                    table_cell=new PdfPCell(new Phrase(PPU));
                    table.addCell(table_cell);
                }
                document.add(table);

                document.add(new Paragraph(
                        "                                                          Total: " + ProductTableController.output1
                ));

                document.close();
                file.close();
                LoginController.infoBox("PDF created!",null," Created pdf!");

            } catch (Exception e) {
                LoginController.ErrorBox(e.getMessage(),null," Error!");
                e.printStackTrace();
            }
        }else{
            LoginController.ErrorBox("Sorry!, PDF file of your Invoice cannot be created as your invoice is not saved!","Please save Invoice file first!"," Cannot create pdf!");
        }

    }

    private int LastSessionSaver(Label a) {
        try {
            Statement statement = connectionn.createStatement();
            ResultSet ResultSet = statement.executeQuery("SELECT count(*) FROM invoice_header");
            ResultSet.next();
            index = ResultSet.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        a.setText(String.valueOf(index)+"/2021");
        id = a.getText();
        return 0;
    }

      public void SetFilename() throws Exception{
          Parent root = FXMLLoader.load(getClass().getResource("/Code/view/SaveFileNameUI.fxml"));
          Stage stage = new Stage();
          stage.initStyle(StageStyle.DECORATED);
          stage.setTitle("Tax Invoice Application");
          stage.setScene(new Scene(root));
          stage.show();
      }


    private void ClearForm() {
        CompanyNameField.clear();
        AddressTextArea.clear();
        PhoneNumberField.clear();
        EmailTextField.clear();

    }

//    private void setRowList() {
//        dataList = FXCollections.observableArrayList();
//        ResultSet rs;
//        try {
//            String SQL= "SELECT * FROM invoice_data";
//            rs = connectionn.createStatement().executeQuery(SQL);
//
//            while (rs.next()) {
//                //Iterate Row
//                ObservableList row = FXCollections.observableArrayList();
//                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added " + row);
//                data.add((Product) row);
//
//            }
//            ProductTable.setItems(data);
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage());
//        }
//    }
}
