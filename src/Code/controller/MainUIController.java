package Code.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;

public class MainUIController  implements Initializable{

    @FXML
    private  Label username;

    @FXML
    private Button NewButton;

    @FXML
    private Button ViewButton;

    @FXML
    private Button ExitButton;

    @FXML
    private ImageView UserImage;

    public void initialize(URL location, ResourceBundle resources) {
        File LogoImageView = new File("src/resources/images/Admin.jpg");
        Image LogoImage = new Image(LogoImageView.toURI().toString());
        UserImage.setImage(LogoImage);
        if(!LoginController.Username.isEmpty()){
            username.setText(LoginController.Username);
        }else if (!Register.usernameNew.isEmpty()){
            username.setText(Register.usernameNew);
        }
    }

    public void  New(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Code/view/NewInvoiceUI.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Tax Invoice Application");
        stage.setScene(new Scene(root));
        stage.show();
        //--------------------------------------------------------------------------------------------------------------
        Stage stage1 = (Stage) NewButton.getScene().getWindow();
        stage1.close();
    }

    public void  ViewAll(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Code/view/ViewOrderHistoryUI.fxml"));
        Stage stage = new Stage();
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("Tax Invoice Application");
        stage.setScene(new Scene(root));
        stage.show();
        //-------------------------------------------------
        Stage stagee = (Stage) ViewButton.getScene().getWindow();
        stagee.close();
    }

    public void  ExitApp(ActionEvent event) {
        Stage stage = (Stage) ExitButton.getScene().getWindow();
        stage.close();
    }

}




