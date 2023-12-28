package hieudx.fpoly.warehousemanager.Supplier.Model;

import java.util.Comparator;

public class Supplier {
    private int id;
    private String name;
    private String phone;
    private String address;
    private String tax_code;

    public static Comparator<Supplier> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());
    public static Comparator<Supplier> sortByNameZA = (t1, t2) -> t2.getName().compareTo(t1.getName());

    public Supplier() {
    }

    public Supplier(int id, String name, String phone, String address, String tax_code) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tax_code = tax_code;
    }

    public Supplier(String name, String phone, String address, String tax_code) {
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTax_code() {
        return tax_code;
    }

    public void setTax_code(String tax_code) {
        this.tax_code = tax_code;
    }
}
