package hieudx.fpoly.warehousemanager.Bill.Model;

public class Bill_out_detail {
    private int id;
    private double price;
    private int quantity;
    private String total;
    private int id_product;
    private String id_bill_out;

    public Bill_out_detail(double price, int quantity, int id_product, String id_bill_out) {
        this.price = price;
        this.quantity = quantity;
        this.id_product = id_product;
        this.id_bill_out = id_bill_out;
    }

    public Bill_out_detail(int id, double price, int quantity, String total, int id_product, String id_bill_out) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.id_product = id_product;
        this.id_bill_out = id_bill_out;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getId_bill_out() {
        return id_bill_out;
    }

    public void setId_bill_out(String id_bill_out) {
        this.id_bill_out = id_bill_out;
    }
}
