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

public class AdminControlPanel implements Initializable {

        @FXML
        private Button addUsers;
        @FXML
        private Button editProductsDetails;
        @FXML
        private Button editCompanyDetails;
        @FXML
        private Button DeleteProductDetails;
        @FXML
        private Button backToLogin;
        @FXML
        private ImageView UserImage;


        public void UsersAdd (ActionEvent event) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/RegisterationUI.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void EditProductsInfo(ActionEvent event) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/AddEditProducts.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void EditCompanyInfo(ActionEvent event) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/CompanyDetails.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
        }

        public void DeleteProduct ()throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/DeleteProducts.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
        }
        public void backToLoginPage (ActionEvent event) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/SignINSignOut.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
            //----------------------------------------------------------------------------------------------------------
            Stage stagee = (Stage) backToLogin.getScene().getWindow();
            stagee.close();
        }


    public void initialize(URL location, ResourceBundle resources) {
        File LogoImageView = new File("src/resources/images/Admin.jpg");
        Image LogoImage = new Image(LogoImageView.toURI().toString());
        UserImage.setImage(LogoImage);
    }
}
