
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Car {
    private final SimpleStringProperty id;
    private final SimpleStringProperty brand;
    private final SimpleStringProperty model;
    private final SimpleStringProperty status;
    private final SimpleDoubleProperty price;

    public Car(String id, String brand, String model, String status, double price) {
        this.id = new SimpleStringProperty(id);
        this.brand = new SimpleStringProperty(brand);
        this.model = new SimpleStringProperty(model);
        this.status = new SimpleStringProperty(status);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getId() { return id.get(); }
    public String getBrand() { return brand.get(); }
    public String getModel() { return model.get(); }
    public String getStatus() { return status.get(); }
    public double getPrice() { return price.get(); }

    public void setId(String id) { this.id.set(id); }
    public void setBrand(String brand) { this.brand.set(brand); }
    public void setModel(String model) { this.model.set(model); }
    public void setStatus(String status) { this.status.set(status); }
    public void setPrice(double price) { this.price.set(price); }
}
