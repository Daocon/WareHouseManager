package hieudx.fpoly.warehousemanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Category;
import hieudx.fpoly.warehousemanager.models.Delivery;

public class Delivery_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Delivery_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public Delivery getDeliveryById(int id) {
        Delivery delivery = new Delivery();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Delivery WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            delivery = new Delivery(c.getInt(0), c.getString(1), c.getString(2), c.getInt(3), c.getString(4));
        }
        return delivery;
    }
    public ArrayList<Delivery> getListDelivery() {
        ArrayList<Delivery> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * from Delivery", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Delivery(c.getInt(0), c.getString(1),c.getString(2),c.getInt(3),c.getString(4)));
            } while (c.moveToNext());
        }
        return list;
    }
    public boolean insertDelivery(Delivery delivery) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", delivery.getName());
        values.put("phone", delivery.getPhone());
        values.put("price", delivery.getPrice());
        values.put("tax_code", delivery.getTax_code());
        long check = database.insert("Delivery", null, values);
        return check > 0;
    }

    public boolean updateDelivery(Delivery delivery) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name", delivery.getName());
        values.put("phone", delivery.getPhone());
        values.put("price", delivery.getPrice());
        values.put("tax_code", delivery.getTax_code());
        long check = database.update("Delivery", values, "id = ?", new String[]{String.valueOf(delivery.getId())});
        return check > 0;
    }

    public int deleteDelivery(int id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Bill_out where id_delivery = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        long check = database.delete("Delivery", "id = ?", new String[]{String.valueOf(id)});
        if (check == -1) {
            return 0;
        } else {
            return 1;
        }
    }
}
