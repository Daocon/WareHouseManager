package hieudx.fpoly.warehousemanager.Member.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.Member.Model.User;

public class User_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private User user;
    private SharedPreferences share;

    public User_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
        share = context.getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> listUser = new ArrayList<User>();
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
                    user.setAvatar(cursor.getString(7));
                    listUser.add(user);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzzzzzzzzzz", "Lỗiiiiii");
        }
        return listUser;
    }

    public boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("avatar", user.getAvatar());
        long row = db.update("User", values, "id = ?", new String[]{String.valueOf(user.getId())});
        return (row > 0);
    }

    public boolean updatePasswordUser(int userId, String newPassword) {
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        long row = db.update("User", values, "id = ?", new String[]{String.valueOf(userId)});
        return (row > 0);
    }

    public long updateUserRoleById(int userId, int role) {
        ContentValues values = new ContentValues();
        values.put("role", role);
        return db.update("User", values, "id = ?", new String[]{String.valueOf(userId)});
    }

    //    1: thêm thành công - 0: thêm thất bại - -1: thủ thư có tồn tại, k đc thêm
    public int insert(User user) {
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT * FROM User WHERE username = ?", new String[]{user.getUsername()});
        if (c.getCount() != 0) {
            return -1;
        }

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("name", user.getName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("role", user.getRole());
        values.put("avatar", user.getAvatar());

        long check = db.insert("User", null, values);
        if (check == -1) {
            return 0;
        }
        return 1;
    }

    public int deleteUser(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Bill_in WHERE id_user = ?", new String[]{String.valueOf(id)});
        Cursor cursor1 = db.rawQuery("SELECT * FROM Bill_out WHERE id_user = ?", new String[]{String.valueOf(id)});

        if (cursor.getCount() != 0 || cursor1.getCount() != 0) {
            return -1;
        }

        long check = db.delete("User", "id=?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        }
        return 1;
    }

    public User getUserById(int id) {
        Cursor c = db.rawQuery("SELECT * FROM User WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            user = new User(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getInt(6));
        }
        return user;
    }

    public User getUserByUsernameAndPassword(String username, String password) {
        User user = null;

        Cursor cursor = db.rawQuery("SELECT * FROM User WHERE username = ? AND password = ?",
                new String[]{username, password});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setPassword(cursor.getString(2));
            user.setName(cursor.getString(3));
            user.setEmail(cursor.getString(4));
            user.setPhone(cursor.getString(5));
            user.setRole(cursor.getInt(6));
            user.setAvatar(cursor.getString(7));
        }

        cursor.close();
        return user;
    }

    public boolean checkLogin(String username, String password) {
        Cursor c = db.rawQuery("SELECT * FROM User WHERE username = ? AND password = ?", new String[]{username, password});
        if (c.getCount() != 0) {
            c.moveToFirst();
            SharedPreferences.Editor editor = share.edit();
            editor.putInt("id", c.getInt(0));
            editor.putString("username", c.getString(1));
            editor.putString("password", c.getString(2));
            editor.putString("name", c.getString(3));
            editor.putString("email", c.getString(4));
            editor.putString("phone", c.getString(5));
            editor.putInt("role", c.getInt(6));
            editor.putString("avatar", c.getString(7));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }
}
