package hieudx.fpoly.warehousemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Category;
import hieudx.fpoly.warehousemanager.models.Product;

public class Category_Dao {
    private DBHelper dbHelper;


    public Category_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    public ArrayList<Category> getListCategory(){
        ArrayList<Category> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from Category", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Category(c.getInt(0),c.getString(1)));
            } while (c.moveToNext());
        }
        return list;
    }
//    public boolean insertCategory(){
//        SQLiteDatabase database = dbHelper.getWritableDatabase();
//
//
//    }
}
