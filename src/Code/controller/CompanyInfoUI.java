package Code.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;

public class CompanyInfoUI implements Initializable {

    @FXML
    private Button SubmitButton;

    @FXML
    private Label StatusLabel;

    @FXML
    private TextField CompanyTextField;

    @FXML
    private TextField ContactTextField;

    @FXML
    private TextField AddressTextField;
    
    @FXML
    private ImageView LoginImageView;


    public static String usernameNew;

    Connection connection = null;
    Stage dialogStage = new Stage();
    Scene scene;

    public CompanyInfoUI() {
        connection = databaseConnector.myConnection();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File LogoimageView = new File("src/resources/images/Tax_invoice.png");
        Image LogoImage = new Image(LogoimageView.toURI().toString());
        LoginImageView.setImage(LogoImage);
    }

    public void  OnsubmitButton(ActionEvent event) throws Exception {
        String Companyname = CompanyTextField.getText();
        String address = AddressTextField.getText();
        String Contact = ContactTextField.getText();

        String fields = "INSERT INTO company_details (Company_name, Address, Contact) VALUES ('";
        String values = Companyname + "','" + address + "','" + Contact + "')";
        String sql = fields + values;

        try{
            if ( Companyname.isEmpty() || address.isEmpty() || Contact.isEmpty()){
                LoginController.ErrorBox("Please fill-in all Information","All Information needed!","Error!");
            }else{
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                LoginController.infoBox("Company detail's saved","Successfully registered!","Success!" );
                usernameNew = NewUI.userName;
                Node node = (Node)event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
}