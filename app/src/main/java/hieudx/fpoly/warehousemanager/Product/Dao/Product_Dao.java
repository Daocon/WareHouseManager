package hieudx.fpoly.warehousemanager.Product.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Product.Model.Product;
import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;

public class Product_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public Product_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Product> getProductList() {
        ArrayList<Product> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Product", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Product(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4), c.getInt(5), c.getInt(6)));
            } while (c.moveToNext());
        }
        return list;
    }

    public Product getProductById(int id) {
        Product product = new Product();
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            product = new Product(c.getInt(0), c.getString(1), c.getDouble(2), c.getInt(3), c.getString(4), c.getInt(5), c.getInt(6));
        }
        return product;
    }

    public boolean getProductByName(String name) {
        Cursor c = db.rawQuery("SELECT * FROM Product WHERE name = ?", new String[]{name});
        if (c.getCount() != 0) {
            return true;
        }
        return false;
    }

    public long insertProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("img", product.getImg());
        values.put("id_category", product.getId_category());
        values.put("id_supplier", product.getId_supplier());
        return db.insert("Product", null, values);
    }

    public long updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("img", product.getImg());
        values.put("id_category", product.getId_category());
        values.put("id_category", product.getId_category());
        return db.update("Product", values, "id = ?", new String[]{String.valueOf(product.getId())});
    }

    public int deleteProduct(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Bill_in_detail WHERE id_product = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("Product", "id = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }
}
