package hieudx.fpoly.warehousemanager.models.bill;

import java.io.Serializable;

public class Bill_In implements Serializable {
    private String id;
    private String date_time;
    private int id_user;
    private int id_supplier;

    public Bill_In() {
    }

    public Bill_In(String id, String date_time, int id_user, int id_supplier) {
        this.id = id;
        this.date_time = date_time;
        this.id_user = id_user;
        this.id_supplier = id_supplier;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_supplier() {
        return id_supplier;
    }

    public void setId_supplier(int id_supplier) {
        this.id_supplier = id_supplier;
    }
}
