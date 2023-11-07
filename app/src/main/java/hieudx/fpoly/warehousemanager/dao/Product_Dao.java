package hieudx.fpoly.warehousemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Product;

public class Product_Dao {
    private DBHelper dbHelper;


    public Product_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Product> getProductList(){
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from Product",null);


        return list;
    }
}
