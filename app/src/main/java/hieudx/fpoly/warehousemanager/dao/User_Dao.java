package hieudx.fpoly.warehousemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Staff;
import hieudx.fpoly.warehousemanager.models.User;

public class User_Dao {
    private final DBHelper dbHelper;
    private User user;

    public User_Dao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> listUser = new ArrayList<User>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM User", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    user = new User();
                    user.setId(cursor.getInt(0));
                    user.setUsername(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    user.setName(cursor.getString(3));
                    user.setEmail(cursor.getString(4));
                    user.setPhone(cursor.getString(5));
                    user.setRole(cursor.getInt(6));
                    listUser.add(user);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzzzzzzzz", "Lỗiiiiii");
        }
        return listUser;
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("role", user.getRole());
        long row = db.insert("User", null, values);
        return (row > 0);
    }
    public boolean deleteUser(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("User", "id=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }

    public User getUserById(int id) {
         user = new User();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM User WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            user.setId(c.getInt(0));
            user.setName(c.getString(1));
        }
        return user;
    }
}
