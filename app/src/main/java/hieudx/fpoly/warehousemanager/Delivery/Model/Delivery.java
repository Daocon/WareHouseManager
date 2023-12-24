package hieudx.fpoly.warehousemanager.Delivery.Model;

import java.util.Comparator;

public class Delivery {
    private int id;
    private String name;
    private String phone;
    private double price;
    private String tax_code;

    public static Comparator<Delivery> sortByAscPrice = (t1, t2) -> (int) (t1.getPrice() - t2.getPrice());
    public static Comparator<Delivery> sortByDescPrice = (t1, t2) -> (int) (t2.getPrice() - t1.getPrice());
    public static Comparator<Delivery> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());
    public static Comparator<Delivery> sortByNameZA = (t1, t2) -> t2.getName().compareTo(t1.getName());

    public Delivery() {
    }

    public Delivery(int id, String name, String phone, double price, String tax_code) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.tax_code = tax_code;
    }

    public Delivery(String name, String phone, double price, String tax_code) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }
}
