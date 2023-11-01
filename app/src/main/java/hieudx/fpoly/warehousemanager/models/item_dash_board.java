package hieudx.fpoly.warehousemanager.models;

public class item_dash_board {
    private int img;
    private String text;

    public item_dash_board(int img, String text) {
        this.img = img;
        this.text = text;
    }

    public item_dash_board() {
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
