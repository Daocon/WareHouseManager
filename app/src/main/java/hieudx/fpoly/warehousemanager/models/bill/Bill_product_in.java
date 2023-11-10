package hieudx.fpoly.warehousemanager.models.bill;

public class Bill_product_in {
    private int id;
    private String name;
    private int price;
    private int sale;
    private int quantity;
    private String total;
    private int id_category;
    private String id_bill_in;

    public Bill_product_in() {
    }

    public Bill_product_in(int id, String name, int price, int sale, int quantity, String total, int id_category, String id_bill_in) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sale = sale;
        this.quantity = quantity;
        this.total = total;
        this.id_category = id_category;
        this.id_bill_in = id_bill_in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSale() {
        return sale;
    }

    public void setSale(int sale) {
        this.sale = sale;
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

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getId_bill_in() {
        return id_bill_in;
    }

    public void setId_bill_in(String id_bill_in) {
        this.id_bill_in = id_bill_in;
    }
}

