package hieudx.fpoly.warehousemanager.interfaces;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Product.Model.Product;

public interface OnCheckedChangeListener {
    void onCheckedChanged(ArrayList<Product> list_checked);
}
