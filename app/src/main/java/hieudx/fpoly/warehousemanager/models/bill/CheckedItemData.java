package hieudx.fpoly.warehousemanager.models.bill;

public class CheckedItemData {
    private int position;
    private String quantity;
    private String price;
    private int productId;

    public CheckedItemData(int position, String quantity, String price, int productId) {
        this.position = position;
        this.quantity = quantity;
        this.price = price;
        this.productId = productId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
