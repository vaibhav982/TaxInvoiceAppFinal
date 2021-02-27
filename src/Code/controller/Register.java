package Code.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class Register implements Initializable {

    @FXML
    private Button RegisterButton;

    @FXML
    private Label StatusLabel;

    @FXML
    private TextField UsernameTextField;

    @FXML
    private TextField EmailTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView LoginImageView;


    public static String usernameNew;

    Connection connection = null;
    Stage dialogStage = new Stage();
    Scene scene;

    public Register() {
        connection = databaseConnector.myConnection();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File LogoimageView = new File("src/resources/images/Tax_invoice.png");
        Image LogoImage = new Image(LogoimageView.toURI().toString());
        LoginImageView.setImage(LogoImage);
    }

    public void  setSigninButton(ActionEvent event) throws Exception {
        String username = UsernameTextField.getText();
        String password = passwordField.getText();
        String email = EmailTextField.getText();

        //String UserSelect = "SELECT username FROM users_table";
        String fields = "INSERT INTO users_table (username, email, password) VALUES ('";
        String values = username + "','" + email + "','" + password + "')";
        String sql = fields + values;

        try{
            if ( username.isEmpty() || password.isEmpty() || email.isEmpty()){
                LoginController.ErrorBox("Please fill-in all Information","All Information needed!","Error!");
            }else{
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);

//                ResultSet rs = statement.executeQuery(UserSelect);
//                List<String> list = new ArrayList<String>();;
//                while (rs.next()) {
//                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
//                        list.add(rs.getString(i));
//                    }
//                }
//                if (list.contains(username)) {
//                    LoginController.ErrorBox("Sorry, username already exists,Try again!", null, "Try Again!");
//                } else {
//                    LoginController.infoBox("User added successfully! ", null, "Success!");
//                    usernameNew = username;
//                    UsernameTextField.clear();
//                    passwordField.clear();
//                    EmailTextField.clear();
//                }

                LoginController.infoBox("User added successfully! ",null,"Success!" );
                usernameNew = username;
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();

        }
    }
}