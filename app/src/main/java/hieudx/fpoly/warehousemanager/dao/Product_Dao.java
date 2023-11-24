package hieudx.fpoly.warehousemanager.dao;

import android.content.ContentValues;
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
                list.add(new Product(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getString(4),c.getInt(5),c.getInt(6)));
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
    public boolean insertProduct(Product product){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("id_category",product.getId_category());
        values.put("price",product.getPrice());
        values.put("quantity",product.getQuantity());
        long check = database.insert("Product",null,values);
        return check>0;
    }
    public boolean updateProduct(Product product){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",product.getName());
        values.put("id_category",product.getId_category());
        values.put("price",product.getPrice());
        values.put("quantity",product.getQuantity());
        values.put("img",product.getImg());
        values.put("id_category",product.getId_category());
        long check = database.update("Product",values,"id = ?",new String[]{String.valueOf(product.getId())});
        return check>0;
    }
    public int deleteProduct(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Product WHERE id = ?",new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check = database.delete("Product","id = ?",new String[]{String.valueOf(id)});
        if (check== -1){
            return 0;
        }else {
            return 1;
        }
    }
}
