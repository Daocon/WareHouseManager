package hieudx.fpoly.warehousemanager.Category.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Category.Model.Category;
import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;

public class Category_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;


    public Category_Dao(Context context) {
        this.dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public ArrayList<Category> getListCategory() {
        ArrayList<Category> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Category", null);
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
        return db.insert("Category", null, values);
    }

    public int editCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        return db.update("Category", values, "id = ?", new String[]{String.valueOf(category.getId())});
    }

    public int deleteCategory(int idCategory) {
        Cursor cursor = db.rawQuery("SELECT * FROM Product where id_category = ?", new String[]{String.valueOf(idCategory)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("Category", "id = ?", new String[]{String.valueOf(idCategory)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isExistCategory(String name) {
        Cursor c = db.rawQuery("select * from Category WHERE name = ?", new String[]{name});
        if (c.getCount() != 0) {
            return true;
        }
        return false;
    }

    @SuppressLint("Range")
    public Category getCategoryById(int id) {
        Category category = null;
        Cursor c = db.rawQuery("SELECT * FROM Category WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            category = new Category(c.getInt(0), c.getString(1));
        }
        return category;
    }
}
