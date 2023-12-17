package hieudx.fpoly.warehousemanager.Product.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Product implements Serializable {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String img;
    private int id_category;
    private int id_supplier;

    public static Comparator<Product> sortByAscPrice = (t1, t2) -> (int) (t1.getPrice() - t2.getPrice());
    public static Comparator<Product> sortByDescPrice = (t1, t2) -> (int) (t2.getPrice() - t1.getPrice());
    public static Comparator<Product> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());
    public static Comparator<Product> sortByNameZA = (t1, t2) -> t2.getName().compareTo(t1.getName());

    public Product() {
    }

    public Product(String name, double price, int quantity, String img, int id_category, int id_supplier) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.id_category = id_category;
        this.id_supplier = id_supplier;
    }

    public Product(int id, String name, double price, int quantity, String img, int id_category, int id_supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.id_category = id_category;
        this.id_supplier = id_supplier;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }
}
