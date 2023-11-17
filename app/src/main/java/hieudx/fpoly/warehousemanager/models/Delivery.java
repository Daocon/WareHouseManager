package hieudx.fpoly.warehousemanager.models;

public class Delivery {
    private int id;
    private String name;
    private String phone;
    private int price;
    private String tax_code;

    public Delivery() {
    }

    public Delivery(int id, String name, String phone, int price, String tax_code) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.tax_code = tax_code;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }
}
