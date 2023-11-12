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

    public ArrayList<Product> getProductList() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor c = database.rawQuery("Select * from Product", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Product(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4)));
            } while (c.moveToNext());
        }
        return list;
    }

    public Product getProductById(int id) {
        Product product = new Product();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            product = new Product(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getInt(4));
        }
        return product;
    }
}
