package Code.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;


// name qty unit price total

public class Data {
    private final SimpleStringProperty rName;
    private final SimpleDoubleProperty rQty;
    private final SimpleDoubleProperty rPrice;
    private final SimpleDoubleProperty rTotal;

    public Data(String sName, float sQty, float sPrice, float sTotal ){
        this.rName = new SimpleStringProperty(sName);
        this.rQty = new SimpleDoubleProperty(sQty);
        this.rPrice = new SimpleDoubleProperty(sPrice);
        this.rTotal = new SimpleDoubleProperty(sTotal);
    }

    //Name

    public String getRName(){
        return rName.get();
    }

    public void setRName(String v){
        rName.set(v);
    }
    //Quantity

    public Double getRQty(){
        return rQty.get();
    }
    public void setRQty(Double v){
        rQty.set(v);
    }

    //Unit Price

    public Double getRPrice(){
        return rPrice.get();
    }

    public void setRPrice(Double v){
        rPrice.set(v);
    }
    //Total Cost
    //Unit Price

    public Double getRTotal(){
        return rTotal.get();
    }

    public void setRTotal(Double v){
        rTotal.set(v);
    }
}