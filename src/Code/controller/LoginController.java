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
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert.AlertType;
import java.sql.Connection;
import javafx.scene.Node;


public class LoginController<UserName> implements Initializable {

    @FXML
    public static Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Label StatusLabel;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView LoginImageView;

    @FXML
    private ImageView ExitButton;

    @FXML
    private Button Exit;

    public static String Username;

    public void ButtonExit() {
        Stage stage = (Stage) Exit.getScene().getWindow();
        stage.close();
    }

    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = null;

    Stage dialogStage = new Stage();
    Scene scene;

    public LoginController() {
        connection = databaseConnector.myConnection();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File LogoimageView = new File("src/resources/images/Tax_invoice.png");
        Image LogoImage = new Image(LogoimageView.toURI().toString());
        LoginImageView.setImage(LogoImage);

        File Exitbutton = new File("src/resources/images/remove.png");
        Image ExitImage = new Image(Exitbutton.toURI().toString());
        ExitButton.setImage(ExitImage);
    }

    public void setSigninButton(ActionEvent event) throws Exception {
        String username = UsernameTextField.getText();
        String password = passwordField.getText();

        String sql = "SELECT * FROM users_table WHERE  username = ? AND  password = ? ";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                if ( username.isEmpty() || password.isEmpty()){
                    LoginController.ErrorBox("Please fill-in all Information","All Information needed!","Error!");
                }else {
                    ErrorBox("Please enter correct Email and Password", null, "Login Failed!");
                }
            }else{
                Username = username;
                SetUserRights(username);
                // For signing in as Admin type "Admin" as username and "admin1289" as password
                if((!Username.equals("Admin"))){
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/Code/view/MainUI.fxml")));
                    dialogStage.setTitle("Tax Invoice Application");
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }
                Node node = (Node)event.getSource();
                dialogStage = (Stage) node.getScene().getWindow();
                dialogStage.close();
            }
        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }


    public static void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    public static void ErrorBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }
    public void SetUserRights(String usernamE) throws Exception{
        String Admin ="Admin";
        if((Username.equals(Admin))){
            infoBox("Welcome Admin !","Administrative tasks can be performed","Administrative tasks are unlocked!" );
            Parent root = FXMLLoader.load(getClass().getResource("/Code/view/AdminPanel.fxml"));
            Stage stage = new Stage();
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Tax Invoice Application");
            stage.setScene(new Scene(root));
            stage.show();
        }else{
            infoBox("Welcome " + usernamE + "!","Logged in as: " + usernamE,"Login Successful!" );
        }

    }

}
