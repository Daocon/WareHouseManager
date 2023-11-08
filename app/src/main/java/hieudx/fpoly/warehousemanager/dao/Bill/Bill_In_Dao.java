package hieudx.fpoly.warehousemanager.dao.Bill;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import hieudx.fpoly.warehousemanager.SQliteDB.DBHelper;
import hieudx.fpoly.warehousemanager.models.bill.Bill_In;

public class Bill_In_Dao {
    private DBHelper dbHelper;
    private Context context;

    public Bill_In_Dao(Context context) {
        dbHelper = new DBHelper(context);
        this.context = context;
    }

    public ArrayList<Bill_In> getAll() {
        ArrayList<Bill_In> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Bill_in", null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                list.add(new Bill_In(c.getString(0), c.getInt(1),c.getString(2),c.getInt(3)));
            } while (c.moveToNext());
        }
        return list;
    }
}

