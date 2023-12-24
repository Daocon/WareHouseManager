package hieudx.fpoly.warehousemanager.Staff;

public class Staff {
    private int id;
    private String name;
    private String phone;

    private String address;

    private int work_day;

    private int salary;

    private int coefficient;

    public Staff(int id, String name, String phone, String address, int work_day, int salary, int coefficient) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.work_day = work_day;
        this.salary = salary;
        this.coefficient = coefficient;
    }

    public Staff(String name, String phone, String address, int work_day, int salary, int coefficient) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.work_day = work_day;
        this.salary = salary;
        this.coefficient = coefficient;
    }

    public Staff() {
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

    public int getWork_day() {
        return work_day;
    }

    public void setWork_day(int work_day) {
        this.work_day = work_day;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
}
