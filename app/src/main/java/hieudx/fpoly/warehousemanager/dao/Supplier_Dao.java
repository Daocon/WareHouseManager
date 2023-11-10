package hieudx.fpoly.warehousemanager.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.Supplier;

public class Supplier_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Supplier_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public Supplier getSupplierById(int id) {
        Supplier supplier = new Supplier();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Supplier WHERE id = ?", new String[]{String.valueOf(id)});
        if (c.getCount() != 0) {
            c.moveToFirst();
            supplier = new Supplier(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4));
        }
        return supplier;
    }
}
