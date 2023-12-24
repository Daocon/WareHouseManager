package hieudx.fpoly.warehousemanager.Delivery.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.Delivery.Model.Delivery;
import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;

public class Delivery_Dao {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public Delivery_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public boolean isExistDelivery(String name) {
        Cursor c = db.rawQuery("select * from Delivery WHERE name = ?", new String[]{name});
        if (c.getCount() != 0) {
            return true;
        }
        return false;
    }

    public Delivery getDeliveryById(int id) {
        Delivery delivery = new Delivery();
        Cursor c = db.rawQuery("SELECT * FROM Delivery WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            delivery = new Delivery(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4));
        }
        return delivery;
    }
    public ArrayList<Delivery> getListDelivery() {
        ArrayList<Delivery> list = new ArrayList<>();
        Cursor c = db.rawQuery("Select * from Delivery", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Delivery(c.getInt(0), c.getString(1),c.getString(2),c.getInt(3),c.getString(4)));
            } while (c.moveToNext());
        }
        return list;
    }
    public long insertDelivery(Delivery delivery) {
        ContentValues values = new ContentValues();
        values.put("name", delivery.getName());
        values.put("phone", delivery.getPhone());
        values.put("price", delivery.getPrice());
        values.put("tax_code", delivery.getTax_code());
        return db.insert("Delivery", null, values);
    }

    public boolean updateDelivery(Delivery delivery) {
        ContentValues values = new ContentValues();
        values.put("name", delivery.getName());
        values.put("phone", delivery.getPhone());
        values.put("price", delivery.getPrice());
        values.put("tax_code", delivery.getTax_code());
        long check = db.update("Delivery", values, "id = ?", new String[]{String.valueOf(delivery.getId())});
        return check > 0;
    }

    public int deleteDelivery(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM Bill_out where id_delivery = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = db.delete("Delivery", "id = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }
}
