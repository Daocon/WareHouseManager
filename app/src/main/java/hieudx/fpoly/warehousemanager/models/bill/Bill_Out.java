package hieudx.fpoly.warehousemanager.models.bill;

import java.io.Serializable;

public class Bill_Out implements Serializable {
    private String id;
    private String date_time;
    private String address;
    private int id_user;
    private int id_delivery;

    public Bill_Out() {
    }

    public Bill_Out(String id, String date_time, String address, int id_user, int id_delivery) {
        this.id = id;
        this.date_time = date_time;
        this.address = address;
        this.id_user = id_user;
        this.id_delivery = id_delivery;
    }

    public int getId_delivery() {
        return id_delivery;
    }

    public void setId_delivery(int id_delivery) {
        this.id_delivery = id_delivery;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
}
