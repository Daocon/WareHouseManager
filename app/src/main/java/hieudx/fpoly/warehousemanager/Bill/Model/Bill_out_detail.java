package hieudx.fpoly.warehousemanager.Bill.Model;

public class Bill_out_detail {
    private int id;
    private int price;
    private int quantity;
    private int total;
    private int id_product;
    private int id_bill_out;

    public Bill_out_detail(int id, int price, int quantity, int total, int id_product, int id_bill_out) {
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

    public int getPrice() {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_bill_out() {
        return id_bill_out;
    }

    public void setId_bill_out(int id_bill_out) {
        this.id_bill_out = id_bill_out;
    }
}
