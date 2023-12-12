package hieudx.fpoly.warehousemanager.Category.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.Category.Model.Category;

public class Category_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase database;


    public Category_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    public ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from Category", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Category(c.getInt(0), c.getString(1)));
            } while (c.moveToNext());
        }
        return list;
    }

    public long insertCategory(String name) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        return database.insert("Category", null, values);
    }

    public boolean updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        long check = database.update("Category", values, "id = ?", new String[]{String.valueOf(category.getId())});
        return check > 0;
    }

    public int deleteCategory(int idCategory) {
        Cursor cursor = database.rawQuery("SELECT * FROM Product where id_category = ?", new String[]{String.valueOf(idCategory)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = database.delete("Category", "id = ?", new String[]{String.valueOf(idCategory)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean getCategoryByName(String name) {
        Cursor c = database.rawQuery("select * from Category WHERE name = ?", new String[]{name});
        if (c.getCount() != 0) {
            return true;
        }
        return false;
    }
}
