package hieudx.fpoly.warehousemanager.models;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantityIn;
    private int getQuantityOut;
    private String img;
    private Category id_category;
    private int sale_price;
    public Product() {
    }


//    Product (\n" +
//            "    id          INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//            "    name        TEXT    NOT NULL,\n" +
//            "    price       INTEGER NOT NULL,\n" +
//            "    quantity    INTEGER NOT NULL,\n" +
//            "    id_category INTEGER REFERENCES Category (id),\n" +
//            "    sale_price  INTEGER NOT NULL\n" +
//            ");\n");


    public Product(int id, String name, int price, int quantityIn, Category id_category, int sale_price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityIn = quantityIn;
        this.id_category = id_category;
        this.sale_price = sale_price;
    }

    public Product(int id, String name, int price, int quantityIn, int getQuantityOut, String img, Category id_category, int sale_price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityIn = quantityIn;
        this.getQuantityOut = getQuantityOut;
        this.img = img;
        this.id_category = id_category;
        this.sale_price = sale_price;
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

    public int getQuantityIn() {
        return quantityIn;
    }

    public void setQuantityIn(int quantityIn) {
        this.quantityIn = quantityIn;
    }

    public int getGetQuantityOut() {
        return getQuantityOut;
    }

    public void setGetQuantityOut(int getQuantityOut) {
        this.getQuantityOut = getQuantityOut;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Category getId_category() {
        return id_category;
    }

    public void setId_category(Category id_category) {
        this.id_category = id_category;
    }

    public int getSale_price() {
        return sale_price;
    }

    public void setSale_price(int sale_price) {
        this.sale_price = sale_price;
    }
}
