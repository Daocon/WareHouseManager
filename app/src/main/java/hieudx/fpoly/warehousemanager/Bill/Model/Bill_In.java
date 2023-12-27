package hieudx.fpoly.warehousemanager.Bill.Model;

import java.io.Serializable;
import java.util.Comparator;

public class Bill_In implements Serializable {
    private String id;
    private String date_time;
    private double sum;
    private int status;
    private int id_user;

    public Bill_In() {
    }

    public static Comparator<Bill_In> sortByAscSum = (t1, t2) -> (int) (t1.getSum() - t2.getSum());

    public static Comparator<Bill_In> sortByDescSum = (t1, t2) -> (int) (t2.getSum() - t1.getSum());

    public static Comparator<Bill_In> sortByNameAZ = (t1, t2) -> t1.getId().compareTo(t2.getId());

    public static Comparator<Bill_In> sortByNameZA = (t1, t2) -> t2.getId().compareTo(t1.getId());

    public Bill_In(String id, String date_time, double sum, int status, int id_user) {
        this.id = id;
        this.date_time = date_time;
        this.sum = sum;
        this.status = status;
        this.id_user = id_user;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
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
