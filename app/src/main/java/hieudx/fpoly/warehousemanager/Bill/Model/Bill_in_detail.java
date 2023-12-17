package hieudx.fpoly.warehousemanager.Bill.Model;

public class Bill_in_detail {
    private int id;
    private double price;
    private int quantity;
    private String total;
    private int id_product;
    private String id_bill_in;

    public Bill_in_detail() {
    }

    public Bill_in_detail(double price, int quantity, int id_product, String id_bill_in) {
        this.price = price;
        this.quantity = quantity;
        this.id_product = id_product;
        this.id_bill_in = id_bill_in;
    }

    public Bill_in_detail(int id, double price, int quantity, String total, int id_product, String id_bill_in) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
        this.id_product = id_product;
        this.id_bill_in = id_bill_in;
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

    public void setPrice(int price) {
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

    public String getId_bill_in() {
        return id_bill_in;
    }

    public void setId_bill_in(String id_bill_in) {
        this.id_bill_in = id_bill_in;
    }
}

