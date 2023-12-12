package hieudx.fpoly.warehousemanager.Category.Model;

import java.util.Comparator;

public class Category {
    private int id;
    private String name;

    public static Comparator<Category> sortByNameAZ = (t1, t2) -> t1.getName().compareTo(t2.getName());

    public static Comparator<Category> sortByNameZA = (t1, t2) -> t2.getName().compareTo(t1.getName());

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
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
}
