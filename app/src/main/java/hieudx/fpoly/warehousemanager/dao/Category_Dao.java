package hieudx.fpoly.warehousemanager.dao;

import android.content.ContentValues;
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
    public boolean insertCategory(Category category){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",category.getName());
        long check = database.insert("Category",null,values);
        return check > 0;

    }
    public boolean updateCategory(Category category){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",category.getName());
        long check = database.update("Category",values, "id = ?", new String[]{String.valueOf(category.getId())});
        return check > 0;
    }
    public boolean deleteCategory(Category category){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long check = database.delete("Category","id = ?", new String[]{String.valueOf(category.getId())});
        return check > 0;
    }
}
