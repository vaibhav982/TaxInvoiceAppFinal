package Code.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;

public class InvoiceItems {

    private final SimpleStringProperty ItemDescription;
    private final  SimpleFloatProperty Quantity;
    private final  SimpleFloatProperty PPU;
    private final  SimpleFloatProperty Rate;

    public InvoiceItems(String Item_description, float quantity,float ppu, float rate) {
        this.ItemDescription = new SimpleStringProperty(Item_description);
        this.Quantity = new SimpleFloatProperty(quantity);
        this.PPU = new SimpleFloatProperty(ppu);
        this.Rate = new SimpleFloatProperty(rate);
    }

    public String getItem() {
        return ItemDescription.get();
    }

    public void setItem(String value) {
        ItemDescription.set(value);
    }

    public Float getQuantity() {
        return Quantity.get();
    }

    public void setQuantity(Float value) {
        Quantity.set(value);
    }

    public Float getPPU() {
        return PPU.get();
    }

    public void setPPU(Float value) {
        PPU.set(value);
    }

    public Float getRATE() {
        return Rate.get();
    }

    public void setRATE(Float value) {
        Rate.set(value);
    }


}
