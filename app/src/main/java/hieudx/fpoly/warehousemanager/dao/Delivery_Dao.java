package hieudx.fpoly.warehousemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
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
}
