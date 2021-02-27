package Code.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import jdk.nashorn.internal.objects.Global;
import net.sf.jasperreports.engine.*;

import Code.utility.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import Code.model.Data;

public class Invoice {

    public static Connection connectionnn = null;
    public static String Username;
    public static String CustomerCompanyName;
    public static String CustomerPhoneNum;
    public static String CustomerAddress;
    public static String CustomerEmail;
    public static String ID;
    public static List<Data> invoiceItems  =  new ArrayList<>();
//    public static String ItemDescription;
//    public static float Quantity;
//    public static float Rate;
//    public static float PPU;
    public static String UserCompanyName;
    public static String UserCompanyAddress;
    public static String UserCompanyContact;

    public Invoice(){connectionnn = databaseConnector.myConnection();}
    public static String newLine = System.getProperty("line.separator");

    public static void InvoiceGen(String ViewOrder, TextArea OrderArea){

        try {
            String sql1 = "SELECT User_name,Company_name, Phone_num, Address, Email, ID FROM invoice_header WHERE  ID = '" + ViewOrder + "'";
            PreparedStatement pst1 = connectionnn.prepareStatement(sql1);
            ResultSet rs1 = pst1.executeQuery();

            while(rs1.next()){
                Username = rs1.getString("User_name");
                CustomerCompanyName = rs1.getString("Company_name");
                CustomerPhoneNum = rs1.getString("Phone_num");
                CustomerAddress = rs1.getString("Address");
                CustomerEmail = rs1.getString("Email");
                ID = rs1.getString("ID");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        try {
            String sql = "SELECT Item_Description, Quantity, Rate, PricePerUnit FROM invoice_data WHERE  ID = '" + ViewOrder + "'";
            PreparedStatement pst = connectionnn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String ItemDescription = rs.getString("Item_Description");
                float Quantity = rs.getFloat ("Quantity");
                float   Rate = rs.getFloat("Rate");
                float PPU = rs.getFloat("PricePerUnit");
                invoiceItems.add(new Data(ItemDescription,Quantity,Rate,PPU));
//                InvoiceItems invoiceItems = new InvoiceItems(ItemDescription,Quantity,Rate,PPU);
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        try {
            String sql2 = "SELECT Company_name, Address, Contact FROM company_details ";
            PreparedStatement pst2 = connectionnn.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();

            while(rs2.next()){
                UserCompanyName = rs2.getString("Company_name");
                UserCompanyAddress = rs2.getString("Address");
                UserCompanyContact = rs2.getString("Contact");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
        
         String invoiceItemsText = "";
        for(int i=0;i < invoiceItems.size();i++){
            invoiceItemsText = invoiceItemsText + " Item: " + invoiceItems.get(i).getRName() + " Quantity: " + invoiceItems.get(i).getRQty() + " PPU: " + invoiceItems.get(i).getRPrice() + " Amount: " + invoiceItems.get(i).getRTotal()+ newLine;
        }
        OrderArea.setText(
                Username + newLine +
                        UserCompanyName + newLine +
                        UserCompanyAddress + newLine +
                        UserCompanyContact + newLine + newLine + newLine +
                        CustomerCompanyName + newLine +
                        CustomerAddress + newLine +
                        CustomerEmail + newLine +
                        CustomerPhoneNum + newLine + newLine + newLine +
                        ID + newLine + invoiceItemsText

        );
    }


//    private void printJasperInvoice(Invoice invoice) {
//
//        Map map = null;
//
//        try {
//            map = getReportParameters(invoice);
//        } catch (Exception e) {
//            Utility.beep();
//            String message = "An error occurred whilst collecting data to print the invoice!";
//            LoginController.ErrorBox(message,"","Invoice Value Map Error!");
//            return;
//        }
//
//        final String invoice_jrxml_file = "src/resources/reports/taxInvoice_report.jrxml";
//        final String invoice_jasper_file = "src/resources/reports/taxInvoice_report.jasper";
//        try {
//            JasperCompileManager.compileReportToFile(invoice_jrxml_file,invoice_jasper_file);
//        } catch (JRException e) {
//            e.printStackTrace();
//            LoginController.ErrorBox(e.getMessage(),"","JRXML to Jasper Compilation Error!");
//        }
//        JasperPrint jasperPrint = null;
//
//        try (InputStream reportStream = this.getClass().getResourceAsStream(invoice_jasper_file) ){
//
//            jasperPrint = JasperFillManager.fillReport(reportStream, map,
//                    new JRBeanCollectionDataSource(invoice.getInvoiceItems(), true));
//        }
//        catch (Exception e)
//        {
//            logger.logp(Level.SEVERE, InvoiceController.class.getName(),
//                    "printInvoice", "Error in fillReport function call", e);
//            Utility.beep();
//            String message = "An error occurred whilst preparing to print the invoice!";
//            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
//            alert.setTitle("Error Occurred");
//            alert.setHeaderText("Error in printing the invoice");
//            alert.initOwner(mainWindow);
//            Global.styleAlertDialog(alert);
//            alert.showAndWait();
//            return;
//        }
//
//        if (userPreferences.getShowPrintPreview()) {
//            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//            jasperViewer.setTitle("Invoice");
//            jasperViewer.setVisible(true);
//        } else {
//            //command for printing the report
//            try {
//                //false means don't show the print dialog. Send output instead directly to the default printer
//                boolean showPrintDialog = userPreferences.getShowPrintDialog();
//                JasperPrintManager.printReport(jasperPrint, showPrintDialog);
//            } catch (Exception ex) {
//                logger.logp(Level.SEVERE, InvoiceController.class.getName(),
//                        "printInvoice", "Error in printReport function call", ex);
//                Utility.beep();
//                String message = "An error occurred whilst printing the invoice!";
//                Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
//                alert.setTitle("Invoice Printing Error");
//                alert.setHeaderText("Error in printing the invoice");
//                alert.initOwner(mainWindow);
//                Global.styleAlertDialog(alert);
//                alert.showAndWait();
//            }
//        }
//
//    }
//
//    private Map getReportParameters(Invoice invoice)  throws Exception{
//        HashMap map = new HashMap(12); //represents report parameters
//
//        map.put("invoiceNumber",new Integer(invoice.getInvoiceId()));
//        map.put("invoiceDate", invoice.getInvoiceDate());
//
//        if (!invoice.getIsCashInvoice()) {
//            Customer customer = invoice.getCustomer();
//            map.put("customerName", customer.getName());
//
//            String city = invoice.getCustomer().getCity();
//            if (!(city == null || city.isEmpty())) {
//                map.put("customerCity", city);
//            }
//        }
//
//        BigDecimal amount = invoice.getAdditionalCharge();
//        if (amount != null) {
//            map.put("additionalCharge", amount);
//        }
//
//        amount = invoice.getDiscount();
//        if (amount != null) {
//            map.put("discount", amount);
//        }
//
//        FirmDetails firmDetails = FirmDetailsPersistence.getData();
//        if (firmDetails != null) {
//            map.put("firmName", firmDetails.getFirmName());
//            map.put("firmAddress", firmDetails.getAddress());
//            String str = firmDetails.getPhoneNumbers();
//            if (str != null) {
//                map.put("firmPhoneNumbers", str);
//            }
//
//            str = firmDetails.getEmailAddress();
//            if (str != null) {
//                map.put("firmEmailAddress", str);
//            }
//
//            byte[] logoBytes = firmDetails.getLogo();
//            if (logoBytes != null) {
//                ByteArrayInputStream byteArrayStream =
//                        new ByteArrayInputStream(logoBytes);
//                map.put("firmLogo", byteArrayStream);
//            }
//        }
//
//
//        return map;
//    }
}
