package Code.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class SaveFileName {


    @FXML
    public TextField FileName;

    @FXML
    public Button SaveFilename;

    public static String Filename;
    Connection connection = null;
    public static boolean Exists = false;

    public SaveFileName() {
        connection = databaseConnector.myConnection();
    }

    public void setSaveFilename(){
            Exists = true;
            Filename = FileName.getText();
            String sql =  " INSERT INTO invoice_header (User_Name, Company_name, Phone_num, Address, Email, File_name, ID) VALUES (?,?,?,?,?,?,?) ";

            try{

                if (Filename.isEmpty()){
                    LoginController.ErrorBox("Please enter the file name to proceed further","Filename required!","Error!");
                }else{

                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, NewUI.userName);
                    statement.setString(2, NewUI.CompanyName);
                    statement.setString(3, NewUI.PhoneNumber);
                    statement.setString(4, NewUI.Address);
                    statement.setString(5, NewUI.Email);
                    statement.setString(6, Filename);
                    statement.setString(7, NewUI.id);
                    int records = statement.executeUpdate();
                    LoginController.infoBox("Filename registered!",null,"Success!" );
                    //-------------------------------------------------------
                    Stage stagee = (Stage) SaveFilename.getScene().getWindow();
                    stagee.close();

                }

            }catch(Exception e){
                e.printStackTrace();
                e.getCause();

            }
        }

    }

