package hieudx.fpoly.warehousemanager.models;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;
    private String img;
    private int id_category;
    private int id_supplier;
    public Product() {
    }

    public Product(int id, String name, String img, int id_category, int id_supplier) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.id_category = id_category;
        this.id_supplier = id_supplier;
    }

    public Product(int id, String name, int price, int quantity, String img, int id_category, int id_supplier) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.img = img;
        this.id_category = id_category;
        this.id_supplier = id_supplier;
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
